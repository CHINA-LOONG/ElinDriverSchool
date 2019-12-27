package com.elin.elindriverschool.model;

import java.util.List;

/**
 * 作者：zzy 2018/4/25 11:31
 * 邮箱：zzyflute@163.com
 */
public class AssistingTaskBean {

    /**
     * rc : 0
     * des :
     * data : [{"stu_name":"王俊芳","stu_phone":"13153961515","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197301154044.jpg","click_num":null},{"stu_name":"王俊芳","stu_phone":"13153961515","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197301154044.jpg","click_num":"0"},{"stu_name":"王俊芳","stu_phone":"13153961515","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197301154044.jpg","click_num":"0"},{"stu_name":"王俊芳","stu_phone":"13153961515","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197301154044.jpg","click_num":"0"},{"stu_name":"王俊芳","stu_phone":"13153961515","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197301154044.jpg","click_num":"0"},{"stu_name":"王俊芳","stu_phone":"13153961515","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197301154044.jpg","click_num":"0"}]
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
         * stu_name : 王俊芳
         * stu_phone : 13153961515
         * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/372801197301154044.jpg
         * click_num : null
         */

        private String stu_name;
        private String stu_phone;
        private String stu_photo;
        private Object click_num;

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getStu_phone() {
            return stu_phone;
        }

        public void setStu_phone(String stu_phone) {
            this.stu_phone = stu_phone;
        }

        public String getStu_photo() {
            return stu_photo;
        }

        public void setStu_photo(String stu_photo) {
            this.stu_photo = stu_photo;
        }

        public Object getClick_num() {
            return click_num;
        }

        public void setClick_num(Object click_num) {
            this.click_num = click_num;
        }
    }
}
