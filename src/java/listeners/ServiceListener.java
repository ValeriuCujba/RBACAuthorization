package listeners;

import dao.impl.PermissionDaoImpl;
import dao.impl.RoleDaoImpl;
import dao.impl.UserDaoImpl;
import dao.intf.PermissionDao;
import dao.intf.RoleDao;
import dao.intf.UserDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import services.JDBCImpl.PermissionJDBCService;
import services.JDBCImpl.RoleJDBCService;
import services.JDBCImpl.UserJDBCService;
import services.intf.PermissionService;
import services.intf.RoleService;
import services.intf.UserService;

public class ServiceListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Context initContext;
        DataSource dataSource = null;
        try {
            initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/role-group-authorization");
        } catch (NamingException ex) {
            Logger.getLogger(ServiceListener.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        PermissionDao permissionDao = PermissionDaoImpl.getInstance(dataSource);
        RoleDao roleDao = RoleDaoImpl.getInstance(dataSource);
        UserDao userDao = UserDaoImpl.getInstance(dataSource);
        
        PermissionService permissionService = PermissionJDBCService.getInstance(permissionDao);
        RoleService roleService = RoleJDBCService.getInstance(roleDao, permissionDao);
        UserService userService = UserJDBCService.getInstance(roleService, userDao);
        
        sce.getServletContext().setAttribute("permissionService", permissionService);
        sce.getServletContext().setAttribute("roleService", roleService);
        sce.getServletContext().setAttribute("userService", userService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         if (sce.getServletContext().getAttribute("permissionService") != null) {
            sce.getServletContext().removeAttribute("permissionService");
        }
          if (sce.getServletContext().getAttribute("roleService") != null) {
            sce.getServletContext().removeAttribute("roleService");
        }
    }
}
