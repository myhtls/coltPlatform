package cn.hope.appplatform.manager;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.Identity;
import org.picketlink.Identity.AuthenticationResult;

/**
 * 登陆 Created by myhtls on 16/6/1.
 */
@Named
@RequestScoped
public class LoginAction {

    private @Inject
    Identity identity;

    /**
     * 登陆
     *
     * @return
     */
    public String login() {

        if (AuthenticationResult.SUCCESS.equals(identity.login())) {

            return "/manager/home.xhtml?faces-redirect=true";
        }

        return "";
    }

}
