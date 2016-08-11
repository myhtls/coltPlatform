package cn.hope.platform.core.entity.platform;



import cn.hope.platform.core.entity.SuperObject;

import javax.persistence.*;

/**
 * 自定义字段
 * @author Javen
 */
@Entity
@Table(name = "customField")
public class CustomField extends SuperObject {

    private String labelName;
    private String apiName;
    private String Description;
    /**
     * 1:是  0:否
     */
    private String isStandard;

    /**
     * 1：是  0:否
     */
    private String isDeployed;

    @OneToOne
    @JoinColumn(name = "customeFieldType_id")
    private CustomeFieldType customeFieldType;

    @ManyToOne
    @JoinColumn(name = "customeObject_id")
    private CustomeObject customeObject;


    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public CustomeFieldType getCustomeFieldType() {
        return customeFieldType;
    }

    public void setCustomeFieldType(CustomeFieldType customeFieldType) {
        this.customeFieldType = customeFieldType;
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

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public CustomeObject getCustomeObject() {
        return customeObject;
    }

    public void setCustomeObject(CustomeObject customeObject) {
        this.customeObject = customeObject;
    }


}
