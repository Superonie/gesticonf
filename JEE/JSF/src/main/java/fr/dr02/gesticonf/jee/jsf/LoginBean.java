package fr.dr02.gesticonf.jee.jsf;

/**
 * Created by damien on 15/02/14.
 */
import fr.dr02.gesticonf.jee.ejb.UserManager;
import fr.dr02.gesticonf.jpa.UserEntity;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@Stateless
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    UserManager userManager;

    private static final long serialVersionUID = 1L;
    private String password;
    private String message="", uname="", oldPass="", newPass="";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getOldPass() { return oldPass; }

    public void setOldPass(String oldPass) { this.oldPass = oldPass; }

    public String getNewPass() { return newPass; }

    public void setNewPass(String newPass) { this.newPass = newPass; }

    public String loginProject() {

        UserEntity u = userManager.findAdmin();

        boolean result = password.equals(u.getPassword());
        if (result) {
            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);

            return "home";
        } else {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Invalid Login!",
                            "Please Try Again!"));

            // invalidate session, and redirect to other pages

            //message = "Invalid Login. Please Try Again!";
            return "login";
        }
    }

    public boolean isAdmin() {
        HttpSession session = Util.getSession();
        if ( session == null )
            return false;
        else if ( session.getAttribute("username") == null )
            return false;
        else
            return session.getAttribute("username").equals("admin");
    }

    public String logout() {
        HttpSession session = Util.getSession();
        session.invalidate();
        return "login";
    }

    public void changeMDP() {
        UserEntity u = userManager.findAdmin();

        if ( oldPass.equals(u.getPassword()) ) {

            u.setPassword(newPass);
            userManager.update(u);
        } else
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Invalid Login!",
                            "Please Try Again!"));
    }
}
