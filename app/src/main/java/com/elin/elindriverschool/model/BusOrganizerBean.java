package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/9/16.
 */

public class BusOrganizerBean {

    /**
     * rc : 0
     * des :
     * data : [{"inner_id":"54","user_name":"曹广军","sex":"男","user_phone":"18953978952","user_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/user/曹广军.JPG"}]
     */

    private int rc;
    private String des;
    private List<DataBean> data;

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * inner_id : 54
         * user_name : 曹广军
         * sex : 男
         * user_phone : 18953978952
         * user_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/user/曹广军.JPG
         */

        private String inner_id;
        private String user_name;
        private String sex;
        private String user_phone;
        private String user_photo;

        public String getInner_id() {
            return inner_id;
        }

        public void setInner_id(String inner_id) {
            this.inner_id = inner_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_photo() {
            return user_photo;
        }

        public void setUser_photo(String user_photo) {
            this.user_photo = user_photo;
        }
    }
}
