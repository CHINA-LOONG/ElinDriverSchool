package com.elin.elindriverschool.model;

import java.util.List;

public class OrderListsBean {
    /**
     * {
     * "rc": "0",返回码：0是成功 <number>
     * "des": "",返回状态提示：例如信息获取失败 <string>
     * -"data": [返回数据<object>
     * -{
     * "id": "60_467_5",计划列表id <string>
     * "start_time": "08:00",开始时间 <string>
     * "end_time": "12:00",结束时间 <string>
     * "already_stu": "1",已预约人数 <integer>
     * "person_limit": "4",可以预约人数（总预约人数） <integer>
     * "train_date": "2019-12-07",预约当天日期 <string>
     * "train_sub": "2",科目 <string>
     * "status": "1",预约状态：1可预约；0不可以预约 <number>
     * "coach_cartype": "C1",准驾车型 <string>
     * "agree_nums": 0,已同意预约数量 <number>
     * "untreated_nums": 1,没处理预约数量 <number>
     * -"student_list": []
     * }
     * ]
     * }
     */

    private int rc;
    private String des;
    private List<CourseDataBean> data;

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

    public List<CourseDataBean> getData() {
        return data;
    }

    public void setData(List<CourseDataBean> data) {
        this.data = data;
    }

    public static class CourseDataBean{
        /**
         * {
         * "id": "60_467_5",计划列表id <string>
         * "start_time": "08:00",开始时间 <string>
         * "end_time": "12:00",结束时间 <string>
         * "already_stu": "1",已预约人数 <integer>
         * "person_limit": "4",可以预约人数（总预约人数） <integer>
         * "train_date": "2019-12-07",预约当天日期 <string>
         * "train_sub": "2",科目 <string>
         * "status": "1",预约状态：1可预约；0不可以预约 <number>
         * "coach_cartype": "C1",准驾车型 <string>
         * "agree_nums": 0,已同意预约数量 <number>
         * "untreated_nums": 1,没处理预约数量 <number>
         * -"student_list": [学生预约列表<array>
         * -{
         * "id": "3",预约列表id <integer>
         * "record_id": "60_467_5",和计划表id关联id <string>
         * "complete_flag": "0",预约状态：0预约中,1预约上,2未预约上,3,该培训未关闭时教练取消,4培训关闭后教练取消,5未关闭学员取消,6关闭后学员取消 <string>
         * "status": "1",进度状态：1正常可预约，2 关闭不可预约,3教练发起签到,4 完成签到5 评价完成6 旷课 <string>
         * "stu_id": "74634",学员id <integer>
         * "stu_name": "李安奎",学员姓名 <string>
         * "stu_phone": "13054934446",学员手机号 <string>
         * "last_date": "2019-12-07",最后一次有效预约日期 <string>
         * "nums": "1"之前预约次数 <integer>
         * }
         * ]
         * }
         */
        private String id;
        private String start_time;
        private String end_time;
        private String already_stu;
        private String person_limit;
        private String train_date;
        private String train_sub;
        private String status;
        private String coach_cartype;
        private String agree_nums;
        private String untreated_nums;
        private List<StudentDataBean> student_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getAlready_stu() {
            return already_stu;
        }

        public void setAlready_stu(String already_stu) {
            this.already_stu = already_stu;
        }

        public String getPerson_limit() {
            return person_limit;
        }

        public void setPerson_limit(String person_limit) {
            this.person_limit = person_limit;
        }

        public String getTrain_date() {
            return train_date;
        }

        public void setTrain_date(String train_date) {
            this.train_date = train_date;
        }

        public String getTrain_sub() {
            return train_sub;
        }

        public void setTrain_sub(String train_sub) {
            this.train_sub = train_sub;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCoach_cartype() {
            return coach_cartype;
        }

        public void setCoach_cartype(String coach_cartype) {
            this.coach_cartype = coach_cartype;
        }

        public String getAgree_nums() {
            return agree_nums;
        }

        public void setAgree_nums(String agree_nums) {
            this.agree_nums = agree_nums;
        }

        public String getUntreated_nums() {
            return untreated_nums;
        }

        public void setUntreated_nums(String untreated_nums) {
            this.untreated_nums = untreated_nums;
        }

        public List<StudentDataBean> getStudent_list() {
            return student_list;
        }

        public void setStudent_list(List<StudentDataBean> student_list) {
            this.student_list = student_list;
        }

    }

    public static class StudentDataBean{
        /**
         * {
         * "id": "3",预约列表id <integer>
         * "record_id": "60_467_5",和计划表id关联id <string>
         * "complete_flag": "0",预约状态：0预约中,1预约上,2未预约上,3,该培训未关闭时教练取消,4培训关闭后教练取消,5未关闭学员取消,6关闭后学员取消 <string>
         * "status": "1",进度状态：1正常可预约，2 关闭不可预约,3教练发起签到,4 完成签到5 评价完成6 旷课 <string>
         * "stu_id": "74634",学员id <integer>
         * "stu_name": "李安奎",学员姓名 <string>
         * "stu_phone": "13054934446",学员手机号 <string>
         * "last_date": "2019-12-07",最后一次有效预约日期 <string>
         * "nums": "1"之前预约次数 <integer>
         * }
         */
        private String id;
        private String record_id;
        private String complete_flag;
        private String status;
        private String stu_id;
        private String stu_name;
        private String stu_phone;
        private String last_date;
        private String nums;


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

        public String getStu_id() {
            return stu_id;
        }

        public void setStu_id(String stu_id) {
            this.stu_id = stu_id;
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

        public String getLast_date() {
            return last_date;
        }

        public void setLast_date(String last_date) {
            this.last_date = last_date;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }

    }

}
