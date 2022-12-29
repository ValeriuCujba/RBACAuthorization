
package services.JDBCImpl;

import dao.intf.PermissionDao;
import entities.Permission;
import java.util.List;
import java.util.logging.Logger;
import services.intf.PermissionService;


public class PermissionJDBCService implements PermissionService{

    private final PermissionDao permissionDao;
    private static PermissionJDBCService instance;
    private static final Logger LOG = Logger.getLogger(PermissionJDBCService.class.getName());

    public static PermissionJDBCService getInstance(PermissionDao permissionDao) {
        if (instance == null) {
            synchronized (PermissionJDBCService.class) {
                instance = new PermissionJDBCService(permissionDao);
            }
        }
        return instance;
    }

    private PermissionJDBCService(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }  
    
    @Override
    public void create(Permission permission) throws Exception {
        Permission perm = permissionDao.findById(permission.getId());
        if(perm == null){
            permissionDao.create(permission);
        }        
        
    }

    @Override
    public void update(Permission permission) throws Exception {
        permissionDao.update(permission);
    }

    @Override
    public void delete(int id) throws Exception {
        permissionDao.delete(id);
    }   

    @Override
    public Permission findById(int id) throws Exception {
       return permissionDao.findById(id);
    }

    @Override
    public List<Permission> findAll() throws Exception {
        return permissionDao.findAll();
    }
    
}
