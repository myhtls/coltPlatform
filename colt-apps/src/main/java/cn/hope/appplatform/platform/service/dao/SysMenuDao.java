/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.platform.service.dao;

import cn.hope.platform.core.entity.base.Sysmenu;
import cn.hope.platform.persistence.dao.Dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * 系统菜单
 *
 * @author hutianlong
 */
@Singleton
public class SysMenuDao extends Dao<Sysmenu> {

    private @Inject
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        this.setEntityManager(entityManager);
    }

    /**
     * 根据名称查询以及上级主键SysMenu
     *
     * @param name
     * @param parentId
     * @return
     */
    public Sysmenu findSysMenuByName(String name, Sysmenu parentId) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("parentMenuId", parentId == null ? "0" : parentId.getId());
        return this.uniqueByNamedQuery(Sysmenu.OLDNAME, Sysmenu.class, param);
    }

    /**
     * 根据父主键查询下级菜单
     *
     * @param parentId 父主键
     * @return 返回查询结果
     */
    public List<Sysmenu> findSysMenuChildren(String parentId) {
        return createNameQueryList(Sysmenu.CHILDREN, Sysmenu.class, "parentMenuId", parentId);

    }

    /**
     * 查找系统菜单根
     *
     * @return 返回查询结果
     */
    public List<Sysmenu> findSysMenuRoot() {
        return createNameQueryList(Sysmenu.ROOT, Sysmenu.class);
    }

    @Override
    public void remove(Sysmenu sysMenu) {
        createNameQuery(Sysmenu.DELETESYSMENU, Sysmenu.class)
                .setParameter("key", sysMenu.getId()).executeUpdate();
        
    }

}
