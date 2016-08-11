package cn.hope.platform.core.entity.platform;



import cn.hope.platform.core.entity.SuperObject;

import javax.persistence.*;
import java.util.Collection;

/**
 *定制化－创建－应用tab项
 * @author Javen
 */
@NamedQueries({
        @NamedQuery(name = "AppTabs.findAll",query = "select tab from AppTabs tab order by tab.labelName desc")
})
@Entity
@Table(name = "appTabs")
public class AppTabs  extends SuperObject {

    public final static String FINDALL = "AppTabs.findAll";


    private String labelName;
    private String apiName;
    private String tabType;

    @OneToOne(fetch = FetchType.LAZY)
    private CustomeObject customeObject;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appTabsId")
    private Collection<ApplicationTabs> applicationTabsCollection;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }


    public CustomeObject getCustomeObject() {
        return customeObject;
    }

    public void setCustomeObject(CustomeObject customeObject) {
        this.customeObject = customeObject;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }

    public Collection<ApplicationTabs> getApplicationTabsCollection() {
        return applicationTabsCollection;
    }

    public void setApplicationTabsCollection(Collection<ApplicationTabs> applicationTabsCollection) {
        this.applicationTabsCollection = applicationTabsCollection;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof AppTabs) && (id != null)
                ? id.equals(((AppTabs) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
    
}
