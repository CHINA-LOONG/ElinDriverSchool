package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by imac on 17/1/6.
 *  查看成绩 学员列表Bean
 */

public class CheckScoreStuBean {

    /**
     * rc : 0
     * des :
     * data_list : {"precent":"42.31%","data_list":[{"stu_name":"傅瑶君","stu_idnum":"371302198910091811","stu_phone":"18053919558","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371302198910091811.jpg","exam_date":"2018-06-21","exam_score":"1","exam_sub":"2"},{"stu_name":"方昆淋","stu_idnum":"371324200002179437","stu_phone":"18853934600","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5a9a118344274.jpg","exam_date":"2018-06-21","exam_score":"1","exam_sub":"2"},{"stu_name":"魏平升","stu_idnum":"371323197703290874","stu_phone":"15215497899","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/59f56c5abde0a.jpg","exam_date":"2018-06-21","exam_score":"1","exam_sub":"2"},{"stu_name":"李忠江","stu_idnum":"371325198009091952","stu_phone":"13791577034","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371325198009091952.jpg","exam_date":"2018-06-11","exam_score":"2","exam_sub":"2"},{"stu_name":"何秀侠","stu_idnum":"371322198203207526","stu_phone":"13969939865","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371322198203207526.jpg","exam_date":"2018-06-11","exam_score":"1","exam_sub":"2"},{"stu_name":"王新花","stu_idnum":"372824197210213525","stu_phone":"18764967815","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5a9a11f9bb91b.jpg","exam_date":"2018-06-11","exam_score":"1","exam_sub":"2"},{"stu_name":"徐庆兰","stu_idnum":"372801197106180626","stu_phone":"15254956790","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197106180626.jpg","exam_date":"2018-06-10","exam_score":"1","exam_sub":"2"},{"stu_name":"曹华","stu_idnum":"371325198109245648","stu_phone":"15054905279","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/59eec48015784.jpg","exam_date":"2018-06-06","exam_score":"1","exam_sub":"2"},{"stu_name":"刘芳草","stu_idnum":"371322199312261788","stu_phone":"18754981117","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5a2dd878b2972.jpg","exam_date":"2018-06-05","exam_score":"2","exam_sub":"2"},{"stu_name":"颜克欣","stu_idnum":"371311199705062625","stu_phone":"18254919725","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5a42f008d2ab1.jpg","exam_date":"2018-05-22","exam_score":"2","exam_sub":"2"},{"stu_name":"侯艳春","stu_idnum":"371311198709092341","stu_phone":"15853961818","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371311198709092341.jpg","exam_date":"2018-05-22","exam_score":"2","exam_sub":"2"},{"stu_name":"李成龙","stu_idnum":"37131119941020231X","stu_phone":"18254920400","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/37131119941020231x.jpg","exam_date":"2018-04-27","exam_score":"2","exam_sub":"2"},{"stu_name":"王文明","stu_idnum":"371122198304231831","stu_phone":"13581063969","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/59bf79b1b5259.jpg","exam_date":"2018-04-13","exam_score":"2","exam_sub":"2"},{"stu_name":"石殿军","stu_idnum":"372801197412042328","stu_phone":"15715392358","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197412042328.jpg","exam_date":"2018-04-10","exam_score":"2","exam_sub":"2"},{"stu_name":"蔡秀晶","stu_idnum":"220183198403291628","stu_phone":"13969991711","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/220183198403291628.jpg","exam_date":"2018-04-10","exam_score":"2","exam_sub":"2"},{"stu_name":"王晴晴","stu_idnum":"320322198712130045","stu_phone":"13854959138","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/320322198712130045.jpg","exam_date":"2018-04-10","exam_score":"2","exam_sub":"2"},{"stu_name":"姚翠平","stu_idnum":"371321198204204726","stu_phone":"15253956007","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371321198204204726.jpg","exam_date":"2018-03-20","exam_score":"2","exam_sub":"2"},{"stu_name":"陈瑶","stu_idnum":"371322199808318848","stu_phone":"18353970358","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371322199808318848.jpg","exam_date":"2018-03-06","exam_score":"1","exam_sub":"2"},{"stu_name":"周油银","stu_idnum":"362322197307256052","stu_phone":"13345098577","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/362322197307256052.jpg","exam_date":"2017-09-07","exam_score":"1","exam_sub":"2"},{"stu_name":"张从娜","stu_idnum":"371322198804161228","stu_phone":"13869960163","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371322198804161228.jpg","exam_date":"2017-09-07","exam_score":"2","exam_sub":"2"}]}
     */

    private String rc;
    private String des;
    private DataListBeanX data_list;

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

    public DataListBeanX getData_list() {
        return data_list;
    }

    public void setData_list(DataListBeanX data_list) {
        this.data_list = data_list;
    }

