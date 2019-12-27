package com.elin.elindriverschool.model;

/**
 * Created by 17535 on 2017/4/8.
 */

public class NotReadMessageBean {


    /**
     * rc : 0
     * des :
     * data : {"all":5,"msg1":"1","msg2":"4"}
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
         * all : 5
         * msg1 : 1
         * msg2 : 4
         */

        private int all;
        private String msg1;
        private String msg2;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

        public String getMsg1() {
            return msg1;
        }

        public void setMsg1(String msg1) {
            this.msg1 = msg1;
        }

        public String getMsg2() {
            return msg2;
        }

        public void setMsg2(String msg2) {
            this.msg2 = msg2;
        }
    }
}
