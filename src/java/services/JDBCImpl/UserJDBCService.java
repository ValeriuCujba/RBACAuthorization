package services.JDBCImpl;

import dao.intf.PermissionDao;
import dao.intf.RoleDao;
import dao.intf.UserDao;
import entities.Group;
import entities.Permission;
import entities.Role;
import entities.User;
import java.util.List;
import java.util.logging.Logger;
import services.intf.RoleService;
import services.intf.UserService;

public class UserJDBCService implements UserService {

    private final RoleService roleService;
    private final UserDao userDao;
    private static UserJDBCService instance;
    private static final Logger LOG = Logger.getLogger(UserJDBCService.class.getName());

    public static UserJDBCService getInstance(RoleService roleService, UserDao userDao) {
        if (instance == null) {
            synchronized (UserJDBCService.class) {
                instance = new UserJDBCService(roleService, userDao);
            }
        }
        return instance;
    }

    private UserJDBCService(RoleService roleService, UserDao userDao) {
        this.roleService = roleService;
        this.userDao = userDao;

    }

    @Override
    public void create(User user) throws Exception {
        userDao.create(user);
    }

    @Override
    public void update(User user) throws Exception {
        userDao.update(user);
    }

    @Override
    public void delete(int id) throws Exception {
        userDao.delete(id);
    }

    @Override
    public User findById(int id) throws Exception {
        User user = userDao.findById(id);
        List<Role> roles = roleService.findRolesByUserId(id);

        user.setRoles(roles);
        return user;
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> users = userDao.findAll();
        for (User user : users) {
            user.setRoles(roleService.findRolesByUserId(user.getId()));
        }
        return users;
    }

    @Override
    public void addRole(User user, Role role) throws Exception {
        userDao.addRole(user, role);
    }

    @Override
    public void addGroup(Group group, User user) throws Exception {
        userDao.addGroup(group, user);
    }

}
