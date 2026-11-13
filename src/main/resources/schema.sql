-- =============================================
-- Esquema de la base seleccionada mediante DB_NAME.
-- Gestión de Usuarios - Arquitectura Hexagonal
-- =============================================

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'admin',
    'admin@admin.com',
    '$2a$12$FZcilm60RgVJJn.XavlszO58Ij8B4r9DO5IBjK2WzLuO7XJdhDnSy',
    'ADMIN',
    'ACTIVE'
);


CREATE TABLE computers (
                                         id               CHAR(36)      NOT NULL PRIMARY KEY,
                                         brand            VARCHAR(50)   NOT NULL,
                                         category         ENUM('GAMING','OFFICE','WORKSTATION','ULTRABOOK','ALL_IN_ONE') NOT NULL,
                                         cpu_brand        VARCHAR(100)  NOT NULL,
                                         cpu_speed        VARCHAR(20)   NOT NULL,   -- '3.5GHZ' o '3500MHZ'
                                         ram_technology   VARCHAR(10)   NOT NULL,
                                         ram_capacity     VARCHAR(20)   NOT NULL,   -- '16GB'
                                         disk_technology  VARCHAR(10)   NOT NULL,
                                         disk_capacity    VARCHAR(20)   NOT NULL,   -- '1TB'
                                         usb_ports        INT UNSIGNED  NOT NULL DEFAULT 0,
                                         hdmi_ports       INT UNSIGNED  NOT NULL DEFAULT 0,
                                         monitor_brand    VARCHAR(100)  NOT NULL,
                                         monitor_inches   DECIMAL(5,2)  NOT NULL,
                                         price            DECIMAL(12,2) NOT NULL,
                                         CONSTRAINT chk_computers_monitor_inches CHECK (monitor_inches > 0 AND monitor_inches <= 80),
                                         CONSTRAINT chk_computers_price CHECK (price > 0 AND price <= 1000000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;