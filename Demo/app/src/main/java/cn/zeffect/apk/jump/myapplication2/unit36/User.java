package cn.zeffect.apk.jump.myapplication2.unit36;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * 一个对象 代表：表的一行
 */
@Table("user_info")
public class User {

    public static final String COL_USER_NAME = "username";
    public static final String COL_PHONE = "phone";

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    @Column(COL_USER_NAME)
    private String userName;
    @Column(COL_PHONE)
    private String phone;

    //按ALT+INSERT 设置set和get

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
