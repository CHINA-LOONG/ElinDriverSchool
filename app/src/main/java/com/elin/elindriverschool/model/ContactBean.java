package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by imac on 17/1/7.
 * 联系人Bean
 */

public class ContactBean {

    /**
     * des :
     * user_list : [{"user_name":"admin","user_phone":"15032145520","user_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/user/席凤云372824198009264640.jpg"},{"user_name":"教练员1","user_phone":"15032145520","user_photo":""},{"user_name":"教练员2","user_phone":"15032155102","user_photo":""},{"user_name":"张三","user_phone":"15065488850","user_photo":""},{"user_name":"张燕","user_phone":"15098764491","user_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/user/宋伊霞37132519970914372x.jpg"},{"user_name":"张明民","user_phone":"15098765590","user_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/user/刘胜人22062119980520071x.jpg"},{"user_name":"张立","user_phone":"15098764490","user_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/user/周云霞371122199101192224.jpg"}]
     * department_list : [{"department_name":"客服部","department_phone":"0539-1234567","department_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/department/席凤云372824198009264640.jpg"},{"department_name":"后勤部","department_phone":"0539-3225578","department_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/department/刘宗新371326198002238866.jpg"},{"department_name":"财务部","department_phone":"0539-7654321","department_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/department/柴焱3.jpg"}]
     * rc : 0
     */

    private String des;
    private int rc;
    /**
     * user_name : admin
     * user_phone : 15032145520
     * user_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/user/席凤云372824198009264640.jpg
     */

    private List<UserListBean> user_list;
    /**
     * department_name : 客服部
     * department_phone : 0539-1234567
     * department_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/department/席凤云372824198009264640.jpg
     */

    private List<DepartmentListBean> department_list;

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

    public List<UserListBean> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<UserListBean> user_list) {
        this.user_list = user_list;
    }

    public List<DepartmentListBean> getDepartment_list() {
        return department_list;
    }

    public void setDepartment_list(List<DepartmentListBean> department_list) {
        this.department_list = department_list;
    }
    public static class UserListBean {
        private String user_name;
        private String user_phone;
        private String user_photo;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_photo() {
            return user_photo;
        }

        public void setUser_photo(String user_photo) {
            this.user_photo = user_photo;
        }
    }

    public static class DepartmentListBean {
        private String department_name;
        private String department_phone;
        private String department_photo;

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getDepartment_phone() {
            return department_phone;
        }

        public void setDepartment_phone(String department_phone) {
            this.department_phone = department_phone;
        }

        public String getDepartment_photo() {
            return department_photo;
        }

        public void setDepartment_photo(String department_photo) {
            this.department_photo = department_photo;
        }
    }
}
