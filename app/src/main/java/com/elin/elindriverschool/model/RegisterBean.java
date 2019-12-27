package com.elin.elindriverschool.model;

/**
 * Created by imac on 17/1/10.
 * 注册 Bean
 */

public class RegisterBean {

    /**
     * des :
     * data : {"coach_phone":"15098765590","coach_idnum":"370725199609299963","token":"vAJTXlWjDm3mufh76N9BZvvBhb//I7MHBKg0i5wUG7g8XaUN4p0RO9x1vqhZ BSvRujYgxaGApVtLFsT+nopymS+FNZWYxWOr","coach_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/coach/崔鑫371328199612291514.JPG","coach_name":"教练员1"}
     * rc : 0
     */

    private String des;
    /**
     * coach_phone : 15098765590
     * coach_idnum : 370725199609299963
     * token : vAJTXlWjDm3mufh76N9BZvvBhb//I7MHBKg0i5wUG7g8XaUN4p0RO9x1vqhZ BSvRujYgxaGApVtLFsT+nopymS+FNZWYxWOr
     * coach_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/coach/崔鑫371328199612291514.JPG
     * coach_name : 教练员1
     */

    private DataBean data;
    private String rc;

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

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public static class DataBean {
        private String coach_phone;
        private String coach_idnum;
        private String token;
        private String coach_photo;
        private String coach_name;

        public String getCoach_phone() {
            return coach_phone;
        }

        public void setCoach_phone(String coach_phone) {
            this.coach_phone = coach_phone;
        }

        public String getCoach_idnum() {
            return coach_idnum;
        }

        public void setCoach_idnum(String coach_idnum) {
            this.coach_idnum = coach_idnum;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCoach_photo() {
            return coach_photo;
        }

        public void setCoach_photo(String coach_photo) {
            this.coach_photo = coach_photo;
        }

        public String getCoach_name() {
            return coach_name;
        }

        public void setCoach_name(String coach_name) {
            this.coach_name = coach_name;
        }
    }
}
