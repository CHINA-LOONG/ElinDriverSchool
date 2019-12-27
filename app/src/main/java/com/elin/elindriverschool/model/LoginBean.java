package com.elin.elindriverschool.model;

/**
 * Created by imac on 16/12/30.
 * 登录后成功返回
 */

public class LoginBean {

    /**
     * rc : 0
     * des :
     * data : {"inner_id":"311","coach_name":"曾昭运","coach_sex":"1","coach_idnum":"371311199311063430","coach_drivenum":null,"coach_phone":"13552465655","coach_cartype":"C1","coach_car":"鲁Q123lv","coach_status":"1","coach_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/coach/QQ截图20170425105003.png","coach_class":"普通班","coach_password":"e10adc3949ba59abbe56e057f20f883e","coach_summary":"很棒","coach_clientid":"160a3797c83458e59e4","school_id":"1","name_py":"zzy_zengzhaoyun","login_id":"zzy000311","token":"gD1P7dCeli5FEz9fmGzGw0j34xbdtFiU/qjRxd/clHYBhknr0/xEnA=="}
     */

    private String rc;
    private String des;
    private DataBean data;

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * inner_id : 311
         * coach_name : 曾昭运
         * coach_sex : 1
         * coach_idnum : 371311199311063430
         * coach_drivenum : null
         * coach_phone : 13552465655
         * coach_cartype : C1
         * coach_car : 鲁Q123lv
         * coach_status : 1
         * coach_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/coach/QQ截图20170425105003.png
         * coach_class : 普通班
         * coach_password : e10adc3949ba59abbe56e057f20f883e
         * coach_summary : 很棒
         * coach_clientid : 160a3797c83458e59e4
         * school_id : 1
         * name_py : zzy_zengzhaoyun
         * login_id : zzy000311
         * token : gD1P7dCeli5FEz9fmGzGw0j34xbdtFiU/qjRxd/clHYBhknr0/xEnA==
         */

        private String inner_id;
        private String coach_name;
        private String coach_sex;
        private String coach_idnum;
        private Object coach_drivenum;
        private String coach_phone;
        private String coach_cartype;
        private String coach_car;
        private String coach_status;
        private String coach_photo;
        private String coach_class;
        private String coach_password;
        private String coach_summary;
        private String coach_clientid;
        private String school_id;
        private String name_py;
        private String login_id;
        private String token;

        public String getInner_id() {
            return inner_id;
        }

        public void setInner_id(String inner_id) {
            this.inner_id = inner_id;
        }

        public String getCoach_name() {
            return coach_name;
        }

        public void setCoach_name(String coach_name) {
            this.coach_name = coach_name;
        }

        public String getCoach_sex() {
            return coach_sex;
        }

        public void setCoach_sex(String coach_sex) {
            this.coach_sex = coach_sex;
        }

        public String getCoach_idnum() {
            return coach_idnum;
        }

        public void setCoach_idnum(String coach_idnum) {
            this.coach_idnum = coach_idnum;
        }

        public Object getCoach_drivenum() {
            return coach_drivenum;
        }

        public void setCoach_drivenum(Object coach_drivenum) {
            this.coach_drivenum = coach_drivenum;
        }

        public String getCoach_phone() {
            return coach_phone;
        }

        public void setCoach_phone(String coach_phone) {
            this.coach_phone = coach_phone;
        }

        public String getCoach_cartype() {
            return coach_cartype;
        }

        public void setCoach_cartype(String coach_cartype) {
            this.coach_cartype = coach_cartype;
        }

        public String getCoach_car() {
            return coach_car;
        }

        public void setCoach_car(String coach_car) {
            this.coach_car = coach_car;
        }

        public String getCoach_status() {
            return coach_status;
        }

        public void setCoach_status(String coach_status) {
            this.coach_status = coach_status;
        }

        public String getCoach_photo() {
            return coach_photo;
        }

        public void setCoach_photo(String coach_photo) {
            this.coach_photo = coach_photo;
        }

        public String getCoach_class() {
            return coach_class;
        }

        public void setCoach_class(String coach_class) {
            this.coach_class = coach_class;
        }

        public String getCoach_password() {
            return coach_password;
        }

        public void setCoach_password(String coach_password) {
            this.coach_password = coach_password;
        }

        public String getCoach_summary() {
            return coach_summary;
        }

        public void setCoach_summary(String coach_summary) {
            this.coach_summary = coach_summary;
        }

        public String getCoach_clientid() {
            return coach_clientid;
        }

        public void setCoach_clientid(String coach_clientid) {
            this.coach_clientid = coach_clientid;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getName_py() {
            return name_py;
        }

        public void setName_py(String name_py) {
            this.name_py = name_py;
        }

        public String getLogin_id() {
            return login_id;
        }

        public void setLogin_id(String login_id) {
            this.login_id = login_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
