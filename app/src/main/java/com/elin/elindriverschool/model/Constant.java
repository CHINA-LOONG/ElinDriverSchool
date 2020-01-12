package com.elin.elindriverschool.model;

/**
 * Created by imac on 16/12/29.
 */

public class Constant {
      public static final String ServerURL = "http://lygj.jiakaopx.com/Client";//正式服务器
//    public static final String ServerURL = "http://lygj.mydriving.com/Client";//测试服务器
//    public static final String ServerURL = "http://192.168.1.153/Client";
    public static final String DYNAMIC_URL = "http://lygj.jiakaopx.com/Student";
    public static final String ServerStuURL = "http://busdriving.elin365.com/DrivingForStudent";//正式外网IP
    public static final String LOGIN_APP = "/Coach/coach_login";//登录
    public static final String LOGIN_OUT = "/Coach/coach_logout";//登出
    public static final String REGISTER_APP = "/Coach/coach_register";//注册
    public static final String FIND_PWD = "/Coach/coach_findpwd";//找回密码
    public static final String GET_CONTACT_LIST = "/Coach/get_telephone_list";//获取联系人
    public static final String UPDATE_PHONE = "/Coach/updatePhone";//修改电话号码
    public static final String MY_DRIVE_STU = "/Student/mystu_list";//我的学员
    public static final String STU_DETAIL = "/Student/mystu_detail";//学员详情
    public static final String WAIT_OPTION_STU = "/Wait/detarec_wait_list";//获取待考学员待受理 列表
    public static final String WAIT_OPTION_APPLY= "/Wait/detarec_apply_list";//待受理学员提交
    public static final String WAIT_TEST_STU = "/Wait/examsub_wait_list";//待考学员 科目1234
    public static final String WAIT_TEST_STU_APPLY = "/Wait/examsub_apply_list";//提交待考学员 科目1234
    public static final String GET_TEST_SITES = "/Wait/get_all_examsite";//获取考试场地

    public static final String PRE_STU_INFO_UPDATE = "/Order/examsub_order_update";//修改预约学员 信息
    public static final String GET_WAIT_TEST_STU_INFO = "/Order/stu_wait_detail";//获取待考学员信息
    public static final String GET_PRE_SUC_WAIT_OPTION = "/Order/detarec_order_list";//获取预约成功学员待受理 列表
    public static final String GET_PRE_SUC_WAIT_TEST = "/Order/examsub_order_list";//获取预约成功学员待考试 列表
    public static final String PRE_SUC_WAIT_TEST_INFO = "/Order/examsub_order_detail";//获取预约成功学员待考试 详情
    public static final String PRE_SUC_WAIT_OPTION_INFO = "/Order/detarec_order_detail";//获取预约成功学员待受理 详情
    public static final String GET_TRAIN_MODEL_LIST = "/Order/getTrainModelList";//获取时间模板
    public static final String ADD_TRAIN_MODEL = "/Order/addTrainModel";//添加培训安排模板
    public static final String ADD_TRAIN_SCHEDULE = "/Order/addTrainSchedule";//设定预约计划
    public static final String GET_TRAIN_MODEL = "/Order/getTrainScheduleListByDate";//通过日期获取该日期的计划
    public static final String GET_ORDER_STUDENTS = "/Order/getOrderStudents";//获取某一时间段记录的预约学员
    public static final String ADD_ORDER_STUDENT = "/Order/addOrderStudent";//添加预约学员
    public static final String DEL_ORDER_STUDENT = "/Order/delOrderStudent";//删除预约学员
    public static final String GET_PRE_STUDENTS_BY_MODEL_ID = "/Order/getPreStudentsByModelId";//获取匹配模板的学员列表
    public static final String GET_BEFORE_DAY = "/Order/getBeforeDay";//获取必须提前发布的天数
    public static final String GET_PRE_STUDENTS_BY_MODEL_TIME_ID = "/Order/getPreStudentsByModelTimeId";//获取匹配模板的学员列表

    public static final String GET_TRAIN_SCHEDULELIST = "/Order/getTrainScheduleList";//获取教练员发布过计划的日期列表
    public static final String EXAMSUB_ORDER_CANCLE = "/Order/examsub_order_cancel";//取消预约
    public static final String TRAIN_SIGN_IN = "/Order/trainSignIn";//对当天培训学员发起签到
    public static final String GET_CANCEL_REASON = "/Order/getCancelReason";//获取取消原因
    public static final String QUERY_MY_TRAIN_PLAN = "/Order/queryMyTrainPlan";//我的培训计划
    public static final String DEL_TRAIN_MODEL = "/Order/delTrainModel";//删除培训模板
    public static final String GET_NO_ORDER_STU_BY_RECORD_ID = "/Order/getNoOrderStuByRecordId";// 获取某一时间段记录的未预约学员列表


