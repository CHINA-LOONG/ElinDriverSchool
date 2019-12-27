package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/10/30.
 */

public class StuForOrderBean {

    /**
     * rc : 0
     * des :
     * data : [{"inner_id":"69","resource_id":"38979","order_name":"梁宸","order_idnum":"371311199809062611","order_coach":"王绪昌","order_date":"2017-07-22","order_site":"临沂市科目二第十七考场（正直）","order_bus":"1","order_status":"2","order_bus_date":"2017-08-16","order_bus_addr":"4242","order_bus_num":"","order_bus_person":"undefined","order_bus_perphone":"234","order_bus_flag":"1","school_id":"1","order_sub":"5","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5959ef963d8f1.jpg","stu_phone":"15666870383"}]
     */

    private String rc;
    private String des;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * inner_id : 69
         * resource_id : 38979
         * order_name : 梁宸
         * order_idnum : 371311199809062611
         * order_coach : 王绪昌
         * order_date : 2017-07-22
         * order_site : 临沂市科目二第十七考场（正直）
         * order_bus : 1
         * order_status : 2
         * order_bus_date : 2017-08-16
         * order_bus_addr : 4242
         * order_bus_num :
         * order_bus_person : undefined
         * order_bus_perphone : 234
         * order_bus_flag : 1
         * school_id : 1
         * order_sub : 5
         * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5959ef963d8f1.jpg
         * stu_phone : 15666870383
         */

        private String inner_id;
        private String resource_id;
        private String order_name;
        private String order_idnum;
        private String order_coach;
        private String order_date;
        private String order_site;
        private String order_bus;
        private String order_status;
        private String order_bus_date;
        private String order_bus_addr;
        private String order_bus_num;
        private String order_bus_person;
        private String order_bus_perphone;
        private String order_bus_flag;
        private String school_id;
        private String order_sub;
        private String stu_photo;
        private String stu_phone;

        public String getInner_id() {
            return inner_id;
        }

        public void setInner_id(String inner_id) {
            this.inner_id = inner_id;
        }

        public String getResource_id() {
            return resource_id;
        }

        public void setResource_id(String resource_id) {
            this.resource_id = resource_id;
        }

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }

        public String getOrder_idnum() {
            return order_idnum;
        }

        public void setOrder_idnum(String order_idnum) {
            this.order_idnum = order_idnum;
        }

        public String getOrder_coach() {
            return order_coach;
        }

        public void setOrder_coach(String order_coach) {
            this.order_coach = order_coach;
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

        public String getOrder_bus() {
            return order_bus;
        }

        public void setOrder_bus(String order_bus) {
            this.order_bus = order_bus;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_bus_date() {
            return order_bus_date;
        }

        public void setOrder_bus_date(String order_bus_date) {
            this.order_bus_date = order_bus_date;
        }

        public String getOrder_bus_addr() {
            return order_bus_addr;
        }

        public void setOrder_bus_addr(String order_bus_addr) {
            this.order_bus_addr = order_bus_addr;
        }

        public String getOrder_bus_num() {
            return order_bus_num;
        }

        public void setOrder_bus_num(String order_bus_num) {
            this.order_bus_num = order_bus_num;
        }

        public String getOrder_bus_person() {
            return order_bus_person;
        }

        public void setOrder_bus_person(String order_bus_person) {
            this.order_bus_person = order_bus_person;
        }

        public String getOrder_bus_perphone() {
            return order_bus_perphone;
        }

        public void setOrder_bus_perphone(String order_bus_perphone) {
            this.order_bus_perphone = order_bus_perphone;
        }

        public String getOrder_bus_flag() {
            return order_bus_flag;
        }

        public void setOrder_bus_flag(String order_bus_flag) {
            this.order_bus_flag = order_bus_flag;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getOrder_sub() {
            return order_sub;
        }

        public void setOrder_sub(String order_sub) {
            this.order_sub = order_sub;
        }

        public String getStu_photo() {
            return stu_photo;
        }

        public void setStu_photo(String stu_photo) {
            this.stu_photo = stu_photo;
        }

        public String getStu_phone() {
            return stu_phone;
        }

        public void setStu_phone(String stu_phone) {
            this.stu_phone = stu_phone;
        }
    }
}
