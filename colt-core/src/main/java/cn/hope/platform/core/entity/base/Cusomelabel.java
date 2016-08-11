/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.core.entity.base;

import cn.hope.platform.core.entity.SuperObject;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author hutianlong
 */
@Entity
@Table(name = "cusomelabel")
@NamedQueries({
    @NamedQuery(name = "Cusomelabel.findAll", query = "SELECT c FROM Cusomelabel c"),
    @NamedQuery(name = "Cusomelabel.findById", query = "SELECT c FROM Cusomelabel c WHERE c.id = :id"),
    @NamedQuery(name = "Cusomelabel.findByName", query = "SELECT c FROM Cusomelabel c WHERE c.name = :name"),
    @NamedQuery(name = "Cusomelabel.findByValue", query = "SELECT c FROM Cusomelabel c WHERE c.value = :value"),
    @NamedQuery(name = "Cusomelabel.findByCategory", query = "SELECT c FROM Cusomelabel c WHERE c.category = :category"),
    @NamedQuery(name = "Cusomelabel.findByDescription", query = "SELECT c FROM Cusomelabel c WHERE c.description = :description"),
    @NamedQuery(name = "Cusomelabel.findByOrganizationId", query = "SELECT c FROM Cusomelabel c WHERE c.organizationId = :organizationId")})
public class Cusomelabel extends SuperObject implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    @Size(max = 255)
    @Column(name = "Value")
    private String value;
    @Size(max = 255)
    @Column(name = "Category")
    private String category;
    @Size(max = 2550)
    @Column(name = "Description")
    private String description;
    @Size(max = 255)
    @Column(name = "OrganizationId")
    private String organizationId;

    public Cusomelabel() {
    }

    public Cusomelabel(String id) {
        this.id = id;
    }

   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cusomelabel other = (Cusomelabel) obj;
        return Objects.equals(this.id, other.id);
    }

  

    @Override
    public String toString() {
        return "cn.hope.platform.core.entity.base.Cusomelabel[ id=" + id + " ]";
    }
    
}