    public static final String GET_UPLOAD_GRADE_STUS = "/Exam/examscore_wait_list";//获取上传成绩学员列表
    public static final String UPLOAD_GRADE_BY_LIST = "/Exam/examscore_upload_list";//上传成绩 学员列表页
    public static final String UPLOAD_GRADE_STU_INFO = "/Exam/examscore_wait_detail";//上传成绩学员详情
    public static final String UPLOAD_GRADE_BY_INFO = "/Exam/examscore_upload_info";//上传成绩 (学员详情页）
    public static final String CHECK_STU_GRADE_LIST = "/Exam/examscore_query_list";//查看成绩 (学员列表）
    public static final String CHECK_STU_GRADE_CHECK = "/Exam/examscore_check_info";//查看成绩 核对
    public static final String COMMIT_CLIENTID = "/Dynamic/upload_clientid";//提交设备id
    public static final String GET_REMINDER_LIST = "/Dynamic/get_reminder_list";//提醒消息
    public static final String READ_MESSAGES = "/Dynamic/reminder_read";//已读消息
    public static final String NOT_READ_MESSAGES = "/Dynamic/get_reminder_list_count";//未读消息
    public static final String UPDATE_INFO = "/Dynamic/update_dynamic";//上传教练信息
    public static final String SHARE_COUNT = "/Dynamic/share_dynamic";//统计转发数量
    public static final String DYNAMIC_LIST = "/Dynamic/get_dynamic_list";//陪驾动态
    public static final String UPDATE_VERSION = "/Dynamic/checkVersion";//更新软件
    public static final String READ_ALL_REMINDER = "/Dynamic/readAllReminder";//消息全部设为已读


    public static final String DYNAMIC_ITEM = "/Index/driveDynamic.html?inner_id=";//陪驾动态单挑
    public static final String ALL_DYNAMIC_LIST = "/index/driveDlist.html";//全部培训动态页面链接
    public static final String GET_MULTI_ATTR = "/Student/getMultiAttr";//获取校区班别车型
    public static final String PRE_SIGNUP = "/Student/preSignUp";//学员预报名
    public static final String GET_PRE_STU = "/Student/getPreStu";//学员预报名列表
    public static final String DEL_PRE_STU = "/Student/delPreStu";//删除预报名学员
    public static final String GET_STU_FOR_BUS = "/Student/getStuForBus";//班车乘坐列表
    public static final String GET_STU_FOR_ORDER = "/Student/getStuForOrder";//获取预约考试的人员
    public static final String GET_STU_FOR_RETURN = "/Student/getStuForReturn";//获取回放学员列表
    public static final String GET_RETURN_TYPE = "/Student/getReturnType";//获取回访结果的类型
    public static final String ADD_RETURN = "/Student/addReturn";//提交回访结果
    public static final String GET_HAS_JOB_STU_LIST = "/Student/getHasJobStuList";//3.2.7 获取有未完成任务的学员
    public static final String GET_ASSISTING_TASK_STU = "/Student/getAssistingTaskStu";//3.2.7 获取有未完成任务的学员


    public static final String GET_ALL_NOTICE = "/Notice/getAllNotices";//获取全部通知
    public static final String GET_NOTICE = "/Notice/getNotice";//获取通知聊天内容
    public static final String SEND_REPLY = "/Notice/sendReply";//发送通知回复
    public static final String READ_NOTICE = "/Notice/readNotice";//读取通知
    public static final String GET_NOTICE_TYPE_COUNT = "/Notice/getNoticeTypeCount";//获取公告类型列表及未读数量
    public static final String GET_NOTICES_BY_TYPE_ID = "/Notice/getNoticesByTypeId";//通过typeId获取通知公告
    public static final String DEL_REPLY = "/Notice/delReply";//删除通知公告
    public static final String GET_NOTICE_STU_LIST = "/Notice/getNoticeStuList";//获取通知对应的学员列表
    //班车乘坐
    public static final String GET_BUS_LIST = "/ShuttleBus/getBusList";//通过日期获取当日班车信息
    public static final String GET_ORGANIZER = "/ShuttleBus/getOrganizer";//获取组织人员信息
    public static final String GET_ORDER_STU = "/ShuttleBus/getOrderStu";//获取乘坐班车学员

    //预约练车
    public static final String GET_COURSE_ORDER_LISTS="/OrderCourse/orderLists";
    public static final String GET_COURSE_STUDENT_LISTS ="/OrderCourse/studentList";
    public static final String GET_COURSE_SYSTEM="/OrderCourse/getSystem";
    public static final String SET_COURSE_SYSTEM="/OrderCourse/systems";
    public static final String GET_COURSE_TRAIN_MODEL="/OrderCourse/getTrainModel";
    public static final String ADD_COURSE_TRAIN_MODEL="/OrderCourse/addTrainModel";
    public static final String DEL_COURSE_TRAIN_MODEL="/OrderCourse/delTrainModel";
    public static final String GET_COURSE_ORDER_TIME_LIST="/OrderCourse/orderTimeList";
}
