/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.manager;

import cn.hope.appplatform.platform.service.SysMenuService;
import cn.hope.platform.core.entity.base.Sysmenu;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang.StringUtils;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

/**
 * 用户登陆系统菜单
 *
 * @author hutianlong
 */
@Named
@SessionScoped
public class LoginUserSysMenu implements java.io.Serializable {

    private @Inject
    SysMenuService sysMenuService;

    private MenuModel model;

    /**
     * 创建一个菜单项
     *
     * @param sysMenu
     * @param menuElement
     */
    private void createMenuItem(Sysmenu sysMenu, DefaultSubMenu menuElement) {
        DefaultMenuItem dmi = new DefaultMenuItem(sysMenu.getName());
        dmi.setIcon(sysMenu.getIcon());
        dmi.setUrl(sysMenu.getUrl());
        menuElement.addElement(dmi);
    }

   private  List<Sysmenu> resultAll = new ArrayList<>();

    private void buildSysmenu(Sysmenu sysmenu, DefaultSubMenu parent) {
        List<Sysmenu> result = sysMenuService.getChilde(sysmenu, resultAll);
        DefaultSubMenu treeNode = null;
        if (!result.isEmpty()) {
            for (Sysmenu sysMenu : result) {
                if (StringUtils.isEmpty(sysMenu.getUrl())) {
                    treeNode = new DefaultSubMenu(sysMenu.getName());
                    parent.addElement(treeNode);
                    buildSysmenu(sysMenu, treeNode);
                } else {
                    createMenuItem(sysMenu, parent);
                }

            }
        }
    }

    /**
     * 递归创建下级所有菜单
     *
     * @param result
     * @param parent
     */
    private void createMenu(Collection<Sysmenu> result, MenuModel parent) {
        DefaultSubMenu treeNode = null;
        for (Sysmenu sysMenu : result) {
            //当为父节点
            if (sysMenuService.hasParentNode(sysMenu)) {
                treeNode = new DefaultSubMenu(sysMenu.getName());
                parent.addElement(treeNode);
                buildSysmenu(sysMenu, treeNode);
            }
        }

    }

    public MenuModel getModel() {
        if (model == null) {
            model = new DefaultMenuModel();
            resultAll.addAll(sysMenuService.allSymenu());
            createMenu(resultAll, model);
        }
        return model;
    }

}
