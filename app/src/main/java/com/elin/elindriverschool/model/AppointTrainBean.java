package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/8/21.
 */

public class AppointTrainBean {

    /**
     * rc : 0
     * des :
     * data : {"id":"14","date":"2017-01-01","create_date":"2017-08-18 14:08:42","model_id":"4","coach_id":"314","timeList":[{"id":"25","coach_id":"314","model_time_id":"3","stu_id":"","create_date":"2017-08-18 14:08:42","train_date":"2017-01-01","already_stu":"1","open_flag":"1","status":"1","tdate_id":"14","start_time":"09:20:00","end_time":"10:20:00","person_limit":"4","train_sub":"3"}]}
     */

    private String rc;
    private String des;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 14
         * date : 2017-01-01
         * create_date : 2017-08-18 14:08:42
         * model_id : 4
         * coach_id : 314
         * timeList : [{"id":"25","coach_id":"314","model_time_id":"3","stu_id":"","create_date":"2017-08-18 14:08:42","train_date":"2017-01-01","already_stu":"1","open_flag":"1","status":"1","tdate_id":"14","start_time":"09:20:00","end_time":"10:20:00","person_limit":"4","train_sub":"3"}]
         */

        private String id;
        private String date;
        private String create_date;
        private String model_id;
        private String coach_id;
        private List<TimeListBean> timeList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getModel_id() {
            return model_id;
        }

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }

        public String getCoach_id() {
            return coach_id;
        }

        public void setCoach_id(String coach_id) {
            this.coach_id = coach_id;
        }

        public List<TimeListBean> getTimeList() {
            return timeList;
        }

        public void setTimeList(List<TimeListBean> timeList) {
            this.timeList = timeList;
        }

        public static class TimeListBean {
            /**
             * id : 25
             * coach_id : 314
             * model_time_id : 3
             * stu_id :
             * create_date : 2017-08-18 14:08:42
             * train_date : 2017-01-01
             * already_stu : 1
             * open_flag : 1
             * status : 1
             * tdate_id : 14
             * start_time : 09:20:00
             * end_time : 10:20:00
             * person_limit : 4
             * train_sub : 3
             */

            private String id;
            private String coach_id;
            private String model_time_id;
            private String stu_id;
            private String create_date;
            private String train_date;
            private String already_stu;
            private String open_flag;
            private String status;
            private String tdate_id;
            private String start_time;
            private String end_time;
            private String person_limit;
            private String train_sub;
            private String define_flag;
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCoach_id() {
                return coach_id;
            }

            public void setCoach_id(String coach_id) {
                this.coach_id = coach_id;
            }

            public String getModel_time_id() {
                return model_time_id;
            }

            public void setModel_time_id(String model_time_id) {
                this.model_time_id = model_time_id;
            }

            public String getDefine_flag() {
                return define_flag;
            }

            public void setDefine_flag(String define_flag) {
                this.define_flag = define_flag;
            }

            public String getStu_id() {
                return stu_id;
            }

            public void setStu_id(String stu_id) {
                this.stu_id = stu_id;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getTrain_date() {
                return train_date;
            }

            public void setTrain_date(String train_date) {
                this.train_date = train_date;
            }

            public String getAlready_stu() {
                return already_stu;
            }

            public void setAlready_stu(String already_stu) {
                this.already_stu = already_stu;
            }

            public String getOpen_flag() {
                return open_flag;
            }

            public void setOpen_flag(String open_flag) {
                this.open_flag = open_flag;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTdate_id() {
                return tdate_id;
            }

            public void setTdate_id(String tdate_id) {
                this.tdate_id = tdate_id;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getPerson_limit() {
                return person_limit;
            }

            public void setPerson_limit(String person_limit) {
                this.person_limit = person_limit;
            }

            public String getTrain_sub() {
                return train_sub;
            }

            public void setTrain_sub(String train_sub) {
                this.train_sub = train_sub;
            }
        }
    }
}
