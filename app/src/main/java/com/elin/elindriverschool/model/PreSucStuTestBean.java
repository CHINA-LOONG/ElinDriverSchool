package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by imac on 17/1/4.
 * 预约成功 待考试 学员Bean
 */

public class PreSucStuTestBean {


    /**
     * rc : 0
     * des :
     * data_list : [{"stu_name":"刘媛","stu_idnum":"371302199401203445","stu_phone":"15266688153","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371302199401203445.jpg","order_date":"2017-09-11","order_site":"临沂市科目二第二考场（兰陵大队）","order_bus":"1","inner_id":"","order_sub":""},{"stu_name":"陈欣雪","stu_idnum":"371302198505204624","stu_phone":"13515394397","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371302198505204624.jpg","order_date":"2017-09-11","order_site":"临沂市科目二第二考场（兰陵大队）","order_bus":"1"},{"stu_name":"高卫星","stu_idnum":"37280119731226162X","stu_phone":"13697817735","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/37280119731226162x.jpg","order_date":"2017-09-11","order_site":"临沂市科目二第二考场（兰陵大队）","order_bus":"1"},{"stu_name":"张广玉","stu_idnum":"371322199403308318","stu_phone":"15006499520","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371322199403308318.jpg","order_date":"2017-09-11","order_site":"临沂市科目二第二考场（兰陵大队）","order_bus":"1"}]
     */

    private String rc;
    private String des;
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

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean {
        /**
         * stu_name : 刘媛
         * stu_idnum : 371302199401203445
         * stu_phone : 15266688153
         * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371302199401203445.jpg
         * order_date : 2017-09-11
         * order_site : 临沂市科目二第二考场（兰陵大队）
         * order_bus : 1
         * inner_id :
         * order_sub :
         */

        private String stu_name;
        private String stu_idnum;
        private String stu_phone;
        private String stu_photo;
        private String order_date;
        private String order_site;
        private String order_bus;
        private String inner_id;
        private String order_sub;

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getStu_idnum() {
            return stu_idnum;
        }

        public void setStu_idnum(String stu_idnum) {
            this.stu_idnum = stu_idnum;
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

        public String getInner_id() {
            return inner_id;
        }

        public void setInner_id(String inner_id) {
            this.inner_id = inner_id;
        }

        public String getOrder_sub() {
            return order_sub;
        }

        public void setOrder_sub(String order_sub) {
            this.order_sub = order_sub;
        }
    }
}
