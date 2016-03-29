package kz.kuzovatov.pavel.action;

import kz.kuzovatov.pavel.utils.ActionFinder;
import kz.kuzovatov.pavel.utils.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static final Logger log = Logger.getLogger(ActionFactory.class);
    private Map<String, Action> actions = new HashMap<>();

    public ActionFactory() {
        PropertyManager propertyManager = PropertyManager.getInstance();
        propertyManager.configure("actions.properties");
        actions = ActionFinder.getActionMap(propertyManager.getProperty("actions.path"));//like "com.carwashes.kuzovatov.actions"
        log.info("Map of actions was created successfully.");
    }

    public Action getAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        return actions.get(action);
    }
}
