package com.elin.elindriverschool.model;

import java.util.List;

public class StudentListBean {
    /**
     * {
     * "rc": "0",... <string>
     * "des": "",... <string>
     * -"data": [...<array>
     * -{
     * "stuname": "尹相芳",学员姓名 <string>
     * "stuid": "51711"学员id <string>
     * }
     * ]
     * }
     */

    private int rc;
    private String des;
    private List<StudentDataBean> data;

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

    public List<StudentDataBean> getData() {
        return data;
    }

    public void setData(List<StudentDataBean> data) {
        this.data = data;
    }

    public static class StudentDataBean{

        /**
         * {
         * "stuname": "尹相芳",学员姓名 <string>
         * "stuid": "51711"学员id <string>
         * }
         */
        private String stuname;
        private String stuid;
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }


        public String getStuname() {
            return stuname;
        }

        public void setStuname(String stuname) {
            this.stuname = stuname;
        }

        public String getStuid() {
            return stuid;
        }

        public void setStuid(String stuid) {
            this.stuid = stuid;
        }
    }
}
