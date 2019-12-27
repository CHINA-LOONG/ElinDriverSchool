package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/8/2.
 */

public class NoticeBean {


    /**
     * rc : 0
     * des :
     * notice_info : [{"id":"558","notice_id":"38","coach_id":"184","status":"1","read_place":null,"read_time":null,"is_coach_del":"0","content":"12312313123","title":"33333333","create_date":"2017-08-08 08:36:54","hander":"admin","type_id":"3","is_del":"0","type_name":"放假通知","school_id":"1","no_read_count":"0","has_stu":"0"}]
     */

    private String rc;
    private String des;
    private List<NoticeInfoBean> notice_info;

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

    public List<NoticeInfoBean> getNotice_info() {
        return notice_info;
    }

    public void setNotice_info(List<NoticeInfoBean> notice_info) {
        this.notice_info = notice_info;
    }

    public static class NoticeInfoBean {
        /**
         * id : 558
         * notice_id : 38
         * coach_id : 184
         * status : 1
         * read_place : null
         * read_time : null
         * is_coach_del : 0
         * content : 12312313123
         * title : 33333333
         * create_date : 2017-08-08 08:36:54
         * hander : admin
         * type_id : 3
         * is_del : 0
         * type_name : 放假通知
         * school_id : 1
         * no_read_count : 0
         * has_stu : 0
         */

        private String id;
        private String notice_id;
        private String coach_id;
        private String status;
        private Object read_place;
        private Object read_time;
        private String is_coach_del;
        private String content;
        private String title;
        private String create_date;
        private String hander;
        private String type_id;
        private String is_del;
        private String type_name;
        private String school_id;
        private String no_read_count;
        private String has_stu;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(String notice_id) {
            this.notice_id = notice_id;
        }

        public String getCoach_id() {
            return coach_id;
        }

        public void setCoach_id(String coach_id) {
            this.coach_id = coach_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getRead_place() {
            return read_place;
        }

        public void setRead_place(Object read_place) {
            this.read_place = read_place;
        }

        public Object getRead_time() {
            return read_time;
        }

        public void setRead_time(Object read_time) {
            this.read_time = read_time;
        }

        public String getIs_coach_del() {
            return is_coach_del;
        }

        public void setIs_coach_del(String is_coach_del) {
            this.is_coach_del = is_coach_del;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getHander() {
            return hander;
        }

        public void setHander(String hander) {
            this.hander = hander;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getNo_read_count() {
            return no_read_count;
        }

        public void setNo_read_count(String no_read_count) {
            this.no_read_count = no_read_count;
        }

        public String getHas_stu() {
            return has_stu;
        }

        public void setHas_stu(String has_stu) {
            this.has_stu = has_stu;
        }
    }
}
