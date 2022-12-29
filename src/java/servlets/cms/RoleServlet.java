package servlets.cms;

import entities.Permission;
import entities.Role;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.intf.PermissionService;
import services.intf.RoleService;

public class RoleServlet extends HttpServlet {

    private RoleService service;
    private PermissionService permissionService;

    private boolean showAddForm = false;
    private boolean showEditForm = false;

    @Override
    public void init() throws ServletException {
        service = (RoleService) getServletContext().getAttribute("roleService");
        permissionService = (PermissionService) getServletContext().getAttribute("permissionService");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            System.out.println("Role Action = " + action);
            if (action == null) {
                showAll(request, response);
            } else {
                switch (action) {
                    case "showAdd":
                        showAdd(request, response);
                        break;
                    case "save":
                        save(request, response);
                        break;
                    case "edit":
                        edit(request, response);
                        break;
                    case "update":
                        update(request, response);
                        break;
                    case "delete":
                        delete(request, response);
                        break;
                    case "closeAdd":
                        closeAdd(request, response);
                        break;

                    default:
                        showAll(request, response);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RoleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roles = null;
        List<Permission> permissions = null;
        try {
            roles = service.findAll();
            permissions = permissionService.findAll();
        } catch (Exception ex) {
            Logger.getLogger(RoleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("showAddForm", showAddForm);
        request.setAttribute("showEditForm", showEditForm);
        request.setAttribute("roles", roles);
        request.setAttribute("permissions", permissions);

        String path = "/WEB-INF/pages/cms/roles.jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void showAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.showAddForm = true;
        showAll(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        String roleName = request.getParameter("roleName");
        Role role = new Role();
        role.setName(roleName);
        List<Permission> permissionsSelected = new ArrayList<>();
        try {
            List<Permission> permissions = permissionService.findAll();

            for (Permission permission : permissions) {
                String id = request.getParameter("" + permission.getId());
                if (id == null) {
                    continue;
                }
                int permissionId = Integer.valueOf(id);
                Permission permissionChecked = permissionService.findById(permissionId);
                System.out.println(permissionChecked.getName());
                permissionsSelected.add(permissionChecked);
            }
            role.setPermissions(permissionsSelected);
            service.create(role);
            this.showAddForm = false;
            showAll(request, response);
        } catch (Exception e) {
            Logger.getLogger(RoleServlet.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.showEditForm = true;
        String id = request.getParameter("id");
        int roleId = Integer.valueOf(id);
        Role role = null;
        try {
            role = service.findById(roleId);

            // TEEST chechboxes
            List<Permission> permissions = permissionService.findAll();

        for(Permission permission : permissions){
            System.out.println(role.getPermissions().contains(permission));
            if(role.getPermissions().contains(permission)){                
                System.out.println("Contains! " + permission.getId() );
            } else{
                System.out.println("1 for " + permission.getId());
            } 
            
        }

        } catch (Exception ex) {
            Logger.getLogger(RoleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("roleToUpdate", role);
        showAll(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        String roleName = request.getParameter("roleUpdateName");
        int roleId = Integer.valueOf(request.getParameter("roleUpdateId"));
        Role role = new Role(roleId, roleName);

        List<Permission> permissionsSelected = new ArrayList<>();
        try {
            List<Permission> permissions = permissionService.findAll();

            for (Permission permission : permissions) {
                String id = request.getParameter("update" + permission.getId());
                if (id == null || id.isEmpty()) {
                    continue;
                }
                int permissionId = Integer.valueOf(id);
                Permission permissionChecked = permissionService.findById(permissionId);
                System.out.println(permissionChecked.getName());
                permissionsSelected.add(permissionChecked);
            }
            role.setPermissions(permissionsSelected);
            service.update(role);
            this.showEditForm = false;
            showAll(request, response);
        } catch (Exception e) {
            Logger.getLogger(RoleServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            int roleId = Integer.valueOf(id);
            service.delete(roleId);
            showAll(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PermissionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void closeAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.showAddForm = false;
        this.showEditForm = false;
        showAll(request, response);
    }

}
