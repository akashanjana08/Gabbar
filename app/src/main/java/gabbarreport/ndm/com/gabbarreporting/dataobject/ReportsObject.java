package gabbarreport.ndm.com.gabbarreporting.dataobject;

import java.io.Serializable;

/**
 * Created by Ndm-PC on 8/22/2016.
 */
public class ReportsObject implements Serializable
{

    String infoId;
    String areaId;
    String catId;
    String description;
    String name;
    String phone;
    String dateTime;
    String image;
    String approvalStatus;

    public ReportsObject(String infoId, String areaId, String catId, String description, String name, String phone, String dateTime, String image, String approvalStatus) {
        this.infoId = infoId;
        this.areaId = areaId;
        this.catId = catId;
        this.description = description;
        this.name = name;
        this.phone = phone;
        this.dateTime = dateTime;
        this.image = image;
        this.approvalStatus = approvalStatus;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        infoId = infoId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
