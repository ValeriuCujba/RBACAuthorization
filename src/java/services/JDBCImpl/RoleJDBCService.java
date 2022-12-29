package services.JDBCImpl;

import dao.intf.PermissionDao;
import dao.intf.RoleDao;
import entities.Permission;
import entities.Role;
import java.util.List;
import java.util.logging.Logger;
import services.intf.RoleService;

public class RoleJDBCService implements RoleService {

    private final RoleDao roleDao;
    private final PermissionDao permissionDao;
    private static RoleJDBCService instance;
    private static final Logger LOG = Logger.getLogger(RoleJDBCService.class.getName());

    public static RoleJDBCService getInstance(RoleDao roleDao, PermissionDao permissionDao) {
        if (instance == null) {
            synchronized (RoleJDBCService.class) {
                instance = new RoleJDBCService(roleDao, permissionDao);
            }
        }
        return instance;
    }

    private RoleJDBCService(RoleDao roleDao, PermissionDao permissionDao) {
        this.roleDao = roleDao;
        this.permissionDao = permissionDao;
    }

    @Override
    public void create(Role role) throws Exception {
       List<Role> roles = roleDao.findAll();
        if(roles.contains(role)){
            return;
        }
        roleDao.create(role);
    }

    @Override
    public void update(Role role) throws Exception {
        roleDao.update(role);
    }

    @Override
    public void delete(int id) throws Exception {
        roleDao.delete(id);
    }

    @Override
    public Role findById(int id) throws Exception {
        Role role = roleDao.findById(id);
        List<Permission> permissions = permissionDao.findPermissionsByRoleId(role.getId());
        if (permissions != null) {
            role.setPermissions(permissions);
        }
        return role;
    }

    @Override
    public List<Role> findAll() throws Exception {
        List<Role> roles = roleDao.findAll();

        for (Role role : roles) {
            List<Permission> permissions = permissionDao.findPermissionsByRoleId(role.getId());
            if (permissions != null) {
                role.setPermissions(permissions);
            }
        }
        return roles;

    }

    @Override
    public List<Role> findRolesByUserId(int id) throws Exception {
        List<Role> roles = roleDao.findRolesByUserId(id);        
         if (roles != null) {
            for (Role role : roles) {
                List<Permission> permissions = permissionDao.findPermissionsByRoleId(role.getId());
                if (permissions != null) {
                    role.setPermissions(permissions);
                }
            }
        }
        return roles;
    }

}