    public static class DataListBeanX {
        /**
         * precent : 42.31%
         * data_list : [{"stu_name":"傅瑶君","stu_idnum":"371302198910091811","stu_phone":"18053919558","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371302198910091811.jpg","exam_date":"2018-06-21","exam_score":"1","exam_sub":"2"},{"stu_name":"方昆淋","stu_idnum":"371324200002179437","stu_phone":"18853934600","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5a9a118344274.jpg","exam_date":"2018-06-21","exam_score":"1","exam_sub":"2"},{"stu_name":"魏平升","stu_idnum":"371323197703290874","stu_phone":"15215497899","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/59f56c5abde0a.jpg","exam_date":"2018-06-21","exam_score":"1","exam_sub":"2"},{"stu_name":"李忠江","stu_idnum":"371325198009091952","stu_phone":"13791577034","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371325198009091952.jpg","exam_date":"2018-06-11","exam_score":"2","exam_sub":"2"},{"stu_name":"何秀侠","stu_idnum":"371322198203207526","stu_phone":"13969939865","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371322198203207526.jpg","exam_date":"2018-06-11","exam_score":"1","exam_sub":"2"},{"stu_name":"王新花","stu_idnum":"372824197210213525","stu_phone":"18764967815","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5a9a11f9bb91b.jpg","exam_date":"2018-06-11","exam_score":"1","exam_sub":"2"},{"stu_name":"徐庆兰","stu_idnum":"372801197106180626","stu_phone":"15254956790","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197106180626.jpg","exam_date":"2018-06-10","exam_score":"1","exam_sub":"2"},{"stu_name":"曹华","stu_idnum":"371325198109245648","stu_phone":"15054905279","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/59eec48015784.jpg","exam_date":"2018-06-06","exam_score":"1","exam_sub":"2"},{"stu_name":"刘芳草","stu_idnum":"371322199312261788","stu_phone":"18754981117","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5a2dd878b2972.jpg","exam_date":"2018-06-05","exam_score":"2","exam_sub":"2"},{"stu_name":"颜克欣","stu_idnum":"371311199705062625","stu_phone":"18254919725","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/5a42f008d2ab1.jpg","exam_date":"2018-05-22","exam_score":"2","exam_sub":"2"},{"stu_name":"侯艳春","stu_idnum":"371311198709092341","stu_phone":"15853961818","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371311198709092341.jpg","exam_date":"2018-05-22","exam_score":"2","exam_sub":"2"},{"stu_name":"李成龙","stu_idnum":"37131119941020231X","stu_phone":"18254920400","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/37131119941020231x.jpg","exam_date":"2018-04-27","exam_score":"2","exam_sub":"2"},{"stu_name":"王文明","stu_idnum":"371122198304231831","stu_phone":"13581063969","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/lygj/student_img/59bf79b1b5259.jpg","exam_date":"2018-04-13","exam_score":"2","exam_sub":"2"},{"stu_name":"石殿军","stu_idnum":"372801197412042328","stu_phone":"15715392358","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197412042328.jpg","exam_date":"2018-04-10","exam_score":"2","exam_sub":"2"},{"stu_name":"蔡秀晶","stu_idnum":"220183198403291628","stu_phone":"13969991711","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/220183198403291628.jpg","exam_date":"2018-04-10","exam_score":"2","exam_sub":"2"},{"stu_name":"王晴晴","stu_idnum":"320322198712130045","stu_phone":"13854959138","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/320322198712130045.jpg","exam_date":"2018-04-10","exam_score":"2","exam_sub":"2"},{"stu_name":"姚翠平","stu_idnum":"371321198204204726","stu_phone":"15253956007","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371321198204204726.jpg","exam_date":"2018-03-20","exam_score":"2","exam_sub":"2"},{"stu_name":"陈瑶","stu_idnum":"371322199808318848","stu_phone":"18353970358","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371322199808318848.jpg","exam_date":"2018-03-06","exam_score":"1","exam_sub":"2"},{"stu_name":"周油银","stu_idnum":"362322197307256052","stu_phone":"13345098577","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/362322197307256052.jpg","exam_date":"2017-09-07","exam_score":"1","exam_sub":"2"},{"stu_name":"张从娜","stu_idnum":"371322198804161228","stu_phone":"13869960163","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371322198804161228.jpg","exam_date":"2017-09-07","exam_score":"2","exam_sub":"2"}]
         */

        private String precent;
        private List<DataListBean> data_list;

        public String getPrecent() {
            return precent;
        }

        public void setPrecent(String precent) {
            this.precent = precent;
        }

        public List<DataListBean> getData_list() {
            return data_list;
        }

        public void setData_list(List<DataListBean> data_list) {
            this.data_list = data_list;
        }

        public static class DataListBean {
            /**
             * stu_name : 傅瑶君
             * stu_idnum : 371302198910091811
             * stu_phone : 18053919558
             * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371302198910091811.jpg
             * exam_date : 2018-06-21
             * exam_score : 1
             * exam_sub : 2
             */

            private String stu_name;
            private String stu_idnum;
            private String stu_phone;
            private String stu_photo;
            private String exam_date;
            private String exam_score;
            private String exam_sub;

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

            public String getStu_photo() {
                return stu_photo;
            }

            public void setStu_photo(String stu_photo) {
                this.stu_photo = stu_photo;
            }

            public String getExam_date() {
                return exam_date;
            }

            public void setExam_date(String exam_date) {
                this.exam_date = exam_date;
            }

            public String getExam_score() {
                return exam_score;
            }

            public void setExam_score(String exam_score) {
                this.exam_score = exam_score;
            }

            public String getExam_sub() {
                return exam_sub;
            }

            public void setExam_sub(String exam_sub) {
                this.exam_sub = exam_sub;
            }
        }
    }
}
