package kz.kuzovatov.pavel.actions;

import kz.kuzovatov.pavel.action.Action;
import kz.kuzovatov.pavel.action.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return  new View("index.jsp",true);
    }
}
