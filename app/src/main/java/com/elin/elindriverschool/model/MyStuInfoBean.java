package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by imac on 17/1/2.
 */

public class MyStuInfoBean {

    /**
     * des :
     * data : {"order_info":[{"order_bus":"1","order_date":"2017-02-28","order_site":"临沂第四考场","order_sub":"2"},{"order_bus":"1","order_date":"2017-03-26","order_site":"临沂第三考场","order_sub":"4"}],"stu_info":{"inner_id":177,"stu_cartype":"C1","stu_coach":"张教练","stu_detarec_date":"2017-02-27","stu_detarec_predate":"","stu_drivrec_date":"2017-02-27","stu_drivrec_flag":"1","stu_idnum":"37132219890708072x","stu_name":"徐丹","stu_phone":"15192872889","stu_reg_date":"2017-02-27","stu_status":6,"stu_sum_pay":"3800"},"exam_info":{"sub_four_list":[{"exam_code":"","exam_codecontent":"","exam_examdate":"2016-10-18","exam_noreason":"","exam_score":"1"}],"sub_three_list":[{"exam_code":"","exam_codecontent":"","exam_examdate":"2016-10-18","exam_noreason":"","exam_score":"1"}],"sub_two_list":[{"exam_code":"","exam_codecontent":"","exam_examdate":"2017-02-28","exam_noreason":"","exam_score":"1"}],"sub_one_list":[{"exam_code":"","exam_codecontent":"","exam_examdate":"2017-02-27","exam_noreason":"","exam_score":"1"}],"stu_status":"6"}}
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
         * order_info : [{"order_bus":"1","order_date":"2017-02-28","order_site":"临沂第四考场","order_sub":"2"},{"order_bus":"1","order_date":"2017-03-26","order_site":"临沂第三考场","order_sub":"4"}]
         * stu_info : {"inner_id":177,"stu_cartype":"C1","stu_coach":"张教练","stu_detarec_date":"2017-02-27","stu_detarec_predate":"","stu_drivrec_date":"2017-02-27","stu_drivrec_flag":"1","stu_idnum":"37132219890708072x","stu_name":"徐丹","stu_phone":"15192872889","stu_reg_date":"2017-02-27","stu_status":6,"stu_sum_pay":"3800"}
         * exam_info : {"sub_four_list":[{"exam_code":"","exam_codecontent":"","exam_examdate":"2016-10-18","exam_noreason":"","exam_score":"1"}],"sub_three_list":[{"exam_code":"","exam_codecontent":"","exam_examdate":"2016-10-18","exam_noreason":"","exam_score":"1"}],"sub_two_list":[{"exam_code":"","exam_codecontent":"","exam_examdate":"2017-02-28","exam_noreason":"","exam_score":"1"}],"sub_one_list":[{"exam_code":"","exam_codecontent":"","exam_examdate":"2017-02-27","exam_noreason":"","exam_score":"1"}],"stu_status":"6"}
         */

        private StuInfoBean stu_info;
        private ExamInfoBean exam_info;
        private List<OrderInfoBean> order_info;

        public StuInfoBean getStu_info() {
            return stu_info;
        }

        public void setStu_info(StuInfoBean stu_info) {
            this.stu_info = stu_info;
        }

        public ExamInfoBean getExam_info() {
            return exam_info;
        }

        public void setExam_info(ExamInfoBean exam_info) {
            this.exam_info = exam_info;
        }

        public List<OrderInfoBean> getOrder_info() {
            return order_info;
        }

        public void setOrder_info(List<OrderInfoBean> order_info) {
            this.order_info = order_info;
        }

        public static class StuInfoBean {
            /**
             * inner_id : 177
             * stu_cartype : C1
             * stu_coach : 张教练
             * stu_detarec_date : 2017-02-27
             * stu_detarec_predate :
             * stu_drivrec_date : 2017-02-27
             * stu_drivrec_flag : 1
             * stu_idnum : 37132219890708072x
             * stu_name : 徐丹
             * stu_phone : 15192872889
             * stu_homphone: "",
             * stu_reg_date : 2017-02-27
             * stu_status : 6
             * stu_sum_pay : 3800
             */

            private int inner_id;
            private String stu_cartype;
            private String stu_coach;
            private String stu_detarec_date;
            private String stu_detarec_predate;
            private String stu_drivrec_date;
            private String stu_drivrec_flag;
            private String stu_idnum;
            private String stu_name;
            private String stu_phone;
            private String stu_homphone;
            private String stu_reg_date;
            private int stu_status;
            private String stu_sum_pay;

            public String getStu_homphone() {
                return stu_homphone;
            }

            public void setStu_homphone(String stu_homphone) {
                this.stu_homphone = stu_homphone;
            }

            public int getInner_id() {
                return inner_id;
            }

