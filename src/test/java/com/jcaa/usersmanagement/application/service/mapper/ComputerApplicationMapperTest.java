package com.jcaa.usersmanagement.application.service.mapper;

import static org.junit.jupiter.api.Assertions.*;
import com.jcaa.usersmanagement.application.service.dto.command.CreateComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetComputerByIdQuery;
import com.jcaa.usersmanagement.domain.enums.Category;
import com.jcaa.usersmanagement.domain.enums.DiskTechnology;
import com.jcaa.usersmanagement.domain.enums.RamTechnology;
import com.jcaa.usersmanagement.domain.exception.InvalidComputerCategoryException;
import com.jcaa.usersmanagement.domain.exception.InvalidComputerIdException;
import com.jcaa.usersmanagement.domain.exception.InvalidComputerMemoryException;
import com.jcaa.usersmanagement.domain.exception.InvalidComputerStorageException;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import com.jcaa.usersmanagement.domain.valueobject.*;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ComputerApplicationMapper")
class ComputerApplicationMapperTest {

    private static final String ID = "8f684dc4-0c67-4813-8d58-7b5e7f7937b8";
    private static final String CATEGORY = "GAMING";
    private static final String BRAND = "Lenovo";
    private static final String CPU_BRAND = "Intel";
    private static final String CPU_SPEED = "3.5GHz";
    private static final String RAM_TECHNOLOGY = "DDR5";
    private static final String RAM_CAPACITY = "16GB";
    private static final String DISK_TECHNOLOGY = "SSD";
    private static final String DISK_CAPACITY = "1TB";
    private static final String MONITOR_BRAND = "Dell";
    private static final BigDecimal MONITOR_INCHES = new BigDecimal("27");
    private static final int USB_PORTS = 4;
    private static final int HDMI_PORTS = 1;
    private static final BigDecimal PRICE = new BigDecimal("1299.99");

    @Test
    @DisplayName("fromCreateCommandToModel() mapea todos los campos y genera un id")
    void shouldMapCreateCommandToModelAndGenerateId() {

        final CreateComputerCommand command = createCommand(CATEGORY, RAM_TECHNOLOGY, DISK_TECHNOLOGY);

        final ComputerModel result = ComputerApplicationMapper.fromCreateCommandToModel(command);
        assertAll(
                "fromCreateCommandToModel()",
                () -> assertNotNull(result.getId(), "id debe ser generado"),
                () -> assertEquals(Category.GAMING, result.getComputerCategory(), "category"),
                () -> assertEquals(new ComputerBrand(BRAND), result.getBrand(), "brand"),
                () -> assertEquals(new ComputerProcessor(CPU_BRAND, CPU_SPEED), result.getProcessor(), "processor"),
                () -> assertEquals(new ComputerMemory(RamTechnology.DDR5, RAM_CAPACITY), result.getMemory(), "memory"),
                () -> assertEquals(new ComputerStorage(DiskTechnology.SSD, DISK_CAPACITY), result.getStorage(), "storage"),
                () -> assertEquals(new ComputerDisplay(MONITOR_BRAND, MONITOR_INCHES), result.getDisplay(), "display"),
                () -> assertEquals(new ComputerPorts(USB_PORTS, HDMI_PORTS), result.getPorts(), "ports"),
                () -> assertEquals(new ComputerPrice(PRICE), result.getPrice(), "price"));
    }

