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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User findById(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> findAll() throws Exception {
//Variante de implementare: 
// 1: 3 sql-uri cu 3 prepared statement
//-- Selectez toti userii si formez lista de user cu infor din 4 coloane  
//SELECT * FROM users;
//-- Pentru fiecare user de sus gasesc rolurile pe care le are dupa user_id & le adaug la lista de roluri ale userului
//SELECT * FROM user_has_roles
//JOIN roles USING(role_id)
//WHERE user_id = 1;
//-- Tot asa dupa user_id selectez grupurile din care face parte userul si le adaug la lista de grupuri ale userului 
//SELECT * FROM group_has_users
//JOIN user_groups USING(group_id)
//WHERE user_id = 1;
//-------------------------------------------------------------------------
//2: 1 sql mare;
//SELECT * from users
//LEFT JOIN user_has_roles USING (user_id)
//LEFT JOIN roles ON user_has_roles.role_id = roles.role_id
//LEFT JOIN group_has_users ON group_has_users.user_id = users.user_id
//LEFT JOIN user_groups ON group_has_users.group_id = user_groups.group_id;
//        String sqlAllUsers = "SELECT * FROM users";
//        List<User> users = new ArrayList<>();
//        try (Connection connection = dataSource.getConnection();
//                PreparedStatement preparedStatement = connection.prepareStatement(sqlAllUsers);) {
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                String firstName = resultSet.getString(2);
//                String lastName = resultSet.getString(3);
//                String email = resultSet.getString(4);
//                String password = resultSet.getString(5);
//
//                User user = new User(id, firstName, lastName, email, password);
//
//                users.add(user);
//            }
//            preparedStatement.execute();
//        }
//
//        // pentru fiecare user din lista ii gasesc ce roluri are si le adaug la user
//        String sqlUserRoles = "SELECT * FROM user_has_roles\n"
//                + "JOIN roles USING(role_id)\n"
//                + "WHERE user_id = ?;";
//
//        try (Connection connection = dataSource.getConnection();
//                PreparedStatement preparedStatement = connection.prepareStatement(sqlUserRoles);) {
//
//            for (User user : users) {
//                preparedStatement.setInt(1, user.getId());
//                ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    int id = resultSet.getInt(1);
//                    String roleName = resultSet.getString(3);
//                    Role role = new Role(id, roleName);
//                    user.addRole(role);
//                }
//                preparedStatement.execute();
//            }
//        }
//
//        String sqlUserGroups = "SELECT * FROM group_has_users\n"
//                + "JOIN user_groups USING(group_id)\n"
//                + "WHERE user_id = ?;";
//
//        try (Connection connection = dataSource.getConnection();
//                PreparedStatement preparedStatement = connection.prepareStatement(sqlUserGroups);) {
//
//            for (User user : users) {
//                preparedStatement.setInt(1, user.getId());
//                ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    int id = resultSet.getInt(1);
//                    String groupName = resultSet.getString(3);
//                    Integer roleId = resultSet.getInt(4);
//                    Group group = null;
//                    if(roleId == null){
//                        group = new Group(id, groupName);
//                    }
//                     group = new Group(id, groupName, roleId);
//                    user.addRole(role);
//                }
//                preparedStatement.execute();
//            }
//            return users;
//        }
        return null;
    }
}
