package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/10/31.
 */

public class StudentVisitBean {


    /**
     * rc : 0
     * des :
     * data : [{"id":"1","stu_id":"1","coach_id":"184","create_date":"2017-10-31","return_date":null,"return_content":null,"node":"1","status":"1","stu_name":"彭东方","stu_idnum":"371302199306250411","stu_phone":"18953935316","coach_name":"张应龙","branch_no":"1","stu_photo":"ddddd","school_id":"1"}]
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
         * stu_id : 1
         * coach_id : 184
         * create_date : 2017-10-31
         * return_date : null
         * return_content : null
         * node : 1
         * status : 1
         * stu_name : 彭东方
         * stu_idnum : 371302199306250411
         * stu_phone : 18953935316
         * coach_name : 张应龙
         * branch_no : 1
         * stu_photo : ddddd
         * school_id : 1
         */

        private String id;
        private String stu_id;
        private String coach_id;
        private String create_date;
        private Object return_date;
        private Object return_content;
        private String node;
        private String status;
        private String stu_name;
        private String stu_idnum;
        private String stu_phone;
        private String coach_name;
        private String branch_no;
        private String stu_photo;
        private String school_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStu_id() {
            return stu_id;
        }

        public void setStu_id(String stu_id) {
            this.stu_id = stu_id;
        }

        public String getCoach_id() {
            return coach_id;
        }

        public void setCoach_id(String coach_id) {
            this.coach_id = coach_id;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public Object getReturn_date() {
            return return_date;
        }

        public void setReturn_date(Object return_date) {
            this.return_date = return_date;
        }

        public Object getReturn_content() {
            return return_content;
        }

        public void setReturn_content(Object return_content) {
            this.return_content = return_content;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

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

        public String getCoach_name() {
            return coach_name;
        }

        public void setCoach_name(String coach_name) {
            this.coach_name = coach_name;
        }

        public String getBranch_no() {
            return branch_no;
        }

        public void setBranch_no(String branch_no) {
            this.branch_no = branch_no;
        }

        public String getStu_photo() {
            return stu_photo;
        }

        public void setStu_photo(String stu_photo) {
            this.stu_photo = stu_photo;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }
    }
}
