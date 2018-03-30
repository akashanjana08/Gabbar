package gabbarreport.ndm.com.gabbarreporting.dataobject;

/**
 * Created by Ndm-PC on 8/18/2016.
 */
public class CategoryMasterObject
{

    String catId;
    String catName;


    public CategoryMasterObject()
    {}

    public CategoryMasterObject(String catId, String catName) {
        this.catId = catId;
        this.catName = catName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