            public void setInner_id(int inner_id) {
                this.inner_id = inner_id;
            }

            public String getStu_cartype() {
                return stu_cartype;
            }

            public void setStu_cartype(String stu_cartype) {
                this.stu_cartype = stu_cartype;
            }

            public String getStu_coach() {
                return stu_coach;
            }

            public void setStu_coach(String stu_coach) {
                this.stu_coach = stu_coach;
            }

            public String getStu_detarec_date() {
                return stu_detarec_date;
            }

            public void setStu_detarec_date(String stu_detarec_date) {
                this.stu_detarec_date = stu_detarec_date;
            }

            public String getStu_detarec_predate() {
                return stu_detarec_predate;
            }

            public void setStu_detarec_predate(String stu_detarec_predate) {
                this.stu_detarec_predate = stu_detarec_predate;
            }

            public String getStu_drivrec_date() {
                return stu_drivrec_date;
            }

            public void setStu_drivrec_date(String stu_drivrec_date) {
                this.stu_drivrec_date = stu_drivrec_date;
            }

            public String getStu_drivrec_flag() {
                return stu_drivrec_flag;
            }

            public void setStu_drivrec_flag(String stu_drivrec_flag) {
                this.stu_drivrec_flag = stu_drivrec_flag;
            }

            public String getStu_idnum() {
                return stu_idnum;
            }

            public void setStu_idnum(String stu_idnum) {
                this.stu_idnum = stu_idnum;
            }

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

            public String getStu_reg_date() {
                return stu_reg_date;
            }

            public void setStu_reg_date(String stu_reg_date) {
                this.stu_reg_date = stu_reg_date;
            }

            public int getStu_status() {
                return stu_status;
            }

            public void setStu_status(int stu_status) {
                this.stu_status = stu_status;
            }

            public String getStu_sum_pay() {
                return stu_sum_pay;
            }

            public void setStu_sum_pay(String stu_sum_pay) {
                this.stu_sum_pay = stu_sum_pay;
            }
        }

        public static class ExamInfoBean {
            /**
             * sub_four_list : [{"exam_code":"","exam_codecontent":"","exam_examdate":"2016-10-18","exam_noreason":"","exam_score":"1"}]
             * sub_three_list : [{"exam_code":"","exam_codecontent":"","exam_examdate":"2016-10-18","exam_noreason":"","exam_score":"1"}]
             * sub_two_list : [{"exam_code":"","exam_codecontent":"","exam_examdate":"2017-02-28","exam_noreason":"","exam_score":"1"}]
             * sub_one_list : [{"exam_code":"","exam_codecontent":"","exam_examdate":"2017-02-27","exam_noreason":"","exam_score":"1"}]
             * stu_status : 6
             */

            private String stu_status;
            private List<SubFourListBean> sub_four_list;
            private List<SubThreeListBean> sub_three_list;
            private List<SubTwoListBean> sub_two_list;
            private List<SubOneListBean> sub_one_list;

            public String getStu_status() {
                return stu_status;
            }

            public void setStu_status(String stu_status) {
                this.stu_status = stu_status;
            }

            public List<SubFourListBean> getSub_four_list() {
                return sub_four_list;
            }

            public void setSub_four_list(List<SubFourListBean> sub_four_list) {
                this.sub_four_list = sub_four_list;
            }

            public List<SubThreeListBean> getSub_three_list() {
                return sub_three_list;
            }

            public void setSub_three_list(List<SubThreeListBean> sub_three_list) {
                this.sub_three_list = sub_three_list;
            }

            public List<SubTwoListBean> getSub_two_list() {
                return sub_two_list;
            }

            public void setSub_two_list(List<SubTwoListBean> sub_two_list) {
                this.sub_two_list = sub_two_list;
            }

            public List<SubOneListBean> getSub_one_list() {
                return sub_one_list;
            }

            public void setSub_one_list(List<SubOneListBean> sub_one_list) {
                this.sub_one_list = sub_one_list;
            }

            public static class SubFourListBean {
                /**
                 * exam_code :
                 * exam_codecontent :
                 * exam_examdate : 2016-10-18
                 * exam_noreason :
                 * exam_score : 1
                 */

                private String exam_code;
                private String exam_codecontent;
                private String exam_examdate;
                private String exam_noreason;
                private String exam_score;

                public String getExam_code() {
                    return exam_code;
                }

                public void setExam_code(String exam_code) {
                    this.exam_code = exam_code;
                }

                public String getExam_codecontent() {
                    return exam_codecontent;
                }

                public void setExam_codecontent(String exam_codecontent) {
                    this.exam_codecontent = exam_codecontent;
                }

                public String getExam_examdate() {
                    return exam_examdate;
                }

                public void setExam_examdate(String exam_examdate) {
                    this.exam_examdate = exam_examdate;
                }

