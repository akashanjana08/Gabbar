package gabbarreport.ndm.com.gabbarreporting.dataobject;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class StateMaster
{
    String stateCode;
    String stateName;
    String countryCode;

    public StateMaster()
    {

    }

    public StateMaster(String stateCode, String stateName, String countryCode) {
        this.stateCode = stateCode;
        this.stateName = stateName;
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
