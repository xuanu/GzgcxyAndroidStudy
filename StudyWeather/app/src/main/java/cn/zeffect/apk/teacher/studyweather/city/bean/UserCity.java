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
    public static final String COL_WEATHER = "weather";
    public static final String COL_TEMP = "temp";
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
    @Column(COL_WEATHER)
    private String weather;
    @Column(COL_TEMP)
    private String temp;


    public String getWeather() {
        if (weather == null) {
            weather = "";
        }
        return weather;
    }

    public UserCity setWeather(String weather) {
        this.weather = weather;
        return this;
    }

    public String getTemp() {
        if (temp == null) {
            temp = "";
        }
        return temp;
    }

    public UserCity setTemp(String temp) {
        this.temp = temp;
        return this;
    }

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
