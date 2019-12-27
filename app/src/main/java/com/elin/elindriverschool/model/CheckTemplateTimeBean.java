package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/9/4.
 */

public class CheckTemplateTimeBean {


    /**
     * rc : 0
     * des :
     * data : [{"id":"17","coach_id":"184","name":"template0","create_time":"2017-09-07 17:09:46","hours":"0","school_id":"1","del_flag":"0","timeList":[{"id":"15","model_id":"17","start_time":"17:30","end_time":"17:40","person_limit":"4","train_sub":"0","hours":"0","is_only":"","able_stu_count":""}]},{"id":"18","coach_id":"184","name":"template0","create_time":"2017-09-07 20:09:56","hours":"0","school_id":"1","del_flag":"0","timeList":[{"id":"16","model_id":"18","start_time":"20:30","end_time":"20:40","person_limit":"4","train_sub":"5","hours":"0","is_only":null,"able_stu_count":"0"}]},{"id":"19","coach_id":"184","name":"aaaaaa","create_time":"2017-09-08 09:09:35","hours":"6","school_id":"1","del_flag":"0","timeList":[{"id":"17","model_id":"19","start_time":"9:30","end_time":"12:30","person_limit":"4","train_sub":"2","hours":"3","is_only":null,"able_stu_count":"0"},{"id":"18","model_id":"19","start_time":"13:30","end_time":"16:30","person_limit":"4","train_sub":"3","hours":"3","is_only":null,"able_stu_count":"0"}]},{"id":"22","coach_id":"184","name":"template0","create_time":"2017-09-29 09:18:16","hours":"2","school_id":"1","del_flag":"0","timeList":[{"id":"20","model_id":"22","start_time":"9:10","end_time":"11:10","person_limit":"4","train_sub":"2","hours":"2","is_only":null,"able_stu_count":"0"}]},{"id":"23","coach_id":"184","name":"template0","create_time":"2017-09-29 09:39:20","hours":"2","school_id":"1","del_flag":"0","timeList":[{"id":"21","model_id":"23","start_time":"11:30","end_time":"13:30","person_limit":"4","train_sub":"2","hours":"2","is_only":null,"able_stu_count":"0"}]},{"id":"24","coach_id":"184","name":"我的安排","create_time":"2017-09-29 10:12:16","hours":"3","school_id":"1","del_flag":"0","timeList":[{"id":"22","model_id":"24","start_time":"10:10","end_time":"12:10","person_limit":"4","train_sub":"2","hours":"2","is_only":null,"able_stu_count":"0"},{"id":"23","model_id":"24","start_time":"11:10","end_time":"12:10","person_limit":"5","train_sub":"3","hours":"1","is_only":null,"able_stu_count":"0"}]},{"id":"26","coach_id":"184","name":"kjdjdj and","create_time":"2017-09-29 16:09:32","hours":"1","school_id":"1","del_flag":"0","timeList":[{"id":"25","model_id":"26","start_time":"18:00","end_time":"19:00","person_limit":"4","train_sub":"2","hours":"1","is_only":null,"able_stu_count":"0"},{"id":"26","model_id":"26","start_time":"16:00","end_time":"16:10","person_limit":"4","train_sub":"3","hours":"0","is_only":null,"able_stu_count":"0"}]},{"id":"28","coach_id":"184","name":"我摸了","create_time":"2017-09-29 16:15:36","hours":"0","school_id":"1","del_flag":"0","timeList":[{"id":"28","model_id":"28","start_time":"16:10","end_time":"16:30","person_limit":"4","train_sub":"2","hours":"0","is_only":null,"able_stu_count":"0"}]}]
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
         * id : 17
         * coach_id : 184
         * name : template0
         * create_time : 2017-09-07 17:09:46
         * hours : 0
         * school_id : 1
         * del_flag : 0
         * timeList : [{"id":"15","model_id":"17","start_time":"17:30","end_time":"17:40","person_limit":"4","train_sub":"0","hours":"0","is_only":"","able_stu_count":""}]
         */

        private String id;
        private String coach_id;
        private String name;
        private String create_time;
        private String hours;
        private String school_id;
        private String del_flag;
        private List<TimeListBean> timeList;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getDel_flag() {
            return del_flag;
        }

        public void setDel_flag(String del_flag) {
            this.del_flag = del_flag;
        }

        public List<TimeListBean> getTimeList() {
            return timeList;
        }

        public void setTimeList(List<TimeListBean> timeList) {
            this.timeList = timeList;
        }

        public static class TimeListBean {
            /**
             * id : 15
             * model_id : 17
             * start_time : 17:30
             * end_time : 17:40
             * person_limit : 4
             * train_sub : 0
             * hours : 0
             * is_only :
             * able_stu_count :
             */

            private String id;
            private String model_id;
            private String start_time;
            private String end_time;
            private String person_limit;
            private String train_sub;
            private String hours;
            private String is_only;
            private String able_stu_count;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getModel_id() {
                return model_id;
            }

            public void setModel_id(String model_id) {
                this.model_id = model_id;
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

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            public String getIs_only() {
                return is_only;
            }

            public void setIs_only(String is_only) {
                this.is_only = is_only;
            }

            public String getAble_stu_count() {
                return able_stu_count;
            }

            public void setAble_stu_count(String able_stu_count) {
                this.able_stu_count = able_stu_count;
            }
        }
    }
}
