package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/9/8.
 */

public class TrainScheduleListBean {

    /**
     * rc : 0
     * des :
     * data : {"train_sub":"3","can_publish":true,"dates":[{"date":"2017-11-28","is_all":"1","has_forenoon":"1","has_afternoon":"1"},{"date":"2017-11-29","is_all":"1","has_forenoon":"1","has_afternoon":"1"},{"date":"2017-11-30","is_all":"1","has_forenoon":"1","has_afternoon":"1"},{"date":"2017-12-01","is_all":"1","has_forenoon":"1","has_afternoon":"1"},{"date":"2017-12-02","is_all":"1","has_forenoon":"1","has_afternoon":"1"}]}
     */

    private String rc;
    private String des;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * train_sub : 3
         * can_publish : true
         * dates : [{"date":"2017-11-28","is_all":"1","has_forenoon":"1","has_afternoon":"1"},{"date":"2017-11-29","is_all":"1","has_forenoon":"1","has_afternoon":"1"},{"date":"2017-11-30","is_all":"1","has_forenoon":"1","has_afternoon":"1"},{"date":"2017-12-01","is_all":"1","has_forenoon":"1","has_afternoon":"1"},{"date":"2017-12-02","is_all":"1","has_forenoon":"1","has_afternoon":"1"}]
         */

        private String train_sub;
        private boolean can_publish;
        private List<DatesBean> dates;

        public String getTrain_sub() {
            return train_sub;
        }

        public void setTrain_sub(String train_sub) {
            this.train_sub = train_sub;
        }

        public boolean isCan_publish() {
            return can_publish;
        }

        public void setCan_publish(boolean can_publish) {
            this.can_publish = can_publish;
        }

        public List<DatesBean> getDates() {
            return dates;
        }

        public void setDates(List<DatesBean> dates) {
            this.dates = dates;
        }

        public static class DatesBean {
            /**
             * date : 2017-11-28
             * is_all : 1
             * has_forenoon : 1
             * has_afternoon : 1
             */

            private String date;
            private String is_all;
            private String has_forenoon;
            private String has_afternoon;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getIs_all() {
                return is_all;
            }

            public void setIs_all(String is_all) {
                this.is_all = is_all;
            }

            public String getHas_forenoon() {
                return has_forenoon;
            }

            public void setHas_forenoon(String has_forenoon) {
                this.has_forenoon = has_forenoon;
            }

            public String getHas_afternoon() {
                return has_afternoon;
            }

            public void setHas_afternoon(String has_afternoon) {
                this.has_afternoon = has_afternoon;
            }
        }
    }
}
