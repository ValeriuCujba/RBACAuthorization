
package dao.impl;

import dao.intf.RoleDao;
import entities.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, role.getName());

            preparedStatement.execute();
        } catch (SQLException sqle) {
            LOG.severe(sqle.toString());
            throw sqle;
        }
    }

    @Override
    public void update(Role role) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Role findById(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Role> findAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
