package kz.kuzovatov.pavel.utils;

import kz.kuzovatov.pavel.action.Action;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ActionFinder {
    private static Logger log = Logger.getLogger(ActionFinder.class);
    private static Map<String, Action> actionMap;

    public ActionFinder(Map<String, Action> actionMap) {
        ActionFinder.actionMap = actionMap;
    }

    public ActionFinder() {
    }

    /**
     *
     * @param packageName Must be like "com.epam.kuzovatov.XXXXXXXX", where XXXXXXXX is a package to search
     * @return Founded classes in selected package
     */
    public static List<Class> getClasses(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = null;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            log.error("IOException was occurred: " + e);
        }
        List<File> dirs = new ArrayList<>();
        assert resources != null;
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    /**
     * Recursive method to find classes in dir + subdir
     *
     * @param directory   The base directory
     * @param packageName The package name, like "com.epam.kuzovatov.XXXXXXXX" where XXXXXXXX is a package name to search
     * @return The classes
     */
    public static List<Class> findClasses(File directory, String packageName){
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                try {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    log.error("ClassNotFoundException was occurred: "+ e);
                }
            }
        }
        return classes;
    }

    /**
     * Method for getting actions in chosen package.
     * @param packageName like "com.epam.kuzovatov.XXXXXXXX"
     * @return Map of actions assigned on com.carwashes.kuzovatov.action name
     */
    public static Map<String, Action> getActionMap(String packageName){
        List<Class> classList = getClasses(packageName);
        actionMap = new HashMap<>();
        for (Class<?> clazz : classList){
            try {
                actionMap.put(clazz.getName().substring(packageName.length()+1, clazz.getName().length()), (Action) clazz.newInstance());
                log.info("Action name : "+clazz.getName().substring(packageName.length()+1, clazz.getName().length())+" - Action: "+clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("InstantiationException or IllegalAccessException was occurred: "+e);
            }
        }
        return actionMap;
    }

    public Package getCurrentPackage(Class clazz){
        return clazz.getPackage();
    }
}
