package cn.zeffect.apk.teacher.studyweather.city.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * 最简单的城市信息
 */
@Table("data_citys_info")
public class CityModel {
    public static final String COL_CITY_CODE = "citycode";
    public static final String COL_AD_CODE = "adcode";
    public static final String COL_PROVINCE = "province";
    public static final String COL_CITY = "city";
    public static final String COL_DISTRICT = "district";
    //
    @Column(COL_CITY_CODE)
    private String cityCode;
    @Column(COL_AD_CODE)
    @PrimaryKey(AssignType.BY_MYSELF)
    private String adCode;
    @Column(COL_PROVINCE)
    private String province;
    @Column(COL_CITY)
    private String city;
    @Column(COL_DISTRICT)
    private String district;


    //按ALT+INERT


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

    public String getProvince() {
        return province;
    }

    public CityModel setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CityModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public CityModel setDistrict(String district) {
        this.district = district;
        return this;
    }
}
