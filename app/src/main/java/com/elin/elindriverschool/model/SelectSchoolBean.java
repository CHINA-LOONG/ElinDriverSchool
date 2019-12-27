package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/5/25.
 */

public class SelectSchoolBean {

    /**
     * rc : 200
     * des : ok
     * schoolList : [{"id":"1","name":"公交驾校","logo":"1","contacts":"1","phone":"1","address":"1"},{"id":"2","name":"石化驾校","logo":"2","contacts":"2","phone":"2","address":"2"},{"id":"3","name":"大众驾校","logo":"3","contacts":"3","phone":"3","address":"3"}]
     */

    private String rc;
    private String des;
    private List<SchoolListBean> schoolList;

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

    public List<SchoolListBean> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<SchoolListBean> schoolList) {
        this.schoolList = schoolList;
    }

    public static class SchoolListBean {
        /**
         * id : 1
         * name : 公交驾校
         * logo : 1
         * contacts : 1
         * phone : 1
         * address : 1
         */

        private String id;
        private String name;
        private String logo;
        private String contacts;
        private String phone;
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
