/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccDocStrg;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ashraf
 */
public class DeleteHistoric extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Prepare the session context.
        HttpSession session = request.getSession(true);
        //Get the user information from the session context.
        User currentUSer = (User) session.getAttribute(Defs.SESSION_USER_STRING);
        //Get the file name from the URL
        //Make sure that the user has already loggedin and that the fileName parameter is not empty/null.
        if (currentUSer != null) {
            //Prepare the Datastore service.
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            //We will serach in the 'Files' table for the file name.
            Query fileQuery = new Query(Defs.DATASTORE_KIND_DATE_STRING);

            List<Entity> Date = datastore.prepare(fileQuery).asList(FetchOptions.Builder.withDefaults());
            Iterator<Entity> allDate = Date.iterator();
            int i = 0;
            while (allDate.hasNext()) {
                String[] date = null;
                date[i] = (String) allDate.next().getProperty(Defs.ENTITY_PROPERTY_DATE_DATE);
                //Set a filetr on the file name.
                Query.Filter fileFilter = new Query.FilterPredicate(Defs.ENTITY_PROPERTY_DATE_DATE,
                        Query.FilterOperator.EQUAL, date[i]);
                fileQuery.setFilter(fileFilter);
                //Run the query.
                List<Entity> dbFiles = datastore.prepare(fileQuery).asList(FetchOptions.Builder.withDefaults());
                if (dbFiles.isEmpty()) {
                    //There was no such file name.
                    session.setAttribute(Defs.SESSION_MESSAGE_STRING, "No such file!");
                    response.sendRedirect(Defs.LIST_PAGE_STRING);
                    //If the file name was found then delete it from the Datastore.
                } else {

                    datastore.delete(dbFiles.get(0).getKey());
                    session.setAttribute(Defs.SESSION_MESSAGE_STRING, "Historic Cleared!");
                    response.sendRedirect(Defs.LIST_PAGE_STRING);
                }
                ++i;
            }
        } else {
            //If the user has not logged in then return him/her to the login page.
            session.setAttribute(Defs.SESSION_MESSAGE_STRING, "Please login firt!");
            response.sendRedirect(Defs.LOGIN_PAGE_STRING);
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
