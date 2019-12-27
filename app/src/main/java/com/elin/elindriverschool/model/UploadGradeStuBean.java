package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by imac on 17/1/5.
 * 上传成绩 学员列表Bean
 */

public class UploadGradeStuBean {


    /**
     * des :
     * data_list : [{"order_date":"2017-03-22","scoreForCheck":"","stu_idnum":"371322198401293410","stu_name":"徐庆伟","stu_phone":"15069955557","stu_photo":""},{"order_date":"2017-03-22","scoreForCheck":"1","stu_idnum":"370781199003010767","stu_name":"张燕","stu_phone":"15098776688","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/柴焱3.jpg"}]
     * rc : 0
     */

    private String des;
    private int rc;
    private List<DataListBean> data_list;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean {
        /**
         * order_date : 2017-03-22
         * scoreForCheck :
         * stu_idnum : 371322198401293410
         * stu_name : 徐庆伟
         * stu_phone : 15069955557
         * stu_photo :
         */

        private String order_date;
        private String scoreForCheck;
        private String stu_idnum;
        private String stu_name;
        private String stu_phone;
        private String stu_photo;

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getScoreForCheck() {
            return scoreForCheck;
        }

        public void setScoreForCheck(String scoreForCheck) {
            this.scoreForCheck = scoreForCheck;
        }

        public String getStu_idnum() {
            return stu_idnum;
        }

        public void setStu_idnum(String stu_idnum) {
            this.stu_idnum = stu_idnum;
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
    }
}
