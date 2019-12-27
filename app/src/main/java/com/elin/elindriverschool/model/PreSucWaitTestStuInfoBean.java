package com.elin.elindriverschool.model;

/**
 * Created by imac on 17/1/5.
 * 预约成功 待考试学员详情 bean
 */

public class PreSucWaitTestStuInfoBean {

    /**
     * des :
     * data : {"order_bus":"2","order_date":"2017-04-03","order_site":"临沂第三考场","stu_cartype":"C1","stu_idnum":"370781199003010767","stu_name":"测试","stu_phone":"15098765590","stu_reg_date":"2016-12-17"}
     * rc : 0
     */

    private String des;
    /**
     * order_bus : 2
     * order_date : 2017-04-03
     * order_site : 临沂第三考场
     * stu_cartype : C1
     * stu_idnum : 370781199003010767
     * stu_name : 测试
     * stu_phone : 15098765590
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
        private String order_bus;
        private String order_date;
        private String order_site;
        private String stu_cartype;
        private String stu_idnum;
        private String stu_name;
        private String stu_phone;
        private String stu_reg_date;

        public String getOrder_bus() {
            return order_bus;
        }

        public void setOrder_bus(String order_bus) {
            this.order_bus = order_bus;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getOrder_site() {
            return order_site;
        }

        public void setOrder_site(String order_site) {
            this.order_site = order_site;
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
