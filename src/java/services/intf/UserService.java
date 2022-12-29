
package services.intf;

import entities.Group;
import entities.Role;
import entities.User;
import java.util.List;


public interface UserService {
    
    void create(User user) throws Exception;

    void update(User user) throws Exception;

    void delete(int id) throws Exception;

    User findById(int id) throws Exception;

    List<User> findAll() throws Exception;

    void addRole(User user, Role role) throws Exception;

    void addGroup(Group group, User user) throws Exception;
    
}
