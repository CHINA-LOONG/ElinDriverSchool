package com.elin.elindriverschool.model;

import java.util.List;

public class TrainModelBean {
    /**
     * {
     * "rc": "0",... <string>
     * "des": "",... <string>
     * -"data": [...<array>
     * -{
     * "id": "12",模板id <string>
     * "time_id": "14",时间模板id <string>
     * "start_time": "8:00",开始时间 <string>
     * "end_time": "12:00",结束时间 <string>
     * "person_limit": "4",预约人数 <string>
     * "train_sub": "2",科目 <string>
     * "car_type": "C1"驾照类型 <string>
     * }
     * ]
     * }
     */

    private int rc;
    private String des;
    private List<ModelDataBean> data;

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

    public List<ModelDataBean> getData() {
        return data;
    }

    public void setData(List<ModelDataBean> data) {
        this.data = data;
    }



    public static class ModelDataBean{
        private String id;
        private String time_id;
        private String start_time;
        private String end_time;
        private String person_limit;
        private String train_sub;
        private String car_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime_id() {
            return time_id;
        }

        public void setTime_id(String time_id) {
            this.time_id = time_id;
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

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

    }

}
