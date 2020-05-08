package cn.zeffect.apk.jump.myapplication2.unit25;

import java.io.Serializable;

public class GameInfo implements Serializable {
    private String name;
    private String imgUrl;
    private String downUrl;

    public String getName() {
        return name;
    }

    public GameInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public GameInfo setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public GameInfo setDownUrl(String downUrl) {
        this.downUrl = downUrl;
        return this;
    }
}
