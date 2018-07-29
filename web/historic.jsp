<%-- 
    Document   : historic
    Created on : Jul 27, 2018, 4:25:12 PM
    Author     : ashraf
--%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@page import="com.google.appengine.api.datastore.Query"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@page import="ccDocStrg.Defs"%>
<%@page import="ccDocStrg.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cloud Storage</title>
    </head>
    <body>
        <h1>Cloud Storage</h1>
        <h2>A Cloud-based application for storing files.</h2>
        <p align="right">
            <%=session.getAttribute(Defs.SESSION_MESSAGE_STRING)%>
     
        <hr>
        <br><br>
        <a href="DeleteHistoric.jsp">Clear historic</a>
        <table>
            <%
              User currentUser = (User) session.getAttribute(Defs.SESSION_USER_STRING);
              if (currentUser != null) {
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                Query dateQuery = new Query(Defs.DATASTORE_KIND_DATE_STRING);
                List<Entity> date = datastore.prepare(dateQuery).asList(FetchOptions.Builder.withDefaults());
                if (!date.isEmpty()) {
                  Iterator<Entity> allDates = date.iterator();
            %>
            <tr>
                <td><b>Dates</b></td><td></td><td></td>
            </tr>
            <%
                  while (allDates.hasNext()) {
                    String historic = (String)allDates.next().getProperty(Defs.ENTITY_PROPERTY_DATE_DATE);
            %><tr>
                <td><%=historic%></td>
            
            </tr>
            
            <%
                }
              }
            %>
        </table>
        <br>
        <hr>
        <footer>
            <a href="/">Home</a> | 
            <a href="upload.jsp">Upload a file</a> | 
            <a href="logout">Logout</a> | 
            <a href="profile.jsp">Update profile</a></footer>
            <%              } else {
                session.setAttribute(Defs.SESSION_MESSAGE_STRING, "Please login first!");
                response.sendRedirect(Defs.LOGIN_PAGE_STRING);
              }
            %>
    </body>
</html>
