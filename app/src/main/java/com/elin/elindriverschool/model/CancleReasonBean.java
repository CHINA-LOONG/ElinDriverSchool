package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/10/18.
 */

public class CancleReasonBean {

    /**
     * rc : 0
     * des :
     * data : [{"id":"4","name":"学员不能及时到场培训","status":"0","type":"2","school_id":"1"},{"id":"5","name":"学员家里有急事","status":"0","type":"2","school_id":"1"}]
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
         * id : 4
         * name : 学员不能及时到场培训
         * status : 0
         * type : 2
         * school_id : 1
         */

        private String id;
        private String name;
        private String status;
        private String type;
        private String school_id;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }
    }
}
