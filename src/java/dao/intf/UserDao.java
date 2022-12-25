
package dao.intf;

import entities.User;
import java.util.List;


public interface UserDao {
    
        void create(User user) throws Exception;
        void update(User user) throws Exception;
        void delete(int id) throws Exception;
        User findById(int id) throws Exception;
        List<User> findAll() throws Exception;
}
