package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/9/8.
 */

public class AppointAlreadyStuBean {

    /**
     * rc : 0
     * des :
     * students : [{"id":"6","record_id":"25","stu_id":"3333","order_type":"1","complete_flag":"0","status":"1","create_date":"2017-08-21 16:31:58","stu_name":"徐娟","stu_phone":"15963924632","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371327198212084929.JPG","stu_idnum":"371327198212084929"}]
     */

    private String rc;
    private String des;
    private List<StudentsBean> students;

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

    public List<StudentsBean> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsBean> students) {
        this.students = students;
    }

    public static class StudentsBean {
        /**
         * id : 6
         * record_id : 25
         * stu_id : 3333
         * order_type : 1
         * complete_flag : 0
         * status : 1
         * create_date : 2017-08-21 16:31:58
         * stu_name : 徐娟
         * stu_phone : 15963924632
         * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371327198212084929.JPG
         * stu_idnum : 371327198212084929
         */

        private String id;
        private String record_id;
        private String stu_id;
        private String order_type;
        private String complete_flag;
        private String status;
        private String create_date;
        private String stu_name;
        private String stu_phone;
        private String stu_photo;
        private String stu_idnum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRecord_id() {
            return record_id;
        }

        public void setRecord_id(String record_id) {
            this.record_id = record_id;
        }

        public String getStu_id() {
            return stu_id;
        }

        public void setStu_id(String stu_id) {
            this.stu_id = stu_id;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getComplete_flag() {
            return complete_flag;
        }

        public void setComplete_flag(String complete_flag) {
            this.complete_flag = complete_flag;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
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

        public String getStu_idnum() {
            return stu_idnum;
        }

        public void setStu_idnum(String stu_idnum) {
            this.stu_idnum = stu_idnum;
        }
    }
}
