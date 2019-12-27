package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by imac on 17/1/3.
 */

public class WaitTestStuBean {

    /**
     * des :
     * data_list : [{"stu_cartype":"C1","stu_idnum":"370781199003010767","stu_name":"测试","stu_phone":"15098765590","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/赵蕾371322199811158822.JPG","stu_reg_date":"2016-12-17"}]
     * rc : 0
     */

    private String des;
    private int rc;
    /**
     * stu_cartype : C1
     * stu_idnum : 370781199003010767
     * stu_name : 测试
     * stu_phone : 15098765590
     * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/赵蕾371322199811158822.JPG
     * stu_reg_date : 2016-12-17
     */

    private List<DataListBean> data_list;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean {
        private String stu_cartype;
        private String stu_idnum;
        private String stu_name;
        private String stu_phone;
        private String stu_photo;
        private String stu_reg_date;

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

        public String getStu_photo() {
            return stu_photo;
        }

        public void setStu_photo(String stu_photo) {
            this.stu_photo = stu_photo;
        }

        public String getStu_reg_date() {
            return stu_reg_date;
        }

        public void setStu_reg_date(String stu_reg_date) {
            this.stu_reg_date = stu_reg_date;
        }
    }
}
