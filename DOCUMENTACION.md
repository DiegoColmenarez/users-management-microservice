# Documentación del servicio de gestión de usuarios

## 1. Propósito y alcance

Servicio REST para crear, consultar, modificar y eliminar usuarios, autenticar
credenciales y emitir tokens JWT. Está construido con Java 17 y Spring Boot 3.
La arquitectura separa el dominio y los casos de uso de los detalles de entrada,
persistencia, correo y seguridad.

La API REST es el punto de entrada activo. El código de la CLI anterior se
conserva en el proyecto, pero no es el mecanismo que inicia la aplicación ni
tiene un contenedor de dependencias independiente. Spring es el único
*composition root*: [Main.java](src/main/java/com/jcaa/usersmanagement/Main.java)
inicia el contexto y Spring crea e inyecta los componentes.

## 2. Arquitectura general: hexagonal

La aplicación sigue Arquitectura Hexagonal (Ports and Adapters). La dirección de
las dependencias apunta hacia el centro:

```text
                 adaptadores de entrada
      REST / HTTP, seguridad, CLI heredada
                         |
                         v
          puertos de entrada (casos de uso)
                         |
                         v
          servicios de aplicación
                         |
                         v
             dominio del negocio
                         ^
                         |
          puertos de salida (interfaces)
                         ^
                         |
       adaptadores de salida: MySQL, SMTP
```

Los adaptadores traducen los protocolos externos a los tipos que entiende la
aplicación. Los puertos definen las operaciones requeridas o expuestas sin
acoplar el centro a Spring MVC, JDBC ni JavaMail. Las implementaciones se
conectan mediante inyección de dependencias de Spring.

### Capas y responsabilidades

#### 2.1 Dominio

Ubicación: [`src/main/java/com/jcaa/usersmanagement/domain`](src/main/java/com/jcaa/usersmanagement/domain)

Contiene los conceptos y reglas centrales:

- `UserModel` representa al usuario. Es inmutable y ofrece la fábrica
  `create(...)`, que genera usuarios en estado `PENDING`, además de operaciones
  para activarlo o desactivarlo.
- Los objetos de valor `UserId`, `UserName`, `UserEmail` y `UserPassword`
  encapsulan normalización y validación. El correo se recorta y se convierte a
  minúsculas; el nombre se recorta; la contraseña se valida y se hashea con
  BCrypt al recibir texto plano.
- `UserRole` admite `ADMIN`, `MEMBER` y `REVIEWER`. `UserStatus` admite
  `ACTIVE`, `INACTIVE`, `PENDING` y `BLOCKED`.
- Excepciones específicas expresan errores del dominio, como usuario inexistente,
  correo duplicado, credenciales incorrectas o datos inválidos.
- Hay clases `UserCreatedDomainEvent`, `UserUpdatedDomainEvent` y
  `UserDeletedDomainEvent`. Actualmente son tipos disponibles en el dominio,
  pero los casos de uso no los crean ni existe un publicador/consumidor conectado;
  por eso el servicio no debe considerarse actualmente dirigido por eventos.

En particular, `UserId` exige un valor no vacío, pero no valida que sea UUID,
aunque la columna de base de datos tiene longitud prevista para ese formato.

#### 2.2 Aplicación

Ubicación: [`src/main/java/com/jcaa/usersmanagement/application`](src/main/java/com/jcaa/usersmanagement/application)

Orquesta las operaciones sin ocuparse de HTTP ni SQL:

- `port/in` declara los casos de uso (crear, actualizar, borrar, buscar,
  listar y autenticar).
- `service` implementa esos casos de uso y aplica validaciones y reglas del
  flujo, por ejemplo comprobar que un correo no esté ocupado antes de guardar.
- `port/out` declara las capacidades externas que necesita la aplicación:
  guardar/buscar/actualizar/borrar usuarios y enviar correo.
- `service/dto/command` y `service/dto/query` definen los datos de entrada de los
  casos de uso. Separan el contrato de aplicación del formato HTTP.
- `UserApplicationMapper` convierte comandos y consultas en objetos de dominio.

Ejemplo de creación: validar el comando, normalizar el correo, verificar que no
esté registrado, crear el `UserModel` (aplicando BCrypt), guardarlo mediante
`SaveUserPort` y solicitar el correo de bienvenida.

#### 2.3 Adaptadores de entrada

Ubicación: [`src/main/java/com/jcaa/usersmanagement/infrastructure/entrypoint`](src/main/java/com/jcaa/usersmanagement/infrastructure/entrypoint)

- `rest/controller` expone los recursos HTTP y delega en los puertos de entrada.
- `rest/dto` contiene los contratos HTTP de solicitud y respuesta. Las respuestas
  de usuario no incluyen la contraseña.
