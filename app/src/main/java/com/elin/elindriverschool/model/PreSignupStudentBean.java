package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/8/2.
 */

public class PreSignupStudentBean {


    /**
     * rc : 0
     * des :
     * preStuList : [{"id":"5","stu_name":"fff","stu_idnum":"371311199311063430","stu_cartype":"C1","stu_class":"普通班","stu_phone":"13552465655","stu_hander":"张应龙","stu_reg_date":"2017-08-01 17:48:42","reg_type":null,"branch_no":"1","school_id":"1","status":"1","stu_remarks":"ffff","stu_sex":"sss"}]
     */

    private String rc;
    private String des;
    private List<PreStuListBean> preStuList;

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

    public List<PreStuListBean> getPreStuList() {
        return preStuList;
    }

    public void setPreStuList(List<PreStuListBean> preStuList) {
        this.preStuList = preStuList;
    }

    public static class PreStuListBean {
        /**
         * id : 5
         * stu_name : fff
         * stu_idnum : 371311199311063430
         * stu_cartype : C1
         * stu_class : 普通班
         * stu_phone : 13552465655
         * stu_hander : 张应龙
         * stu_reg_date : 2017-08-01 17:48:42
         * reg_type : null
         * branch_no : 1
         * school_id : 1
         * status : 1
         * stu_remarks : ffff
         * stu_sex : sss
         */

        private String id;
        private String stu_name;
        private String stu_idnum;
        private String stu_cartype;
        private String stu_class;
        private String stu_phone;
        private String stu_hander;
        private String stu_reg_date;
        private Object reg_type;
        private String branch_no;
        private String school_id;
        private String status;
        private String stu_remarks;
        private String stu_sex;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getStu_cartype() {
            return stu_cartype;
        }

        public void setStu_cartype(String stu_cartype) {
            this.stu_cartype = stu_cartype;
        }

        public String getStu_class() {
            return stu_class;
        }

        public void setStu_class(String stu_class) {
            this.stu_class = stu_class;
        }

        public String getStu_phone() {
            return stu_phone;
        }

        public void setStu_phone(String stu_phone) {
            this.stu_phone = stu_phone;
        }

        public String getStu_hander() {
            return stu_hander;
        }

        public void setStu_hander(String stu_hander) {
            this.stu_hander = stu_hander;
        }

        public String getStu_reg_date() {
            return stu_reg_date;
        }

        public void setStu_reg_date(String stu_reg_date) {
            this.stu_reg_date = stu_reg_date;
        }

        public Object getReg_type() {
            return reg_type;
        }

        public void setReg_type(Object reg_type) {
            this.reg_type = reg_type;
        }

        public String getBranch_no() {
            return branch_no;
        }

        public void setBranch_no(String branch_no) {
            this.branch_no = branch_no;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStu_remarks() {
            return stu_remarks;
        }

        public void setStu_remarks(String stu_remarks) {
            this.stu_remarks = stu_remarks;
        }

        public String getStu_sex() {
            return stu_sex;
        }

        public void setStu_sex(String stu_sex) {
            this.stu_sex = stu_sex;
        }
    }
}
