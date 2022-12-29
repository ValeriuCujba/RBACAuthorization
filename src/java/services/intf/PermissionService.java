package services.intf;

import entities.Permission;
import java.util.List;

public interface PermissionService {

    void create(Permission permission) throws Exception;

    void update(Permission permission) throws Exception;

    void delete(int id) throws Exception;

    Permission findById(int id) throws Exception;

    List<Permission> findAll() throws Exception;
    
    
}