- `rest/mapper/UserRestMapper` convierte los DTO HTTP a comandos/consultas y
  modelos de dominio a DTO de respuesta.
- `rest/advice/GlobalExceptionHandler` traduce excepciones a respuestas HTTP.
- `desktop` contiene adaptadores de la antigua CLI/controlador. No equivale a
  una interfaz de consola activa en el arranque actual.

#### 2.4 Adaptadores de salida

Ubicación: [`src/main/java/com/jcaa/usersmanagement/infrastructure/adapter`](src/main/java/com/jcaa/usersmanagement/infrastructure/adapter)

- `persistence/repository/UserRepositoryMySQL` implementa los puertos de
  persistencia usando JDBC, `DataSource` y sentencias preparadas.
- `persistence/mapper/UserPersistenceMapper` traduce entre filas/resultados,
  DTO de persistencia y el modelo de dominio.
- `email/JavaMailEmailSenderAdapter` implementa el puerto de correo y envía
  mensajes SMTP.

Los detalles de construcción del `DataSource`, SMTP, ejecución asíncrona, OpenAPI
y seguridad se configuran bajo
[`infrastructure/config`](src/main/java/com/jcaa/usersmanagement/infrastructure/config)
y [`infrastructure/security`](src/main/java/com/jcaa/usersmanagement/infrastructure/security).

## 3. Patrones y principios utilizados

| Patrón/principio | Uso en el proyecto |
|---|---|
| Arquitectura Hexagonal / Ports and Adapters | Puertos de entrada/salida en `application/port`; REST, MySQL y SMTP son adaptadores reemplazables. |
| Inversión de dependencias | Los servicios dependen de interfaces de salida, no de `UserRepositoryMySQL` ni de JavaMail. Spring conecta las implementaciones. |
| Servicio de aplicación / Use Case | Cada servicio implementa una operación concreta y coordina dominio y puertos. |
| Domain-Driven Design (DDD) | Modelo de dominio, objetos de valor, enumeraciones, excepciones propias y tipos de evento de dominio. |
| Value Object | `UserId`, `UserName`, `UserEmail` y `UserPassword` encapsulan invariantes y normalización. |
| Adapter | Los controladores REST traducen HTTP; el repositorio adapta JDBC/MySQL; el emisor adapta JavaMail/SMTP a los puertos. |
| Repository (estilo repositorio) | `UserRepositoryMySQL` centraliza acceso a usuarios y oculta SQL a los casos de uso; implementa varios puertos especializados. |
| DTO y Mapper | Separa representación HTTP, comandos de aplicación, persistencia y dominio; los mappers hacen las conversiones explícitas. |
| Command/Query (separación ligera) | Las operaciones reciben comandos y consultas distintos. No es CQRS completo: se usa el mismo modelo y almacenamiento para escritura y lectura. |
| Inyección de dependencias / Composition Root | Spring instancia servicios, adaptadores y configuración; `Main` arranca el contenedor. |
| Filter Chain | Spring Security inserta `JwtAuthenticationFilter` en su cadena para autenticar solicitudes antes de los controladores. |
| Ejecución asíncrona | `@Async("notificationExecutor")` y un `ThreadPoolTaskExecutor` dedicado desacoplan el envío de notificaciones del hilo HTTP. |
| Factory method | `UserModel.create(...)` crea un usuario nuevo con estado inicial; `UserPassword.fromPlainText/fromHash` distingue la creación de hash de la reconstrucción desde persistencia. |

No se observa una implementación de publicación de eventos, *outbox*, CQRS con
modelos separados ni unidad de trabajo/eventos transaccionales.

## 4. Flujos principales

### Crear usuario

```text
POST /api/users
  -> validar DTO HTTP
  -> UserRestMapper crea CreateUserCommand
  -> CreateUserUseCase / CreateUserService
  -> validar comando y comprobar correo
  -> UserApplicationMapper construye UserModel (PENDING, password BCrypt)
  -> SaveUserPort -> UserRepositoryMySQL -> MySQL
  -> notificación asíncrona a través de EmailSenderPort
  -> respuesta UserRestResponse (sin contraseña)
```

La solicitud requiere `id`, `name`, `email`, `password` y `role`. El nombre debe
tener al menos 3 caracteres y la contraseña al menos 8. El identificador no se
genera automáticamente en este flujo. El rol debe coincidir con uno de los
valores permitidos por el dominio.

### Inicio de sesión

