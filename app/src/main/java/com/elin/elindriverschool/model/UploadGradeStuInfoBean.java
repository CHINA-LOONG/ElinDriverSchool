package com.elin.elindriverschool.model;

/**
 * Created by imac on 17/1/6.
 * 上传成绩学员信息Bean
 */

public class UploadGradeStuInfoBean {


    /**
     * des :
     * data : {"order_date":"2017-01-11","order_site":"临沂第四考场"}
     * rc : 0
     */

    private String des;
    /**
     * order_date : 2017-01-11
     * order_site : 临沂第四考场
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
        private String order_date;
        private String order_site;

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
    }
}
