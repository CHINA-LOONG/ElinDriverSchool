package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by imac on 17/1/2.
 */

public class MyDriveStudentBean {


    /**
     * rc : 0
     * des :
     * data_list : [{"stu_cartype":"C1","stu_idnum":"530121198207183746","stu_name":"测试一","stu_phone":"18255555555","stu_reg_date":"2017-06-19","stu_photo":null,"stu_status":"2"}]
     * data_count : 1
     */

    private String rc;
    private String des;
    private String data_count;
    private List<DataListBean> data_list;

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

    public String getData_count() {
        return data_count;
    }

    public void setData_count(String data_count) {
        this.data_count = data_count;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean {
        /**
         * stu_cartype : C1
         * stu_idnum : 530121198207183746
         * stu_name : 测试一
         * stu_phone : 18255555555
         * stu_reg_date : 2017-06-19
         * stu_photo : null
         * stu_status : 2
         */

        private String stu_cartype;
        private String stu_idnum;
        private String stu_name;
        private String stu_phone;
        private String stu_reg_date;
        private Object stu_photo;
        private String stu_status;

        public String getStu_cartype() {
            return stu_cartype;
        }

        public void setStu_cartype(String stu_cartype) {
            this.stu_cartype = stu_cartype;
        }

        public String getStu_idnum() {
            return stu_idnum;
        }

        public void setStu_idnum(String stu_idnum) {
            this.stu_idnum = stu_idnum;
        }

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getStu_phone() {
            return stu_phone;
        }

        public void setStu_phone(String stu_phone) {
            this.stu_phone = stu_phone;
        }

        public String getStu_reg_date() {
            return stu_reg_date;
        }

        public void setStu_reg_date(String stu_reg_date) {
            this.stu_reg_date = stu_reg_date;
        }

        public Object getStu_photo() {
            return stu_photo;
        }

        public void setStu_photo(Object stu_photo) {
            this.stu_photo = stu_photo;
        }

        public String getStu_status() {
            return stu_status;
        }

        public void setStu_status(String stu_status) {
            this.stu_status = stu_status;
        }
    }
}
