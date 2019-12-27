package com.elin.elindriverschool.model;

/**
 * Created by 17535 on 2017/10/18.
 */

public class GetBeforeDayBean {


    /**
     * rc : 0
     * des :
     * data : {"day":"5","minDay":"5"}
     */

    private int rc;
    private String des;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * day : 5
         * minDay : 5
         */

        private String day;
        private String minDay;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getMinDay() {
            return minDay;
        }

        public void setMinDay(String minDay) {
            this.minDay = minDay;
        }
    }
}
