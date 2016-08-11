/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.core.entity.platform;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author myhtls
 */
@Entity
@Table(name = "applicationTabs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ApplicationTabs.findAll", query = "SELECT a FROM ApplicationTabs a"),
    @NamedQuery(name = "ApplicationTabs.findById", query = "SELECT a FROM ApplicationTabs a WHERE a.id = :id")
})
public class ApplicationTabs implements Serializable {
    public static final String REMOVEBYAPPIDANDAPPTABID = "delete from ApplicationTabs aats where aats.applicationId.id=:appId and aats.appTabsId.id=:atId";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "applicationId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Application applicationId;
    @JoinColumn(name = "appTabsId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AppTabs appTabsId;

    public ApplicationTabs() {
    }

    public ApplicationTabs(Long id) {
        this.id = id;
    }

    public ApplicationTabs(Application applicationId, AppTabs appTabsId) {
        this.applicationId = applicationId;
        this.appTabsId = appTabsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Application getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Application applicationId) {
        this.applicationId = applicationId;
    }

    public AppTabs getAppTabsId() {
        return appTabsId;
    }

    public void setAppTabsId(AppTabs appTabsId) {
        this.appTabsId = appTabsId;
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
        if (!(object instanceof ApplicationTabs)) {
            return false;
        }
        ApplicationTabs other = (ApplicationTabs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.hxdev.platform.ApplicationTabs[ id=" + id + " ]";
    }
    
}
