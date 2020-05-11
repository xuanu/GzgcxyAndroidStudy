package cn.zeffect.apk.teacher.studyweather.city.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 最简单的城市信息
 */
@Table("data_citys_info")
public class CityModel implements Serializable {
    public static final String COL_CITY_CODE = "citycode";
    public static final String COL_AD_CODE = "adcode";
    public static final String COL_PROVINCE = "province";
    public static final String COL_CITY = "city";
    public static final String COL_DISTRICT = "district";
    public static final String COL_NAM = "name";
    public static final String COL_CENTER = "center";
    public static final String COL_LEVEL = "level";
    //
    @Column(COL_CITY_CODE)
    private String cityCode;
    @Column(COL_AD_CODE)
    @PrimaryKey(AssignType.BY_MYSELF)
    private String adCode;
    @Column(COL_NAM)
    private String name;
    @Column(COL_CENTER)
    private String centerPoint;
    @Column(COL_DISTRICT)
    private ArrayList<CityModel> districts;
    @Column(COL_LEVEL)
    private String level;


    //按ALT+INERT


    public String getLevel() {
        return level;
    }

    public CityModel setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getCenterPoint() {
        return centerPoint;
    }

    public CityModel setCenterPoint(String centerPoint) {
        this.centerPoint = centerPoint;
        return this;
    }

    public ArrayList<CityModel> getDistricts() {
        return districts;
    }

    public CityModel setDistricts(ArrayList<CityModel> districts) {
        this.districts = districts;
        return this;
    }

    public String getCityCode() {
        return cityCode;
    }

    public CityModel setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public String getAdCode() {
        return adCode;
    }

    public CityModel setAdCode(String adCode) {
        this.adCode = adCode;
        return this;
    }

}
