/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.core.entity.base;

import cn.hope.platform.core.entity.SuperObject;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hutianlong
 */
@Entity
@Table(name = "userlicense")
@NamedQueries({
    @NamedQuery(name = "Userlicense.findAll", query = "SELECT u FROM Userlicense u"),
    @NamedQuery(name = "Userlicense.findById", query = "SELECT u FROM Userlicense u WHERE u.id = :id"),
    @NamedQuery(name = "Userlicense.findByName", query = "SELECT u FROM Userlicense u WHERE u.name = :name"),
    @NamedQuery(name = "Userlicense.findByDescription", query = "SELECT u FROM Userlicense u WHERE u.description = :description"),
    @NamedQuery(name = "Userlicense.findByOrganizationId", query = "SELECT u FROM Userlicense u WHERE u.organizationId = :organizationId")})
public class Userlicense extends SuperObject implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    @Size(max = 2550)
    @Column(name = "Description")
    private String description;
    @Size(max = 255)
    @Column(name = "OrganizationId")
    private String organizationId;

    public Userlicense() {
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

   

    @Override
    public String toString() {
        return "cn.hope.platform.core.entity.base.Userlicense[ id=" + id + " ]";
    }
    
}
