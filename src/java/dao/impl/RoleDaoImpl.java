package dao.impl;

import dao.intf.PermissionDao;
import dao.intf.RoleDao;
import entities.Permission;
import entities.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class RoleDaoImpl implements RoleDao {

    private final DataSource dataSource;
    private static RoleDaoImpl instance;
    private static final Logger LOG = Logger.getLogger(RoleDaoImpl.class.getName());

    public static RoleDaoImpl getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (RoleDaoImpl.class) {
                instance = new RoleDaoImpl(dataSource);
            }
        }
        return instance;
    }

    private RoleDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Role role) throws Exception {
        String sql = "INSERT INTO roles VALUES (null, ?);";
        String sqlId = "SELECT role_id FROM roles WHERE roles.name=?;";
        String sqlBridgeTable = "INSERT INTO role_has_permissions VALUES (?,?);";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PreparedStatement preparedStatementId = connection.prepareStatement(sqlId);
                PreparedStatement preparedStatementBridgeTable = connection.prepareStatement(sqlBridgeTable);) {

            preparedStatement.setString(1, role.getName());
            preparedStatement.execute();

            //execute Select - find role Id
            Integer roleId = null;
            preparedStatementId.setString(1, role.getName());
            ResultSet resultSet = preparedStatementId.executeQuery();
            while (resultSet.next()) {
                roleId = resultSet.getInt(1);
            }

            for (Permission permission : role.getPermissions()) {
                preparedStatementBridgeTable.setInt(1, roleId);
                preparedStatementBridgeTable.setInt(2, permission.getId());
                preparedStatementBridgeTable.execute();
            }

        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
    }

    @Override
    public void update(Role role) throws Exception {
        String sql = "UPDATE roles SET name=? WHERE role_id=?;";
        String sqlDeleteBridgeTable = "DELETE FROM role_has_permissions WHERE role_id=?;";
        String sqlNewBridgeTable = "INSERT INTO role_has_permissions VALUES (?,?);";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PreparedStatement preparedStatementDelete = connection.prepareStatement(sqlDeleteBridgeTable);
                PreparedStatement preparedStatementNew = connection.prepareStatement(sqlNewBridgeTable);) {

            preparedStatement.setString(1, role.getName());
            preparedStatement.setInt(2, role.getId());
            preparedStatement.executeUpdate();

            preparedStatementDelete.setInt(1, role.getId());
            preparedStatementDelete.executeUpdate();

            for (Permission permission : role.getPermissions()) {
                preparedStatementNew.setInt(1, role.getId());
                preparedStatementNew.setInt(2, permission.getId());
                preparedStatementNew.executeUpdate();
            }
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }

    }

    @Override
    public void delete(int id) throws Exception {

        String sql = "DELETE FROM roles WHERE role_id=?;";

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
    public Role findById(int id) throws Exception {
        String sql = "SELECT name FROM roles WHERE role_id = ?;";

        Role role = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(1);

                role = new Role(id, name);
            }
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
        return role;
    }

    @Override
    public List<Role> findAll() throws Exception {

        String sql = "SELECT * FROM roles;";

        List<Role> roles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int roleId = resultSet.getInt(1);
                String roleName = resultSet.getString(2);
                Role role = new Role(roleId, roleName);
                roles.add(role);
            }
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
        return roles;
    }

    @Override
    public List<Role> findRolesByUserId(int id) throws Exception {

        String sql = "SELECT * FROM roles "
                + "JOIN user_has_roles USING (role_id) "
                + "WHERE user_has_roles.user_id = ?;";

        List<Role> roles = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int roleId = resultSet.getInt(1);
                String roleName = resultSet.getString(2);

                Role role = new Role(roleId, roleName);

                roles.add(role);
            }
            preparedStatement.execute();

        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
        return roles;

    }

}
