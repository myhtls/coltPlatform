 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.manager;

import cn.hope.appplatform.platform.service.UserService;
import cn.hope.platform.core.entity.base.User;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.omnifaces.util.Messages;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;

/**
 *
 * @author hutianlong
 */
@PicketLink
@RequestScoped
public class PlatformCoreAuthenticator extends BaseAuthenticator{

    private @Inject UserService userService;
    private @Inject UserPassword  userPassword;
    
    @Override
    public void authenticate() {
        User loginUser = userService.login(userPassword.getUserName(), userPassword.getPassword());
      
        if(loginUser != null){
             setStatus(AuthenticationStatus.SUCCESS);
             setAccount(new org.picketlink.idm.model.basic.User(loginUser.getUserName()));
        }else{
             setStatus(AuthenticationStatus.FAILURE);
             Messages.addGlobalWarn("登陆失败,用户名或密码错误.");
        }
    }
    
    
    
}
