package dao.impl;

import dao.intf.UserDao;
import entities.Group;
import entities.Role;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class UserDaoImpl implements UserDao {

    private final DataSource dataSource;
    private static UserDaoImpl instance;
    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class.getName());

    public static UserDaoImpl getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (UserDaoImpl.class) {
                instance = new UserDaoImpl(dataSource);
            }
        }
        return instance;
    }

    private UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(User user) throws Exception {

        String sql = "INSERT INTO users VALUES (null, ?, ?, ?,?); ";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.execute();
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
    }

    @Override
    public void update(User user) throws Exception {
        String sql = "UPDATE users SET first_name=?, \n"
                + "last_name=?,\n"
                + "email =?, \n"
                + "password=?\n"
                + "WHERE user_id=?;";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.execute();
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM users WHERE user_id=?;";

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
    public User findById(int id) throws Exception {
        String sql = "SELECT * FROM users WHERE user_id = ?;";

        User user = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);

                user = new User(userId, firstName, lastName, email, password);
            }
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
        return user;
    }

    @Override
    public List<User> findAll() throws Exception {
        String sql = "SELECT * FROM users;";

        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);

                User user = new User(userId, firstName, lastName, email, password);
                users.add(user);
            }
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
        return users;
    }

    @Override
    public void addRole(User user, Role role) throws Exception {
        String sql = "INSERT INTO user_has_roles VALUES(?,?);";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, role.getId());

            preparedStatement.execute();

        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }

    }

    @Override
    public void addGroup(Group group, User user) throws Exception {
        String sql = "INSERT INTO group_has_users VALUES(?,?);";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, group.getId());
            preparedStatement.setInt(2, user.getId());

            preparedStatement.execute();

        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }

    }
}
