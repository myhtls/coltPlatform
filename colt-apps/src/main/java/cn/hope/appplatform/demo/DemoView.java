/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.demo;

import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author hutianlong
 */
@Named
@ViewScoped
public class DemoView implements java.io.Serializable{
    
    private @Inject DemoUserService demoUserService;
    
    public void testAction(){
        Long count = demoUserService.count();
        System.err.println(count);
    }
    
}