```text
POST /api/auth/login
  -> LoginRestRequest / LoginCommand
  -> LoginService busca el correo y verifica BCrypt
  -> rechaza credenciales inválidas o usuario no ACTIVE
  -> JwtTokenService genera JWT firmado con id, rol y expiración
  -> respuesta con token, tipo Bearer y duración
```

### Consulta y administración

Las rutas REST convierten los datos de entrada en query/command, invocan el caso
de uso apropiado y mapean el resultado a DTO. Actualizar conserva el hash actual
si la contraseña nueva está ausente o en blanco; si se envía una nueva, la
valida y vuelve a hashearla. Borrar verifica primero que el usuario exista.

### Notificaciones de correo

`EmailNotificationService` carga plantillas HTML desde
[`src/main/resources/templates`](src/main/resources/templates), sustituye
marcadores y llama al puerto de salida. El envío se ejecuta en un pool dedicado
(2 hilos base, 4 máximo, cola de 100). Si el envío falla, se registra el error y
se lanza una excepción dentro de la tarea asíncrona; no hay reintentos ni cola
durable configurados.

La plantilla de bienvenida incluye el texto de contraseña recibido durante el
alta. Esta decisión forma parte del comportamiento actual, no de una garantía de
seguridad del diseño.

## 5. API HTTP

La documentación OpenAPI/Swagger UI está disponible en `/swagger-ui.html`; los
documentos OpenAPI se sirven bajo `/v3/api-docs`.

| Método | Ruta | Acceso configurado | Resultado habitual |
|---|---|---|---|
| `GET` | `/health` | Público | `200`, estado y metadatos de build. No verifica dependencias externas. |
| `POST` | `/api/auth/login` | Público | `200`, token JWT, tipo `Bearer` y expiración. |
| `POST` | `/api/users` | Público | `201`, usuario creado. |
| `GET` | `/api/users` | `ADMIN`, `REVIEWER` | `200`, lista de usuarios. |
| `GET` | `/api/users/{id}` | `ADMIN`, `REVIEWER` | `200`, usuario encontrado. |
| `PUT` | `/api/users/{id}` | `ADMIN` | `200`, usuario actualizado. |
| `DELETE` | `/api/users/{id}` | `ADMIN` | `204`, sin cuerpo. |

Las solicitudes protegidas deben llevar `Authorization: Bearer <token>`. La
configuración es stateless; CSRF, HTTP Basic y el formulario de login están
deshabilitados. Las rutas no listadas requieren autenticación.

Errores comunes se traducen en `ApiErrorResponse`:

- `400`: validación o excepción del dominio.
- `401`: credenciales no válidas o autenticación ausente/no válida.
- `403`: usuario autenticado sin rol suficiente.
- `404`: usuario inexistente.
- `409`: correo ya registrado.
- `500`: error de persistencia u otro error interno.

El manejador de errores evita devolver detalles de persistencia al cliente en
errores `500`.

## 6. Modelo de datos

El esquema está en [`src/main/resources/schema.sql`](src/main/resources/schema.sql).
La tabla `users` almacena `id`, `name`, `email`, `password` (hash), `role`,
`status` y marcas de tiempo. El correo tiene restricción de unicidad. La base
usa InnoDB y `utf8mb4`.

El esquema también inserta un registro de ejemplo con rol administrativo. Antes
de usar una base en un entorno real, revisar el contenido de inicialización,
rotar o retirar cuentas de ejemplo y aplicar las políticas de acceso apropiadas.

La aplicación usa JDBC directo y HikariCP; no hay ORM/JPA en el repositorio
principal. Las consultas SQL usan `PreparedStatement`.

## 7. Configuración y variables de entorno

`application.properties` importa opcionalmente `.env` para desarrollo local y
expone sus valores como propiedades Spring. El archivo `.env` está ignorado por
Git. **No se deben copiar secretos ni los valores actuales de `.env` a esta
documentación, a `application.properties`, al repositorio ni a logs.**

| Variable | Propósito |
|---|---|
| `PORT` | Puerto HTTP del servicio. |
| `DB_HOST`, `DB_PORT`, `DB_NAME` | Ubicación y nombre de la base MySQL usada por la API. |
| `DB_USERNAME`, `DB_PASSWORD` | Credenciales de conexión de la API. |
| `DB_SSL_MODE` | Modo SSL de la conexión MySQL. |
| `MYSQL_USER`, `MYSQL_PASSWORD`, `MYSQL_ROOT_PASSWORD` | Inicialización del contenedor MySQL en Compose; son independientes de las variables de conexión `DB_*`, aunque Compose las relaciona. |
| `JWT_SECRET` | Clave de firma JWT en formato Base64, decodificada por el servicio al arrancar. Debe ser aleatoria, suficientemente fuerte y exclusiva por entorno. |
| `JWT_EXPIRATION_SECONDS` | Duración del token en segundos. |
| `SMTP_HOST`, `SMTP_PORT` | Host y puerto del servidor SMTP. |
| `SMTP_USERNAME`, `SMTP_PASSWORD` | Credenciales SMTP. |
| `SMTP_FROM_ADDRESS`, `SMTP_FROM_NAME` | Dirección y nombre visibles del remitente. |

