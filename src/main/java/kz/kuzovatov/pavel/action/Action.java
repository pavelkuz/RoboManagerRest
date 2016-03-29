package kz.kuzovatov.pavel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    View execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
