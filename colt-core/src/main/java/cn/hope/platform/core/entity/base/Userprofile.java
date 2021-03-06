/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.core.entity.base;

import cn.hope.platform.core.entity.SuperObject;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "userprofile")
@NamedQueries({
    @NamedQuery(name = "Userprofile.findAll", query = "SELECT u FROM Userprofile u"),
    @NamedQuery(name = "Userprofile.findById", query = "SELECT u FROM Userprofile u WHERE u.id = :id"),
    @NamedQuery(name = "Userprofile.findByName", query = "SELECT u FROM Userprofile u WHERE u.name = :name"),
    @NamedQuery(name = "Userprofile.findByDescription", query = "SELECT u FROM Userprofile u WHERE u.description = :description"),
    @NamedQuery(name = "Userprofile.findByUserLicenseId", query = "SELECT u FROM Userprofile u WHERE u.userLicenseId = :userLicenseId"),
    @NamedQuery(name = "Userprofile.findByOrganizationId", query = "SELECT u FROM Userprofile u WHERE u.organizationId = :organizationId")})
public class Userprofile extends SuperObject implements Serializable {

    private static final long serialVersionUID = 1L;
 
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    @Size(max = 2550)
    @Column(name = "Description")
    private String description;
    @Size(max = 255)
    @Column(name = "UserLicenseId")
    private String userLicenseId;
    @Size(max = 255)
    @Column(name = "OrganizationId")
    private String organizationId;
    @OneToMany(mappedBy = "userProfileId", fetch = FetchType.LAZY)
    private Collection<User> userCollection;

    public Userprofile() {
    }

  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserLicenseId() {
        return userLicenseId;
    }

    public void setUserLicenseId(String userLicenseId) {
        this.userLicenseId = userLicenseId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }



    
}
