package cn.hope.appplatform.platform.service;

import cn.hope.appplatform.exception.RenamingException;
import cn.hope.appplatform.platform.service.dao.SysMenuDao;
import cn.hope.platform.core.entity.base.Sysmenu;
import cn.hope.platform.persistence.dao.Dao;
import cn.hope.platform.utils.UUIDGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;

/**
 * Created by myhtls on 16/5/27.
 */
@Stateless
public class SysMenuService extends Dao<Sysmenu> {

    private @Inject
    SysMenuDao sysMenuDao;

    /**
     * 判断实例
     *
     * @param parentSysMenu
     * @return
     */
    private Sysmenu isSysMenuKeyEmpty(Sysmenu parentSysMenu) {
        return parentSysMenu == null ? null : parentSysMenu;
    }

    /**
     * 判断是否有节点
     *
     * @param sysMenu
     * @return
     */
    public boolean hasChildNode(Sysmenu sysMenu) {
        Collection collection = sysMenu.getSysmenuCollection();
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断是否为root节点
     *
     * @param parentNode
     * @return
     */
    public boolean hasParentNode(Sysmenu parentNode) {
        return null == parentNode.getParentMenuId();
    }

    /**
     * 查询所有系统菜单数据
     *
     * @return
     */
    public List<Sysmenu> allSymenu() {
        return this.createNameQueryList(Sysmenu.ALL, Sysmenu.class);
    }

    public List<Sysmenu> getChilde(Sysmenu sysmenu, List<Sysmenu> resultAll) {

        List<Sysmenu> list = new ArrayList<>();
        resultAll.stream().filter((c) -> (!hasParentNode(c)
                && sysmenu.getId().equals(c.getParentMenuId().getId()))).forEach((c) -> {
                    list.add(c);
        });
        Collections.reverse(list);
        return list;

    }

    /**
     * 保存或更新
     *
     * @param sysMenu
     * @param parentSysMenu
     * @throws RenamingException
     */
    public void createOrUpdateSysMenu(Sysmenu sysMenu, Sysmenu parentSysMenu) throws RenamingException {
        Sysmenu oldSysMenu = sysMenuDao.findSysMenuByName(sysMenu.getName(), isSysMenuKeyEmpty(parentSysMenu));

        /**
         * 创建
         */
        if (sysMenu.getId() == null) {
            if (oldSysMenu != null) {
                throw new RenamingException("菜单名称已存在.");
            }
            sysMenu.setId(UUIDGenerator.getUUID());
            sysMenu.setParentMenuId(isSysMenuKeyEmpty(parentSysMenu));
            sysMenu.setIsDelete(1);
            sysMenuDao.save(sysMenu);
        } else {//编辑
            if (oldSysMenu != null && !Objects.equals(oldSysMenu.getId(), sysMenu.getId())) {
                throw new RenamingException("菜单名称已存在.");
            }
            sysMenuDao.update(sysMenu);
        }

    }

    @Override
    public void remove(Sysmenu sysMenu) {
        if (sysMenu.getId() != null) {
            sysMenuDao.remove(sysMenu);
        }
    }



    @Override
    public EntityManager getEntityManager() {
        return sysMenuDao.getEntityManager();
    }

}
