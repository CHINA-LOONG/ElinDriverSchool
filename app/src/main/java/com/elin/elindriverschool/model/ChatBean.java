package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/8/2.
 */

public class ChatBean {

    /**
     * rc : 0
     * des :
     * data : [{"id":"221","detail_id":"221","reply_person":"admin","reply_content":"提高考试通过率","reply_time":"2017-08-01","status":"0"}]
     */

    private String rc;
    private String des;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 221
         * detail_id : 221
         * reply_person : admin
         * reply_content : 提高考试通过率
         * reply_time : 2017-08-01
         * status : 0
         */

        private String id;
        private String detail_id;
        private String reply_person;
        private String reply_content;
        private String reply_time;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDetail_id() {
            return detail_id;
        }

        public void setDetail_id(String detail_id) {
            this.detail_id = detail_id;
        }

        public String getReply_person() {
            return reply_person;
        }

        public void setReply_person(String reply_person) {
            this.reply_person = reply_person;
        }

        public String getReply_content() {
            return reply_content;
        }

        public void setReply_content(String reply_content) {
            this.reply_content = reply_content;
        }

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
