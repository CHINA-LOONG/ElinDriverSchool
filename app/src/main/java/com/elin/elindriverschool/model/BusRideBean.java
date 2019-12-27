package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/8/16.
 */

public class BusRideBean {


    /**
     * rc : 0
     * des :
     * data : [{"order_bus_num":"鲁Q888888","order_bus_date":"2018-07-26 13:13:00","order_bus_person":"曹广军","order_bus_perphone":"12345678","order_bus_addr":"总校","order_sub":"5","order_stu_count":"1"},{"order_bus_num":"鲁Q888888","order_bus_date":"2018-07-26 13:44:59","order_bus_person":"曹广军","order_bus_perphone":"12345678;","order_bus_addr":"总校","order_sub":"5","order_stu_count":"1"},{"order_bus_num":"鲁Q888888","order_bus_date":"2018-07-26 13:48:20","order_bus_person":"陈学中","order_bus_perphone":"12345678","order_bus_addr":"总校","order_sub":"5","order_stu_count":"1"},{"order_bus_num":"鹅鹅鹅鹅鹅鹅","order_bus_date":"2018-07-26 13:07:54","order_bus_person":"曹广军","order_bus_perphone":"12345678","order_bus_addr":"总校","order_sub":"5","order_stu_count":"1"}]
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
         * order_bus_num : 鲁Q888888
         * order_bus_date : 2018-07-26 13:13:00
         * order_bus_person : 曹广军
         * order_bus_perphone : 12345678
         * order_bus_addr : 总校
         * order_sub : 5
         * order_stu_count : 1
         */

        private String order_bus_num;
        private String order_bus_date;
        private String order_bus_person;
        private String order_bus_perphone;
        private String order_bus_addr;
        private String order_sub;
        private String order_stu_count;

        public String getOrder_bus_num() {
            return order_bus_num;
        }

        public void setOrder_bus_num(String order_bus_num) {
            this.order_bus_num = order_bus_num;
        }

        public String getOrder_bus_date() {
            return order_bus_date;
        }

        public void setOrder_bus_date(String order_bus_date) {
            this.order_bus_date = order_bus_date;
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

        public String getOrder_bus_addr() {
            return order_bus_addr;
        }

        public void setOrder_bus_addr(String order_bus_addr) {
            this.order_bus_addr = order_bus_addr;
        }

        public String getOrder_sub() {
            return order_sub;
        }

        public void setOrder_sub(String order_sub) {
            this.order_sub = order_sub;
        }

        public String getOrder_stu_count() {
            return order_stu_count;
        }

        public void setOrder_stu_count(String order_stu_count) {
            this.order_stu_count = order_stu_count;
        }
    }
}
