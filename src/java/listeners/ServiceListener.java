package listeners;

import dao.impl.PermissionDaoImpl;
import dao.intf.PermissionDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import services.JDBCImpl.PermissionJDBCService;
import services.intf.PermissionService;

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
        PermissionService permissionService = PermissionJDBCService.getInstance(permissionDao);
        
        sce.getServletContext().setAttribute("permissionService", permissionService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         if (sce.getServletContext().getAttribute("permissionService") != null) {
            sce.getServletContext().removeAttribute("permissionService");
        }
    }
}
