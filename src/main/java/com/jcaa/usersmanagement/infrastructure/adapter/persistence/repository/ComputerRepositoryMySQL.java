package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteComputerPort;
import com.jcaa.usersmanagement.application.port.out.GetAllComputersPort;
import com.jcaa.usersmanagement.application.port.out.GetComputerByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveComputerPort;
import com.jcaa.usersmanagement.application.port.out.UpdateComputerPort;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import com.jcaa.usersmanagement.domain.valueobject.ComputerId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ComputerEntity;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.ComputerPersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ComputerRepositoryMySQL
        implements SaveComputerPort,
        UpdateComputerPort,
        DeleteComputerPort,
        GetComputerByIdPort,
        GetAllComputersPort {

    private static final String COLUMNS =
            "category, brand, cpu_brand, cpu_speed, ram_technology, ram_capacity, "
                    + "disk_technology, disk_capacity, monitor_brand, monitor_inches, "
                    + "usb_ports, hdmi_ports, price";

    private static final String SQL_INSERT =
            "INSERT INTO computers (" + COLUMNS + ", id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE computers SET category = ?, brand = ?, cpu_brand = ?, cpu_speed = ?, "
                    + "ram_technology = ?, ram_capacity = ?, disk_technology = ?, disk_capacity = ?, "
                    + "monitor_brand = ?, monitor_inches = ?, usb_ports = ?, hdmi_ports = ?, price = ? "
                    + "WHERE id = ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT id, " + COLUMNS + " FROM computers WHERE id = ? LIMIT 1";

    private static final String SQL_SELECT_ALL =
            "SELECT id, " + COLUMNS + " FROM computers ORDER BY brand ASC";

    private static final String SQL_DELETE = "DELETE FROM computers WHERE id = ?";

    private final DataSource dataSource;

    @Override
    public void save(final ComputerModel computer) {
        final ComputerEntity entity = ComputerPersistenceMapper.fromModelToEntity(computer);
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            bindColumns(statement, entity);
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseComputerSaveFailed(entity.id(), exception);
        }
    }

    @Override
    public void update(final ComputerModel computer) {
        final ComputerEntity entity = ComputerPersistenceMapper.fromModelToEntity(computer);
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            bindColumns(statement, entity);
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseComputerUpdateFailed(entity.id(), exception);
        }
    }

    @Override
    public Optional<ComputerModel> getById(final ComputerId computerId) {
        final String id = computerId.value().toString();
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setString(1, id);
            try (final ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(ComputerPersistenceMapper.fromResultSetToModel(resultSet));
            }
        } catch (final SQLException exception) {
            throw PersistenceException.becauseComputerFindIdFailed(id, exception);
        }
    }

    @Override
    public List<ComputerModel> getAll() {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
             final ResultSet resultSet = statement.executeQuery()) {
            return ComputerPersistenceMapper.fromResultSetToModelList(resultSet);
        } catch (final SQLException exception) {
            throw PersistenceException.becauseComputerFindAllFailed(exception);
        }
    }

    @Override
    public void delete(final ComputerId computerId) {
        final String id = computerId.value().toString();
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseComputerDeleteFailed(id, exception);
        }
    }

    private static void bindColumns(final PreparedStatement statement, final ComputerEntity entity)
            throws SQLException {
        statement.setString(1, entity.category());
        statement.setString(2, entity.brand());
        statement.setString(3, entity.cpuBrand());
        statement.setString(4, entity.cpuSpeed());
        statement.setString(5, entity.ramTechnology());
        statement.setString(6, entity.ramCapacity());
        statement.setString(7, entity.diskTechnology());
        statement.setString(8, entity.diskCapacity());
        statement.setString(9, entity.monitorBrand());
        statement.setBigDecimal(10, entity.monitorInches());
        statement.setInt(11, entity.usbPorts());
        statement.setInt(12, entity.hdmiPorts());
        statement.setBigDecimal(13, entity.price());
        statement.setString(14, entity.id());
    }
}