package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/9/16.
 */

public class BusStuBean {

    /**
     * rc : 0
     * des :
     * data : [{"stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5959ee6c6e2b7.jpg","stu_phone":"15165507378","stu_name":"李丽","stu_coach":"周波","inner_id":"38973","coach_phone":"13280560606","stu_sex":"nv","stu_idnum":"371324199802190727"}]
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
         * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5959ee6c6e2b7.jpg
         * stu_phone : 15165507378
         * stu_name : 李丽
         * stu_coach : 周波
         * inner_id : 38973
         * coach_phone : 13280560606
         * stu_sex : nv
         * stu_idnum : 371324199802190727
         */

        private String stu_photo;
        private String stu_phone;
        private String stu_name;
        private String stu_coach;
        private String inner_id;
        private String coach_phone;
        private String stu_sex;
        private String stu_idnum;

        public String getStu_photo() {
            return stu_photo;
        }

        public void setStu_photo(String stu_photo) {
            this.stu_photo = stu_photo;
        }

        public String getStu_phone() {
            return stu_phone;
        }

        public void setStu_phone(String stu_phone) {
            this.stu_phone = stu_phone;
        }

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getStu_coach() {
            return stu_coach;
        }

        public void setStu_coach(String stu_coach) {
            this.stu_coach = stu_coach;
        }

        public String getInner_id() {
            return inner_id;
        }

        public void setInner_id(String inner_id) {
            this.inner_id = inner_id;
        }

        public String getCoach_phone() {
            return coach_phone;
        }

        public void setCoach_phone(String coach_phone) {
            this.coach_phone = coach_phone;
        }

        public String getStu_sex() {
            return stu_sex;
        }

        public void setStu_sex(String stu_sex) {
            this.stu_sex = stu_sex;
        }

        public String getStu_idnum() {
            return stu_idnum;
        }

        public void setStu_idnum(String stu_idnum) {
            this.stu_idnum = stu_idnum;
        }
    }
}
