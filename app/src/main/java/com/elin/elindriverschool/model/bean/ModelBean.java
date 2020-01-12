package com.elin.elindriverschool.model.bean;

import java.util.ArrayList;
import java.util.List;

public class ModelBean {
    /**
     * {
     * "id": "12",模板id <string>
     * "name": "测试",模板名称 <string>
     * -"time_list": [时间段数据<array>
     * -{
     * "time_id": "14",时间段id <string>
     * "start_time": "8:00",开始时间 <string>
     * "end_time": "12:00",结束时间 <string>
     * "person_limit": "4",预约人数 <string>
     * "train_sub": "2",科目 <string>
     * "car_type": "C1"准驾车型 <string>
     * }
     * ]
     * }
     */


    public String id;
    public String name;
    public List<TimeBean> time_list = new ArrayList<>();

    public static class TimeBean{
        public String time_id;
        public String start_time;
        public String end_time;
        public String person_limit;
        public String train_sub;
        public String car_type;

        public TimeBean(){
            time_id="";
            start_time = "00:00";
            end_time = "00:00";
            person_limit="10";
            train_sub = "5";
            car_type="C1";
        }
    }
}
