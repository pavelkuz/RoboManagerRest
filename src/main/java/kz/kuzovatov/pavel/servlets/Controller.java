package kz.kuzovatov.pavel.servlets;

import kz.kuzovatov.pavel.action.Action;
import kz.kuzovatov.pavel.action.ActionFactory;
import kz.kuzovatov.pavel.action.View;
import kz.kuzovatov.pavel.daos.JdbcDaoFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Controller", urlPatterns = "/Controller")
public class Controller extends HttpServlet {
    private static Logger log = Logger.getLogger(Controller.class);
    private static JdbcDaoFactory jdbcDaoFactory = new JdbcDaoFactory();
    private static ActionFactory actionFactory = new ActionFactory();
    public static ActionFactory getActionFactory(){
        return actionFactory;
    }
    public static JdbcDaoFactory getJdbcDaoFactory() {
        return jdbcDaoFactory;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        Action action = getActionFactory().getAction(req);
        if(action == null){
            try {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
               log.error("IOException was occurred: " + e);
            }
            return;
        }
        View view = null;
        try {
            view = action.execute(req, resp);
        } catch (Exception e) {
            try {
                PrintWriter printWriter = resp.getWriter();
                printWriter.write("Error was occurred: "+e);
            } catch (IOException e1) {
                log.error("Unsuccessfully attempt of printing error: " + e);
            }
            log.error("Error was occurred: " + e);
        }
        if (view!=null){
            if (view.isRedirect()) {
                try {
                    resp.sendRedirect(view.getPath());
                } catch (IOException e) {
                    log.error("IOException was occurred: " + e);
                }
            } else {
                try {
                    req.getRequestDispatcher(req.getContextPath() + view.getPath()).forward(req, resp);
                } catch (ServletException | IOException e) {
                    log.error("Error was occurred: " + e);
                }
            }
        }
        else {
            log.error(req.getParameter("action"));
            log.error(action);
        }
    }
}
