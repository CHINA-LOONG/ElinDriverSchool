package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/9/29.
 */

public class AddTrainScheduleBean {

    /**
     * rc : 330
     * des : 这些日期已存在计划
     * errorDates : ["2017-10-04"]
     */

    private String rc;
    private String des;
    private List<String> errorDates;

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

    public List<String> getErrorDates() {
        return errorDates;
    }

    public void setErrorDates(List<String> errorDates) {
        this.errorDates = errorDates;
    }
}
