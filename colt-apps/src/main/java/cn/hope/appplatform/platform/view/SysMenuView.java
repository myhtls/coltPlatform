package cn.hope.appplatform.platform.view;

import cn.hope.appplatform.exception.RenamingException;
import cn.hope.appplatform.platform.service.SysMenuService;
import cn.hope.platform.core.entity.base.Sysmenu;
import cn.hope.viewplatform.primefaces.PrimeFacesUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单 Created by myhtls on 16/5/27.
 */
@Named
@ViewScoped
public class SysMenuView implements java.io.Serializable {

    @Inject
    private transient Logger log;

    private TreeNode root;
    private Sysmenu puSysMenu;
    private Sysmenu parentPuSysMenu;

    private @Inject
    SysMenuService sysMenuService;

    private TreeNode selectedNode;

    @PostConstruct
    public void init() {
        if (!Faces.isAjaxRequest()) {
            log.debug("非ajax调用此方法");

        }
    }

    /**
     * 创建菜单
     */
    public void createOrUpdateSysMenuAction() {
        try {
            sysMenuService.createOrUpdateSysMenu(puSysMenu, parentPuSysMenu);
            PrimeFacesUtils.addCallbackParam("result", "crudOk");
            Messages.addGlobalInfo("操作成功.");
            reset();
        } catch (RenamingException re) {
            Messages.addGlobalWarn(re.getMessage());
        }
    }

    /**
     * 删除菜单
     */
    public void deletePuSysMenuAction() {
        puSysMenu = (Sysmenu) selectedNode.getData();
        sysMenuService.remove(puSysMenu);
        this.reset();
        Messages.addGlobalInfo("删除成功.");
    }

    /**
     * 清理动作
     */
    public void reset() {
        root = null;
        resultAll.clear();
        clearParentPuSysMenu();
    }

    /**
     * 清理父节点
     */
    public void clearParentPuSysMenu() {
        parentPuSysMenu = null;
        puSysMenu = null;
    }

    /**
     * 修改
     */
    public void modify() {
        puSysMenu = (Sysmenu) selectedNode.getData();
        if (puSysMenu.getParentMenuId() == null) {
            parentPuSysMenu = null;
        } else {
            parentPuSysMenu = (Sysmenu) selectedNode.getParent().getData();
        }
    }

    /**
     * 添加子节点时
     */
    public void addChildrenData() {
        parentPuSysMenu = (Sysmenu) selectedNode.getData();
    }

    private List<Sysmenu> resultAll = new ArrayList<>();

    private void buildSysmenu(Sysmenu sysmenu, TreeNode parent) {
        List<Sysmenu> result = sysMenuService.getChilde(sysmenu, resultAll);
        TreeNode treeNode = null;
        if (!result.isEmpty()) {
            for (Sysmenu sysMenu : result) {
                treeNode = new DefaultTreeNode(sysMenu, parent);
                buildSysmenu(sysMenu, treeNode);

            }
        }
    }

    /**
     * 递归创建下级所有菜单
     *
     * @param result
     * @param parent
     */
    private void createTreeNode(Collection<Sysmenu> result, TreeNode parent) {
        TreeNode treeNode = null;
        for (Sysmenu sysMenu : result) {
            //当为父节点
            if (sysMenuService.hasParentNode(sysMenu)) {
                treeNode = new DefaultTreeNode(sysMenu, parent);
                treeNode.setExpanded(true);
                buildSysmenu(sysMenu, treeNode);
            }
        }

    }

    public TreeNode getRoot() {
        if (root == null) {
            root = new DefaultTreeNode(new Sysmenu(), null);
            resultAll.addAll(sysMenuService.allSymenu());
            createTreeNode(resultAll, root);
        }
        return root;
    }

    public Sysmenu getPuSysMenu() {
        if (puSysMenu == null) {
            puSysMenu = Sysmenu.createPuSysMenu();
        }
        return puSysMenu;
    }

    public void setPuSysMenu(Sysmenu puSysMenu) {
        this.puSysMenu = puSysMenu;
    }

    public Sysmenu getParentPuSysMenu() {
        return parentPuSysMenu;
    }

    public void setParentPuSysMenu(Sysmenu parentPuSysMenu) {
        this.parentPuSysMenu = parentPuSysMenu;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
}
