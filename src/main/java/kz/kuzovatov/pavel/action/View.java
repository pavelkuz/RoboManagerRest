package kz.kuzovatov.pavel.action;

public class View {
    private String path;
    private boolean redirect;

    public View(String path, boolean redirect) {
            this.path = path;
            this.redirect=redirect;
    }

    public View(){}

    public boolean isRedirect() {
            return redirect;
        }

    public void setRedirect(boolean redirect) {
            this.redirect = redirect;
        }

    public String getPath(){
            return path;
        }

    public void setPath(String path) {
            this.path = path;
        }
}
