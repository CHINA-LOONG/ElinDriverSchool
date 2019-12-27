package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/10/31.
 */

public class SubmitReasonBean {

    /**
     * rc : 0
     * des :
     * data : [{"id":"1","content":"很好","level":null,"school_id":"1"},{"id":"4","content":"一般","level":null,"school_id":"1"}]
     */

    private int rc;
    private String des;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * content : 很好
         * level : null
         * school_id : 1
         */

        private String id;
        private String content;
        private Object level;
        private String school_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }
    }
}
