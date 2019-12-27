package com.elin.elindriverschool.model;

/**
 * Created by 17535 on 2017/6/16.
 */

public class UpdateVetsionBean {

    /**
     * id : 9
     * versioncode : 6
     * versionname : 11
     * loadurl : http://elindriving.oss-cn-hangzhou.aliyuncs.com//app_file/594339c4d64bd.apk
     * img_url : null
     * date : 2017-06-16 09:52:09
     * update_user : admin
     * type : null
     * description : 222
     * forceupdate : 2
     */

    private String id;
    private String versioncode;
    private String versionname;
    private String loadurl;
    private Object img_url;
    private String date;
    private String update_user;
    private Object type;
    private String description;
    private String forceupdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getLoadurl() {
        return loadurl;
    }

    public void setLoadurl(String loadurl) {
        this.loadurl = loadurl;
    }

    public Object getImg_url() {
        return img_url;
    }

    public void setImg_url(Object img_url) {
        this.img_url = img_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getForceupdate() {
        return forceupdate;
    }

    public void setForceupdate(String forceupdate) {
        this.forceupdate = forceupdate;
    }
}