Para desarrollo local, crear un `.env` privado con las variables requeridas por
el entorno; no reutilizar ni compartir el archivo local. Compose proporciona
algunos valores predeterminados no secretos para la API, pero requiere las
variables marcadas en `compose.yaml`. En Render, completar los secretos en el
panel de variables del servicio. `render.yaml` y `compose.yaml` describen
configuración de despliegue, no son almacenes para credenciales.

## 8. Ejecución y despliegue

### Requisitos

- Java 17 (para ejecución local).
- Docker y Docker Compose para levantar API y MySQL juntos.
- Una base MySQL y credenciales SMTP si se quiere probar persistencia y correos.

### Pruebas y empaquetado

```bash
./mvnw clean test
./mvnw clean package
```

En Windows se puede usar `mvnw.cmd`. Las pruebas están organizadas por dominio,
aplicación e infraestructura en `src/test/java`; cubren objetos de valor,
servicios, mappers, controladores, persistencia y JWT.

### Docker Compose

Con el `.env` local configurado:

```bash
docker compose up --build
```

Compose levanta MySQL, espera su health check y luego inicia la API en el puerto
publicado `8080`. El script `schema.sql` se monta como inicialización de MySQL;
la inicialización automática de la imagen se aplica al crear un volumen de datos
nuevo.

### Render

[`render.yaml`](render.yaml) declara un servicio web Docker con `/health` como
health check y despliegue asociado a `main`. La base MySQL debe ser accesible
desde Render y tener el esquema aplicado; no se declara una base MySQL gestionada
por ese Blueprint. Swagger UI se expone en `/swagger-ui.html`.

El [`Dockerfile`](Dockerfile) construye el artefacto con Maven/Java 17 y ejecuta
el JAR en una imagen JRE con un usuario de sistema no root.

## 9. Configuración técnica relevante

- [`application.properties`](src/main/resources/application.properties) importa
  `.env` y desactiva autoconfiguraciones de DataSource, mail y
  `UserDetailsService`; esos componentes se definen explícitamente.
- `DataSourceSpringConfig` construye un pool HikariCP.
- `SmtpSpringConfig` y `SmtpConfig` configuran la integración SMTP.
- `SecurityConfig` declara reglas HTTP por método y ruta.
- `JwtAuthenticationFilter` analiza tokens Bearer; los tokens inválidos se
  dejan sin autenticar y Spring aplica después las reglas de acceso.
- `GlobalExceptionHandler` forma la respuesta JSON de error de REST.
- Los metadatos de `/health` proceden de `build-info` generado por Maven.

## 10. Organización de pruebas

El conjunto de pruebas refleja las capas:

- `domain`: invariantes, eventos, estados, roles y excepciones.
- `application`: casos de uso, notificaciones y mapeo de aplicación.
- `infrastructure`: controladores, mappers, adaptadores de persistencia/correo
  y generación/verificación de JWT.

Para validar cambios de comportamiento, ejecutar `./mvnw clean test`. Para
generar el artefacto ejecutable y comprobar el empaquetado, ejecutar
`./mvnw clean package`.

## 11. Referencias de código

- Arranque: [Main.java](src/main/java/com/jcaa/usersmanagement/Main.java)
- Dominio: [`domain`](src/main/java/com/jcaa/usersmanagement/domain)
- Puertos y servicios: [`application`](src/main/java/com/jcaa/usersmanagement/application)
- Entrada REST: [`rest`](src/main/java/com/jcaa/usersmanagement/infrastructure/entrypoint/rest)
- Seguridad JWT: [`security`](src/main/java/com/jcaa/usersmanagement/infrastructure/security)
- Persistencia MySQL: [`persistence`](src/main/java/com/jcaa/usersmanagement/infrastructure/adapter/persistence)
- Correo: [`email`](src/main/java/com/jcaa/usersmanagement/infrastructure/adapter/email)
- Configuración de aplicación: [application.properties](src/main/resources/application.properties)
- Esquema SQL: [schema.sql](src/main/resources/schema.sql)
- Compose: [compose.yaml](compose.yaml)
- Despliegue Render: [render.yaml](render.yaml)
- Build/dependencias: [pom.xml](pom.xml)
