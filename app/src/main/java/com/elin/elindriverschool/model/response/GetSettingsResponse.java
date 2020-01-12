package com.elin.elindriverschool.model.response;

import com.elin.elindriverschool.model.bean.ModelBean;

import java.util.List;

public class GetSettingsResponse extends BaseResponse {

    /**
     * success
     * {
     * "rc": "0",... <string>
     * "des": "",... <string>
     * -"data": {...<object>
     * "week": "1,5,6,7",自动开放预约，1是周一，7是周日 <string>
     * "num": "2"学员1天最多预约次数，为0表示不限 <string>
     * }
     * }
     */
    private SettingsData data;

    public SettingsData getData() {
        return data;
    }

    public void setData(SettingsData data) {
        this.data = data;
    }

    public class SettingsData{

        /**
         * {...<object>
         * -"model": [模板数据<array>
         * -{
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
         * ],
         * "week": "1,5,6",自动开放预约，1是周一，7是周日 <string>
         * "num": "2"学员1天最多预约次数，为0表示不限 <string>
         * }
         */
        private String week;
        private String num;
        private List<ModelBean> model;

        public List<ModelBean> getModel() {
            return model;
        }

        public void setModel(List<ModelBean> model) {
            this.model = model;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

    }

}
