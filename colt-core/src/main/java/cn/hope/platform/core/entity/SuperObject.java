package cn.hope.platform.core.entity;

import cn.hope.platform.core.entity.base.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * Created by myhtls on 16/7/2.
 */
@MappedSuperclass
public abstract class SuperObject implements Serializable {

    private static final long serialVersionUID = 8915767964901493670L;

    @NotNull
    @Id
     @Column(name = "Id")
    protected String id;
    @Version
    @Column(name = "version")
    protected Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy")
    protected User createdBy;
    @Column(name = "createdDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date createdDate;
    @JoinColumn(name = "lastModifiedBy")
    @ManyToOne(fetch = FetchType.LAZY)
    protected User lastModifiedBy;

    @Column(name = "lastModifiedDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date lastModifiedDate;
    @JoinColumn(name = "ownerId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User ownerId;
    
    /**
     * 逻辑删除 0 删除,1保留
     */
    @Column(name = "isDelete")
    private int isDelete;

    public SuperObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final SuperObject other = (SuperObject) obj;
        return Objects.equals(this.id, other.id);
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }


    
    

}
