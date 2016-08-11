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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hutianlong
 */
@Entity
@Table(name = "userrole")
@NamedQueries({
    @NamedQuery(name = "Userrole.findAll", query = "SELECT u FROM Userrole u"),
    @NamedQuery(name = "Userrole.findById", query = "SELECT u FROM Userrole u WHERE u.id = :id"),
    @NamedQuery(name = "Userrole.findByName", query = "SELECT u FROM Userrole u WHERE u.name = :name"),
    @NamedQuery(name = "Userrole.findByOrganizationId", query = "SELECT u FROM Userrole u WHERE u.organizationId = :organizationId")})
public class Userrole extends SuperObject implements Serializable {

    private static final long serialVersionUID = 1L;
 
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    @Size(max = 255)
    @Column(name = "OrganizationId")
    private String organizationId;
    @OneToMany(mappedBy = "parentRoleId", fetch = FetchType.LAZY)
    private Collection<Userrole> userroleCollection;
    @JoinColumn(name = "ParentRoleId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Userrole parentRoleId;
    @OneToMany(mappedBy = "userRoleId", fetch = FetchType.LAZY)
    private Collection<User> userCollection;

    public Userrole() {
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Collection<Userrole> getUserroleCollection() {
        return userroleCollection;
    }

    public void setUserroleCollection(Collection<Userrole> userroleCollection) {
        this.userroleCollection = userroleCollection;
    }

    public Userrole getParentRoleId() {
        return parentRoleId;
    }

    public void setParentRoleId(Userrole parentRoleId) {
        this.parentRoleId = parentRoleId;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

   
    
}
