package com.elin.elindriverschool.model;

/**
 * Created by imac on 17/1/5.
 * 预约成功 待处理学员详情Bean
 */

public class PreSucOptionStuInfoBean {

    /**
     * des :
     * data : {"detarec_order_bus":"2","detarec_order_date":"2017-01-03","detarec_order_site":"临沂第四考场","stu_cartype":"C1","stu_idnum":"370781199003010766","stu_name":"张鹏","stu_phone":"15621789870","stu_reg_date":"2016-12-17"}
     * rc : 0
     */

    private String des;
    /**
     * detarec_order_bus : 2
     * detarec_order_date : 2017-01-03
     * detarec_order_site : 临沂第四考场
     * stu_cartype : C1
     * stu_idnum : 370781199003010766
     * stu_name : 张鹏
     * stu_phone : 15621789870
     * stu_reg_date : 2016-12-17
     */

    private DataBean data;
    private int rc;

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

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public static class DataBean {
        private String detarec_order_bus;
        private String detarec_order_date;
        private String detarec_order_site;
        private String stu_cartype;
        private String stu_idnum;
        private String stu_name;
        private String stu_phone;
        private String stu_reg_date;

        public String getDetarec_order_bus() {
            return detarec_order_bus;
        }

        public void setDetarec_order_bus(String detarec_order_bus) {
            this.detarec_order_bus = detarec_order_bus;
        }

        public String getDetarec_order_date() {
            return detarec_order_date;
        }

        public void setDetarec_order_date(String detarec_order_date) {
            this.detarec_order_date = detarec_order_date;
        }

        public String getDetarec_order_site() {
            return detarec_order_site;
        }

        public void setDetarec_order_site(String detarec_order_site) {
            this.detarec_order_site = detarec_order_site;
        }

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
    }
}
