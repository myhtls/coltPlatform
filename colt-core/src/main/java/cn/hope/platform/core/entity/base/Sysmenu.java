/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.core.entity.base;

import cn.hope.platform.core.entity.SuperObject;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author hutianlong
 */
@Entity
@Table(name = "sysmenu")
@NamedQueries({
    @NamedQuery(name = "SysMenu.findSysMenuByRoot", query = "select sysMenu from Sysmenu sysMenu where sysMenu.parentMenuId is null and sysMenu.isDelete = 1 order by sysMenu.sequence desc"),
    @NamedQuery(name = "SysMenu.findSysMenuByParentId", query = "select sysMenu from Sysmenu sysMenu where sysMenu.parentMenuId.id=:parentMenuId and sysMenu.isDelete = 1 order by sysMenu.sequence desc"),
    @NamedQuery(name = "SysMenu.findSysMenuByName", query = "select sysMenu from Sysmenu sysMenu where sysMenu.name=:name and sysMenu.parentMenuId.id=:parentMenuId and sysMenu.isDelete = 1"),
    @NamedQuery(name = "SysMenu.DELETE", query = "UPDATE  Sysmenu sysMenu set sysMenu.isDelete=0 where sysMenu.id=:key"),
    @NamedQuery(name = "SysMenu.All", query = "select sysMenu from Sysmenu sysMenu where sysMenu.isDelete=1  order by sysMenu.sequence desc")

})
public class Sysmenu extends SuperObject implements Serializable,Cloneable {

    public final static String ROOT = "SysMenu.findSysMenuByRoot";
    public final static String CHILDREN = "SysMenu.findSysMenuByParentId";
    public final static String OLDNAME = "SysMenu.findSysMenuByName";
      public final static String ALL = "SysMenu.All";
    public final static String DELETESYSMENU = "SysMenu.DELETE";
    private static final long serialVersionUID = 1L;

    private static Sysmenu PUSYSMENU = new Sysmenu();

    /**
     * 克隆创建一个bean
     *
     * @return
     */
    public static Sysmenu createPuSysMenu() {
        try {
            return (Sysmenu) PUSYSMENU.clone();
        } catch (CloneNotSupportedException cnse) {
        }
        return null;
    }

    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    @Column(name = "Sequence")
    private Integer sequence;
    @Size(max = 2550)
    @Column(name = "Description")
    private String description;
    @Size(max = 255)
    @Column(name = "icon")
    private String icon;
    @Size(max = 255)
    @Column(name = "URL")
    private String url;
    @OrderBy(value = "sequence desc")
    @OneToMany(mappedBy = "parentMenuId", fetch = FetchType.LAZY)
    private Collection<Sysmenu> sysmenuCollection;
    @JoinColumn(name = "parentMenuId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sysmenu parentMenuId;

    public Sysmenu(String id) {
        super.setId(id);
    }

    public Sysmenu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Collection<Sysmenu> getSysmenuCollection() {
        return sysmenuCollection;
    }

    public void setSysmenuCollection(Collection<Sysmenu> sysmenuCollection) {
        this.sysmenuCollection = sysmenuCollection;
    }

    public Sysmenu getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Sysmenu parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sysmenu)) {
            return false;
        }
        Sysmenu other = (Sysmenu) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return id;
    }

}
