package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/9/9.
 */

public class NoticeTypeCountBean {

    /**
     * rc : 0
     * des :
     * data : [{"id":"0","name":"未读消息","icon":"","count":"0"},{"id":"3","name":"放假通知","icon":"","count":"1"},{"id":"4","name":"培训公告","icon":"","count":"1"},{"id":"5","name":"报名通知","icon":"","count":"0"}]
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
         * id : 0
         * name : 未读消息
         * icon :
         * count : 0
         */

        private String id;
        private String name;
        private String icon;
        private String count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
