package dao.impl;

import dao.intf.PermissionDao;
import entities.Permission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class PermissionDaoImpl implements PermissionDao {

    private final DataSource dataSource;
    private static PermissionDaoImpl instance;
    private static final Logger LOG = Logger.getLogger(PermissionDaoImpl.class.getName());

    public static PermissionDaoImpl getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (PermissionDaoImpl.class) {
                instance = new PermissionDaoImpl(dataSource);
            }
        }
        return instance;
    }

    private PermissionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Permission permission) throws Exception {
        String sql = "INSERT INTO permissions VALUES (null, ?);";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, permission.getName());

            preparedStatement.execute();
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
    }

    @Override
    public void update(Permission permission) throws Exception {
        String sql = "UPDATE permissions SET name=? WHERE permission_id=?;";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, permission.getName());
            preparedStatement.setInt(2, permission.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM permissions WHERE permission_id=?;";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
    }

    @Override
    public Permission findById(int id) throws Exception {
        String sql = "SELECT name FROM permissions\n"
                + "WHERE permission_id = ?;";
        Permission permission = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(1);

                permission = new Permission(id, name);

            }

        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
        return permission;
    }

    @Override
    public List<Permission> findAll() throws Exception {
        String sql = "SELECT * FROM permissions;";

        List<Permission> permissions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);

                Permission permission = new Permission(id, name);

                permissions.add(permission);
            }
//            preparedStatement.execute();

        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
        return permissions;
    }

    @Override
    public List<Permission> findPermissionsByRoleId(int id) throws Exception {

        String sql = "SELECT * FROM permissions\n"
                + "JOIN role_has_permissions USING (permission_id)\n"
                + "WHERE role_has_permissions.role_id = ?;";

        List<Permission> permissions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int permissionId = resultSet.getInt(1);
                String name = resultSet.getString(2);

                Permission permission = new Permission(permissionId, name);

                permissions.add(permission);
            }
            preparedStatement.execute();

        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
        return permissions;

    }
}
