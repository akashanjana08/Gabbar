package gabbarreport.ndm.com.gabbarreporting.dataobject;

/**
 * Created by Ndm-PC on 8/18/2016.
 */
public class AreaMasterObject
{

    String areaCode;
    String areaName;
    String cityCode;

    public AreaMasterObject()
    {}

    public AreaMasterObject(String areaCode, String areaName, String cityCode)
    {
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areacityCode) {
        this.areaCode = areacityCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
