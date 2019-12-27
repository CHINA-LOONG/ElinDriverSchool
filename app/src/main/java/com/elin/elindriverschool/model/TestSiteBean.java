package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by imac on 17/1/3.
 */

public class TestSiteBean {

    /**
     * des :
     * data_list : [{"exam_site":"临沂第四考场"},{"exam_site":"临沂第三考场"}]
     * rc : 0
     */

    private String des;
    private int rc;
    /**
     * exam_site : 临沂第四考场
     */

    private List<DataListBean> data_list;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean {
        private String exam_site;

        public String getExam_site() {
            return exam_site;
        }

        public void setExam_site(String exam_site) {
            this.exam_site = exam_site;
        }
    }
}
