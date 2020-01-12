package com.elin.elindriverschool.model.request;

import com.elin.elindriverschool.model.TrainModelBean;

import java.util.ArrayList;
import java.util.List;

public class SetSettingsRequest extends BaseRequest {

    public String week;
    public String trainNums;
    public List<TrainModelBean.ModelDataBean> data=new ArrayList<>();

//    public String getWeek() {
//        return week;
//    }
//
//    public void setWeek(String week) {
//        this.week = week;
//    }
//
//    public String getTrainNums() {
//        return trainNums;
//    }
//
//    public void setTrainNums(String trainNums) {
//        this.trainNums = trainNums;
//    }
//
//
//    public List<TrainModelBean.ModelDataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<TrainModelBean.ModelDataBean> data) {
//        this.data = data;
//    }


}