    @Test
    @DisplayName("fromCreateCommandToModel() genera un id distinto en cada llamada")
    void shouldGenerateDifferentIdOnEachCall() {
        final CreateComputerCommand command = createCommand(CATEGORY, RAM_TECHNOLOGY, DISK_TECHNOLOGY);
        final ComputerModel first = ComputerApplicationMapper.fromCreateCommandToModel(command);
        final ComputerModel second = ComputerApplicationMapper.fromCreateCommandToModel(command);
        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    @DisplayName("fromCreateCommandToModel() acepta categoría y tecnologías sin importar mayúsculas")
    void shouldAcceptEnumValuesIgnoringCase() {
        final CreateComputerCommand command = createCommand("gaming", "ddr5", "ssd");
        final ComputerModel result = ComputerApplicationMapper.fromCreateCommandToModel(command);
        assertAll(
                () -> assertEquals(Category.GAMING, result.getComputerCategory()),
                () -> assertEquals(RamTechnology.DDR5, result.getMemory().ramTechnology()),
                () -> assertEquals(DiskTechnology.SSD, result.getStorage().diskTechnology()));
    }

    @Test
    @DisplayName("fromCreateCommandToModel() propaga InvalidComputerCategoryException si la categoría no existe")
    void shouldPropagateInvalidCategory() {
        final CreateComputerCommand command = createCommand("TABLET", RAM_TECHNOLOGY, DISK_TECHNOLOGY);
        assertThrows(
                InvalidComputerCategoryException.class,
                () -> ComputerApplicationMapper.fromCreateCommandToModel(command));
    }

    @Test
    @DisplayName("fromCreateCommandToModel() propaga InvalidComputerMemoryException si la tecnología RAM no existe")
    void shouldPropagateInvalidRamTechnology() {
        final CreateComputerCommand command = createCommand(CATEGORY, "DDR9", DISK_TECHNOLOGY);
        assertThrows(
                InvalidComputerMemoryException.class,
                () -> ComputerApplicationMapper.fromCreateCommandToModel(command));
    }

    @Test
    @DisplayName("fromCreateCommandToModel() propaga InvalidComputerStorageException si la tecnología de disco no existe")
    void shouldPropagateInvalidDiskTechnology() {
        final CreateComputerCommand command = createCommand(CATEGORY, RAM_TECHNOLOGY, "FLOPPY");
        assertThrows(
                InvalidComputerStorageException.class,
                () -> ComputerApplicationMapper.fromCreateCommandToModel(command));
    }

    @Test
    @DisplayName("fromUpdateCommandToModel() conserva el id del command y mapea todos los campos")
    void shouldMapUpdateCommandToModelPreservingId() {
        final UpdateComputerCommand command = updateCommand(ID);
        final ComputerModel result = ComputerApplicationMapper.fromUpdateCommandToModel(command);
        assertAll(
                "fromUpdateCommandToModel()",
                () -> assertEquals(UUID.fromString(ID), result.getId().value(), "id"),
                () -> assertEquals(Category.GAMING, result.getComputerCategory(), "category"),
                () -> assertEquals(new ComputerBrand(BRAND), result.getBrand(), "brand"),
                () -> assertEquals(new ComputerProcessor(CPU_BRAND, CPU_SPEED), result.getProcessor(), "processor"),
                () -> assertEquals(new ComputerMemory(RamTechnology.DDR5, RAM_CAPACITY), result.getMemory(), "memory"),
                () -> assertEquals(new ComputerStorage(DiskTechnology.SSD, DISK_CAPACITY), result.getStorage(), "storage"),
                () -> assertEquals(new ComputerDisplay(MONITOR_BRAND, MONITOR_INCHES), result.getDisplay(), "display"),
                () -> assertEquals(new ComputerPorts(USB_PORTS, HDMI_PORTS), result.getPorts(), "ports"),
                () -> assertEquals(new ComputerPrice(PRICE), result.getPrice(), "price"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"not-a-uuid"})
    @DisplayName("fromUpdateCommandToModel() lanza InvalidComputerIdException si el id no es un UUID válido")
    void shouldRejectInvalidIdOnUpdate(final String invalidId) {
        final UpdateComputerCommand command = updateCommand(invalidId);
        assertThrows(
                InvalidComputerIdException.class,
                () -> ComputerApplicationMapper.fromUpdateCommandToModel(command));
    }

    @Test
    @DisplayName("fromGetComputerByIdQueryToComputerId() extrae el ComputerId del query")
    void shouldExtractComputerIdFromQuery() {
        final ComputerId result =
                ComputerApplicationMapper.fromGetComputerByIdQueryToComputerId(new GetComputerByIdQuery(ID));
        assertEquals(UUID.fromString(ID), result.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"not-a-uuid"})
    @DisplayName("fromGetComputerByIdQueryToComputerId() lanza InvalidComputerIdException si el id es inválido")
    void shouldRejectInvalidIdOnQuery(final String invalidId) {
        final GetComputerByIdQuery query = new GetComputerByIdQuery(invalidId);
        assertThrows(
                InvalidComputerIdException.class,
                () -> ComputerApplicationMapper.fromGetComputerByIdQueryToComputerId(query));
    }

    @Test
    @DisplayName("fromDeleteCommandToComputerId() extrae el ComputerId del command")
    void shouldExtractComputerIdFromDeleteCommand() {
        final ComputerId result =
                ComputerApplicationMapper.fromDeleteCommandToComputerId(new DeleteComputerCommand(ID));
        assertEquals(UUID.fromString(ID), result.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"not-a-uuid"})
    @DisplayName("fromDeleteCommandToComputerId() lanza InvalidComputerIdException si el id es inválido")
    void shouldRejectInvalidIdOnDelete(final String invalidId) {
        final DeleteComputerCommand command = new DeleteComputerCommand(invalidId);
        assertThrows(
                InvalidComputerIdException.class,
                () -> ComputerApplicationMapper.fromDeleteCommandToComputerId(command));
    }

    private static CreateComputerCommand createCommand(
            final String category, final String ramTechnology, final String diskTechnology) {
        return new CreateComputerCommand(
                category, BRAND, CPU_BRAND, CPU_SPEED, ramTechnology, RAM_CAPACITY,
                diskTechnology, DISK_CAPACITY, MONITOR_BRAND, MONITOR_INCHES,
                USB_PORTS, HDMI_PORTS, PRICE);
    }

    private static UpdateComputerCommand updateCommand(final String id) {
        return new UpdateComputerCommand(
                id, CATEGORY, BRAND, CPU_BRAND, CPU_SPEED, RAM_TECHNOLOGY, RAM_CAPACITY,
                DISK_TECHNOLOGY, DISK_CAPACITY, MONITOR_BRAND, MONITOR_INCHES,
                USB_PORTS, HDMI_PORTS, PRICE);
    }
}