
package dao.intf;

import entities.Role;
import java.util.List;


public interface RoleDao {    
    
    void create(Role role) throws Exception;

    void update(Role role) throws Exception;

    void delete(int id) throws Exception;

    Role findById(int id) throws Exception;

    List<Role> findAll() throws Exception;
}
