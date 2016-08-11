package cn.hope.platform.core.entity.platform;



import cn.hope.platform.core.entity.SuperObject;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Javen
 */
@Entity
@Table(name = "customeFieldType")
public class CustomeFieldType extends SuperObject {

    private String apiName;
    private String Description;
    /**
     * 1：是  0:否
     */
    private String isDeployed;

    @OneToOne
    private CustomField customField;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIsDeployed() {
        return isDeployed;
    }

    public void setIsDeployed(String isDeployed) {
        this.isDeployed = isDeployed;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }
}