                public String getExam_noreason() {
                    return exam_noreason;
                }

                public void setExam_noreason(String exam_noreason) {
                    this.exam_noreason = exam_noreason;
                }

                public String getExam_score() {
                    return exam_score;
                }

                public void setExam_score(String exam_score) {
                    this.exam_score = exam_score;
                }
            }

            public static class SubThreeListBean {
                /**
                 * exam_code :
                 * exam_codecontent :
                 * exam_examdate : 2016-10-18
                 * exam_noreason :
                 * exam_score : 1
                 */

                private String exam_code;
                private String exam_codecontent;
                private String exam_examdate;
                private String exam_noreason;
                private String exam_score;

                public String getExam_code() {
                    return exam_code;
                }

                public void setExam_code(String exam_code) {
                    this.exam_code = exam_code;
                }

                public String getExam_codecontent() {
                    return exam_codecontent;
                }

                public void setExam_codecontent(String exam_codecontent) {
                    this.exam_codecontent = exam_codecontent;
                }

                public String getExam_examdate() {
                    return exam_examdate;
                }

                public void setExam_examdate(String exam_examdate) {
                    this.exam_examdate = exam_examdate;
                }

                public String getExam_noreason() {
                    return exam_noreason;
                }

                public void setExam_noreason(String exam_noreason) {
                    this.exam_noreason = exam_noreason;
                }

                public String getExam_score() {
                    return exam_score;
                }

                public void setExam_score(String exam_score) {
                    this.exam_score = exam_score;
                }
            }

            public static class SubTwoListBean {
                /**
                 * exam_code :
                 * exam_codecontent :
                 * exam_examdate : 2017-02-28
                 * exam_noreason :
                 * exam_score : 1
                 */

                private String exam_code;
                private String exam_codecontent;
                private String exam_examdate;
                private String exam_noreason;
                private String exam_score;

                public String getExam_code() {
                    return exam_code;
                }

                public void setExam_code(String exam_code) {
                    this.exam_code = exam_code;
                }

                public String getExam_codecontent() {
                    return exam_codecontent;
                }

                public void setExam_codecontent(String exam_codecontent) {
                    this.exam_codecontent = exam_codecontent;
                }

                public String getExam_examdate() {
                    return exam_examdate;
                }

                public void setExam_examdate(String exam_examdate) {
                    this.exam_examdate = exam_examdate;
                }

                public String getExam_noreason() {
                    return exam_noreason;
                }

                public void setExam_noreason(String exam_noreason) {
                    this.exam_noreason = exam_noreason;
                }

                public String getExam_score() {
                    return exam_score;
                }

                public void setExam_score(String exam_score) {
                    this.exam_score = exam_score;
                }
            }

            public static class SubOneListBean {
                /**
                 * exam_code :
                 * exam_codecontent :
                 * exam_examdate : 2017-02-27
                 * exam_noreason :
                 * exam_score : 1
                 */

                private String exam_code;
                private String exam_codecontent;
                private String exam_examdate;
                private String exam_noreason;
                private String exam_score;

                public String getExam_code() {
                    return exam_code;
                }

                public void setExam_code(String exam_code) {
                    this.exam_code = exam_code;
                }

                public String getExam_codecontent() {
                    return exam_codecontent;
                }

                public void setExam_codecontent(String exam_codecontent) {
                    this.exam_codecontent = exam_codecontent;
                }

                public String getExam_examdate() {
                    return exam_examdate;
                }

                public void setExam_examdate(String exam_examdate) {
                    this.exam_examdate = exam_examdate;
                }

                public String getExam_noreason() {
                    return exam_noreason;
                }

                public void setExam_noreason(String exam_noreason) {
                    this.exam_noreason = exam_noreason;
                }

                public String getExam_score() {
                    return exam_score;
                }

                public void setExam_score(String exam_score) {
                    this.exam_score = exam_score;
                }
            }
        }

        public static class OrderInfoBean {
            /**
             * order_bus : 1
             * order_date : 2017-02-28
             * order_site : 临沂第四考场
             * order_sub : 2
             */

            private String order_bus;
            private String order_date;
            private String order_site;
            private String order_sub;

            public String getOrder_bus() {
                return order_bus;
            }

            public void setOrder_bus(String order_bus) {
                this.order_bus = order_bus;
            }

            public String getOrder_date() {
                return order_date;
            }

            public void setOrder_date(String order_date) {
                this.order_date = order_date;
            }

            public String getOrder_site() {
                return order_site;
            }

            public void setOrder_site(String order_site) {
                this.order_site = order_site;
            }

            public String getOrder_sub() {
                return order_sub;
            }

            public void setOrder_sub(String order_sub) {
                this.order_sub = order_sub;
            }
        }
    }
}
