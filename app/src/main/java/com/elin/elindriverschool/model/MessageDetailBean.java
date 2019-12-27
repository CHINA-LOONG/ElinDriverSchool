package com.elin.elindriverschool.model;

/**
 * Created by 17535 on 2017/2/20.
 */

public class MessageDetailBean {


    /**
     * des :
     * data : {"reminder_content":"您有推送2017-02-20 16:06:29","reminder_date":"2017-02-20 16:06:29","reminder_id":"67","reminder_status":2,"reminder_title":"测试2017-02-20 16:06:29"}
     * rc : 0
     */

    private String des;
    private DataBean data;
    private int rc;

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

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public static class DataBean {
        /**
         * reminder_content : 您有推送2017-02-20 16:06:29
         * reminder_date : 2017-02-20 16:06:29
         * reminder_id : 67
         * reminder_status : 2
         * reminder_title : 测试2017-02-20 16:06:29
         */

        private String reminder_content;
        private String reminder_date;
        private String reminder_id;
        private int reminder_status;
        private String reminder_title;

        public String getReminder_content() {
            return reminder_content;
        }

        public void setReminder_content(String reminder_content) {
            this.reminder_content = reminder_content;
        }

        public String getReminder_date() {
            return reminder_date;
        }

        public void setReminder_date(String reminder_date) {
            this.reminder_date = reminder_date;
        }

        public String getReminder_id() {
            return reminder_id;
        }

        public void setReminder_id(String reminder_id) {
            this.reminder_id = reminder_id;
        }

        public int getReminder_status() {
            return reminder_status;
        }

        public void setReminder_status(int reminder_status) {
            this.reminder_status = reminder_status;
        }

        public String getReminder_title() {
            return reminder_title;
        }

        public void setReminder_title(String reminder_title) {
            this.reminder_title = reminder_title;
        }
    }
}
