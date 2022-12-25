package servlets.cms;

import entities.Permission;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.intf.PermissionService;

public class PermissionServlet extends HttpServlet {

    private PermissionService service;

    private boolean showAddForm = false;
    private boolean showDeleteButtons = false;

    @Override
    public void init() throws ServletException {
        service = (PermissionService) getServletContext().getAttribute("permissionService");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            System.out.println("Action = " + action);

            if (action == null) {
                showAll(request, response);
            } else {
                switch (action) {
                    case "showAdd":
                        showAdd(request, response);
                        break;
                    case "showDelete":
                        showDelete(request, response);
                        break;
                    case "delete":
                        delete(request, response);
                        break;
                    case "edit":
                        edit(request, response);
                        break;
                    case "saveOrUpdate":
                        saveOrUpdate(request, response);
                        break;
                    default:
                        showAll(request, response);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PermissionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Permission> permissions = null;

        try {
            permissions = service.findAll();
        } catch (Exception ex) {
            Logger.getLogger(PermissionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("permissions", permissions);
        request.setAttribute("showAddForm", showAddForm);
        request.setAttribute("showDeleteButtons", showDeleteButtons);

        if (request.getAttribute("permissionToEdit") == null) {
            request.setAttribute("permissionToEdit", new Permission(0, ""));
        }
        String path = "/WEB-INF/pages/permissions.jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }

    private void showAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.showAddForm = true;

        showAll(request, response);

    }

    private void showDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        showDeleteButtons = true;
        showAll(request, response);

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            int permissionId = Integer.valueOf(id);
            service.delete(permissionId);
            showDeleteButtons = false;
            showAll(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PermissionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.showAddForm = true;
            String id = request.getParameter("id");
            int permissionId = Integer.valueOf(id);
            Permission permisionToEdit = service.findById(permissionId);

            request.setAttribute("permissionToEdit", permisionToEdit);
            showAll(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PermissionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.valueOf(request.getParameter("permissionId"));
            String permissionName = request.getParameter("permissionName");
            Permission permission = new Permission();
            permission.setName(permissionName);

            if (id == 0) {
                service.create(permission);
            } else {
                permission.setId(id);
                service.update(permission);
            }
            this.showAddForm = false;
            showAll(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PermissionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

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

}
