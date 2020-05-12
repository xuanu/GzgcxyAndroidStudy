package cn.zeffect.apk.teacher.studyweather.city.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

@Table("user_add_city")
public class UserCity implements Serializable {
    public static final String COL_AD_CODE = "adcode";
    public static final String COL_CITY_MODEL = "city";
    public static final String COL_CITY_NAME = "cityname";
    public static final String COL_TYPE = "type";
    //
    public static final String TYPE_LOCATION = "location";
    public static final String TYPE_USER_ADD = "user_add";
    //
    @Column(COL_AD_CODE)
    @PrimaryKey(AssignType.BY_MYSELF)
    private String adcode;
    @Column(COL_CITY_NAME)
    private String cityname;
    @Column(COL_TYPE)
    private String type;


    public String getAdcode() {
        return adcode;
    }

    public UserCity setAdcode(String adcode) {
        this.adcode = adcode;
        return this;
    }

    public String getCityname() {
        return cityname;
    }

    public UserCity setCityname(String cityname) {
        this.cityname = cityname;
        return this;
    }

    public String getType() {
        return type;
    }

    public UserCity setType(String type) {
        this.type = type;
        return this;
    }
}
