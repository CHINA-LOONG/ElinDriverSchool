package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/5/31.
 */

public class ToAcceptBean {

    /**
     * rc : 0
     * des :
     * data_list : [{"stu_name":"张广昊","stu_cartype":"C1","stu_idnum":"371311199709242316","stu_phone":"15020360619","stu_reg_date":"2017-05-28","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/张广昊371311199709242316.jpg","stu_detarec_predate":"2018-01-01","stu_detarec_wait_flag":"101","stu_exam_wait_flag":"201","stu_detarec_remind_flag":"2"},{"stu_name":"龚浩","stu_cartype":"C1","stu_idnum":"37283319970624091X","stu_phone":"15092952371","stu_reg_date":"2017-06-27","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/595364cb6aaad.jpg","stu_detarec_predate":"","stu_detarec_wait_flag":"101","stu_exam_wait_flag":"201","stu_detarec_remind_flag":"2"},{"stu_name":"李依洁","stu_cartype":"C1","stu_idnum":"371329198108272121","stu_phone":"13953972898","stu_reg_date":"2017-06-29","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5954a9f537b32.jpg","stu_detarec_predate":"2018-01-01","stu_detarec_wait_flag":"101","stu_exam_wait_flag":"201","stu_detarec_remind_flag":"2"},{"stu_name":"高明霞","stu_cartype":"C1","stu_idnum":"372822197703201722","stu_phone":"13406841218","stu_reg_date":"2017-06-29","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5954a9d6a0455.jpg","stu_detarec_predate":"","stu_detarec_wait_flag":"101","stu_exam_wait_flag":"201","stu_detarec_remind_flag":"2"},{"stu_name":"臧书兰","stu_cartype":"C1","stu_idnum":"371329198201155122","stu_phone":"15305495503","stu_reg_date":"2017-06-29","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5954a9bf3889a.jpg","stu_detarec_predate":"","stu_detarec_wait_flag":"101","stu_exam_wait_flag":"201","stu_detarec_remind_flag":"2"},{"stu_name":"陈孝红","stu_cartype":"C1","stu_idnum":"371327198409200647","stu_phone":"15953959620","stu_reg_date":"2017-06-29","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5954a96f2a956.jpg","stu_detarec_predate":"","stu_detarec_wait_flag":"101","stu_exam_wait_flag":"201","stu_detarec_remind_flag":"2"}]
     * next_detarec_date : 2018-01-14
     */

    private String rc;
    private String des;
    private String next_detarec_date;
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

    public String getNext_detarec_date() {
        return next_detarec_date;
    }

    public void setNext_detarec_date(String next_detarec_date) {
        this.next_detarec_date = next_detarec_date;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean {
        /**
         * stu_name : 张广昊
         * stu_cartype : C1
         * stu_idnum : 371311199709242316
         * stu_phone : 15020360619
         * stu_reg_date : 2017-05-28
         * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/张广昊371311199709242316.jpg
         * stu_detarec_predate : 2018-01-01
         * stu_detarec_wait_flag : 101
         * stu_exam_wait_flag : 201
         * stu_detarec_remind_flag : 2
         * inner_id:""
         */

        private String stu_name;
        private String stu_cartype;
        private String stu_idnum;
        private String stu_phone;
        private String stu_reg_date;
        private String stu_photo;
        private String stu_detarec_predate;
        private String stu_detarec_wait_flag;
        private String stu_exam_wait_flag;
        private String stu_detarec_remind_flag;
        private String inner_id;

        public String getInner_id() {
            return inner_id;
        }

        public void setInner_id(String inner_id) {
            this.inner_id = inner_id;
        }

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getStu_cartype() {
            return stu_cartype;
        }

        public void setStu_cartype(String stu_cartype) {
            this.stu_cartype = stu_cartype;
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

        public String getStu_reg_date() {
            return stu_reg_date;
        }

        public void setStu_reg_date(String stu_reg_date) {
            this.stu_reg_date = stu_reg_date;
        }

        public String getStu_photo() {
            return stu_photo;
        }

        public void setStu_photo(String stu_photo) {
            this.stu_photo = stu_photo;
        }

        public String getStu_detarec_predate() {
            return stu_detarec_predate;
        }

        public void setStu_detarec_predate(String stu_detarec_predate) {
            this.stu_detarec_predate = stu_detarec_predate;
        }

        public String getStu_detarec_wait_flag() {
            return stu_detarec_wait_flag;
        }

        public void setStu_detarec_wait_flag(String stu_detarec_wait_flag) {
            this.stu_detarec_wait_flag = stu_detarec_wait_flag;
        }

        public String getStu_exam_wait_flag() {
            return stu_exam_wait_flag;
        }

        public void setStu_exam_wait_flag(String stu_exam_wait_flag) {
            this.stu_exam_wait_flag = stu_exam_wait_flag;
        }

        public String getStu_detarec_remind_flag() {
            return stu_detarec_remind_flag;
        }

        public void setStu_detarec_remind_flag(String stu_detarec_remind_flag) {
            this.stu_detarec_remind_flag = stu_detarec_remind_flag;
        }
    }
}
