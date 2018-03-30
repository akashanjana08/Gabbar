package gabbarreport.ndm.com.gabbarreporting.dataobject;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class CityMaster
{

    String cityCode;
    String cityName;
    String stateCode;

    public CityMaster()
    {}

    public CityMaster(String cityCode, String cityName, String stateCode) {
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.stateCode = stateCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
