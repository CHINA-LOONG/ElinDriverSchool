package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/8/1.
 */

public class MultiAttrBean {

    private List<String> carType;
    private List<String> classes;
    private List<String> branches;

    public List<String> getCarType() {
        return carType;
    }

    public void setCarType(List<String> carType) {
        this.carType = carType;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getBranches() {
        return branches;
    }

    public void setBranches(List<String> branches) {
        this.branches = branches;
    }
}
