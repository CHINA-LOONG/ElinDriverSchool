package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/2/20.
 */

public class DynamicBean {


    /**
     * carousels : [{"inner_id":"8","carousel_img":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e6164c968c.jpg","carousel_url":"","school_id":"1"},{"inner_id":"9","carousel_img":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e63e11e1a1.jpg","carousel_url":"","school_id":"1"},{"inner_id":"10","carousel_img":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e63eb21f09.jpg","carousel_url":"","school_id":"1"}]
     * rc : 0
     * des :
     * data_list : [{"inner_id":"3","dynamic_img":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e64c4dc235.png","dynamic_title":"预约考试","dynamic_content":"<p><span style=\"font-size: 18px;\">车管所受理业务后，<\/span><\/p><p><span style=\"font-size: 18px;\"><span style=\"font-size: 14px;\">请学员登陆网址：<\/span><span style=\"font-size: 14px; text-decoration: none;\"><a href=\"http://sd.122.gov.cn\" target=\"_self\"><strong>http://sd.122.gov.cn<\/strong><\/a><strong>&nbsp;<\/strong><\/span><span style=\"font-size: 14px;\">考试预约<\/span><\/span><\/p><p><img src=\"/ueditor/php/upload/image/20170613/1497343160609908.png\" title=\"1485156051299.png\" alt=\"1485156051299.png\"/><\/p>","dynamic_date":"2017-01-07","type":"1","intro":null,"music":null,"school_id":"1","coach_infor":"","coach_phone":"","coach_wx":"","coach_wx_qrcode":""},{"inner_id":"4","dynamic_img":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e65bb4441f.png","dynamic_title":"互联网预约操作说明","dynamic_content":"<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 10px; text-indent: 2em; color: rgb(85, 85, 85); font-family: &#39;Microsoft YaHei&#39;, &#39;Helvetica Neue&#39;, Helvetica, Arial, sans-serif; font-size: 14px; line-height: 23.8px; white-space: normal;\"><strong>办理业务用户类型：<\/strong>互联网注册的个人用户。<\/p><h5 style=\"margin: 0px; font-family: 微软雅黑; line-height: 20px; color: rgb(85, 85, 85); text-rendering: optimizeLegibility; font-size: 16px; padding: 10px; white-space: normal;\"><a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">♦ 业务流程及操作说明<\/a><\/h5><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 10px; text-indent: 2em; color: rgb(85, 85, 85); font-family: &#39;Microsoft YaHei&#39;, &#39;Helvetica Neue&#39;, Helvetica, Arial, sans-serif; font-size: 14px; line-height: 23.8px; white-space: normal;\"><a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyynormal\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\"><strong>普通考试预约<\/strong><\/a><\/p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 10px; text-indent: 2em; color: rgb(85, 85, 85); font-family: &#39;Microsoft YaHei&#39;, &#39;Helvetica Neue&#39;, Helvetica, Arial, sans-serif; font-size: 14px; line-height: 23.8px; white-space: normal;\"><a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyynormalB1\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【选择考试科目】<\/a>→&nbsp;<a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyynormalB2\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【业务须知】<\/a>→&nbsp;<a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyynormalB3\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【选择考试地点】<\/a>→&nbsp;<a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyynormalB4\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【选择考试场次】<\/a>→&nbsp;<a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyynormalB5\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【确认并提交】<\/a><\/p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 10px; text-indent: 2em; color: rgb(85, 85, 85); font-family: &#39;Microsoft YaHei&#39;, &#39;Helvetica Neue&#39;, Helvetica, Arial, sans-serif; font-size: 14px; line-height: 23.8px; white-space: normal;\"><a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyygreen\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\"><strong>优先考试预约<\/strong><\/a><\/p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px 10px; text-indent: 2em; color: rgb(85, 85, 85); font-family: &#39;Microsoft YaHei&#39;, &#39;Helvetica Neue&#39;, Helvetica, Arial, sans-serif; font-size: 14px; line-height: 23.8px; white-space: normal;\"><a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyygreenB1\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【选择考试科目】<\/a>→&nbsp;<a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyygreenB2\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【业务须知】<\/a>→&nbsp;<a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyygreenB3\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【选择考试地点】<\/a>→&nbsp;<a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyygreenB4\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【选择考试场次】<\/a>→&nbsp;<a href=\"https://sd.122.gov.cn/views/help/help-netksyy.html#ksyygreenB5\" style=\"color: rgb(16, 33, 139); text-decoration: none; cursor: pointer;\">【确认并提交】<\/a><\/p><p style=\";margin-bottom:0;line-height:25px\"><span style=\";color:#FF2941\">自主约考流程<\/span><\/p><p style=\";text-indent: 32px;line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">登录网址<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u2014\u2014<\/span><span style=\";color:#3E3E3E\">个人登录<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u2014\u2014<\/span><span style=\";color:#3E3E3E\">驾驶证业务<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u2014\u2014<\/span><span style=\";color:#3E3E3E\">考试预约<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">{<\/span><span style=\";color:#3E3E3E\">对上一门考试作出评价（若预约科目二<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">/<\/span><span style=\";color:#3E3E3E\">科目三）<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u2014\u2014<\/span><span style=\";color:#3E3E3E\">业务须知<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u2014\u2014<\/span><span style=\";color:#3E3E3E\">确认信息<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u2014\u2014<\/span><span style=\";color:#3E3E3E\">选择科目和考场<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u2014\u2014<\/span><span style=\";color:#3E3E3E\">选择考试场次<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u2014\u2014<\/span><span style=\";color:#3E3E3E\">确认提交<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">}<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\"><br/> 1<\/span><span style=\";color:#3E3E3E\">、自主约考网址是什么？<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">答：打开浏览器输入网址<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\"><a href=\"http://sd.122.gov.cn/\"><span style=\"color:red;text-underline: none\">http://sd.122.gov.cn<\/span><\/a><\/span><span style=\";color:#3E3E3E\">进入<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">交通安全综合服务管理平台<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201d<\/span><\/p><p style=\"min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><\/p><p style=\"min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-size:16px;font-family:宋体\"><br/><\/span><\/p><p style=\"min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-size:16px;font-family:宋体\"><img src=\"http://elindriving.oss-cn-hangzhou.aliyuncs.com/img/1493103105266.jpg\" title=\"1493103105266.jpg\" alt=\"1493103105266.jpg\"/><\/span><\/p><p style=\";margin-bottom:0;text-indent:32px;line-height:25px\"><span style=\";color:#3E3E3E\">登录问题解答<\/span><\/p><p style=\"min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-size:16px;font-family:宋体\"><img src=\"http://elindriving.oss-cn-hangzhou.aliyuncs.com/img/1493103120410.jpg\" title=\"1493103120410.jpg\" alt=\"1493103120410.jpg\"/><\/span><\/p><p style=\";margin-bottom:0;line-height:25px\"><span style=\"font-family:Helvetica;color:#3E3E3E\"><br/> 2<\/span><span style=\";color:#3E3E3E\">、如何登录？<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">答：进入平台后右上角有：用户注册、个人登录、单位登录三项。<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u0081<\/span><span style=\";color:#3E3E3E\">如果您已经到临沂市车管所办理过面签业务，您可以点击<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">个人登录<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201d<\/span><span style=\";color:#3E3E3E\">，使用接收的短信中的用户名和密码登录即可。<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">未办理过面签业务的学员，您可以点击<\/span><span style=\"font-family: Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">用户注册<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201d<\/span><span style=\";color:#3E3E3E\">，先申请账号和密码再登录。<\/span><\/p><p><span style=\"font-size:16px;font-family:宋体\"><img src=\"http://elindriving.oss-cn-hangzhou.aliyuncs.com/img/1493103131018.jpg\" title=\"1493103131018.jpg\" alt=\"1493103131018.jpg\"/><\/span><\/p><p style=\";margin-bottom:0;line-height:25px\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp;<\/span><\/p><p style=\";margin-bottom:0;line-height:25px\"><span style=\"font-family:Helvetica;color:#3E3E3E\"> 3<\/span><span style=\";color:#3E3E3E\">、密码错误或忘记密码该如何操作？<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">如果是忘记密码或密码错误，您可点击<\/span><span style=\"font-family: Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">找回密码<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201d<\/span><span style=\";color:#3E3E3E\">，但如果您面签时预留的手机号码为非山东省的联通手机号，可能会收不到短信，出现以上情况您需要到临沂市车管所<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">29<\/span><span style=\";color:#3E3E3E\">号面签窗口办理变更手机号业务。<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">4<\/span><span style=\";color:#3E3E3E\">、用户注册不上，怎么办？（提示有车或电话不对等所有问题）<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">答：如果注册遇到问题，注册不上，那么需要到车管所<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">29<\/span><span style=\";color:#3E3E3E\">号面签窗口变更（手机号、密码等所有问题）<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp;<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">考试预约操作解答<\/span><\/p><p style=\"min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-size:16px;font-family:宋体\"><img src=\"http://elindriving.oss-cn-hangzhou.aliyuncs.com/img/1493103152884.jpg\" title=\"1493103152884.jpg\" alt=\"1493103152884.jpg\"/><\/span><\/p><p style=\";margin-bottom:0;line-height:25px\"><span style=\"font-family:Helvetica;color:#3E3E3E\"><br/> 1<\/span><span style=\";color:#3E3E3E\">、若预约科目二<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">/<\/span><span style=\";color:#3E3E3E\">科目三考试须对上一门考试进行评价，点<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">评价<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201d<\/span><span style=\";color:#3E3E3E\">选择五角星，填写文字评价信息（如满意，很好等），点击下一步；<\/span><\/p><p style=\"min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-size:16px;font-family:宋体\"><img src=\"http://elindriving.oss-cn-hangzhou.aliyuncs.com/img/1493103166209.jpg\" title=\"1493103166209.jpg\" alt=\"1493103166209.jpg\"/><\/span><\/p><p style=\";margin-bottom:0;line-height:25px\"><span style=\"font-family:Helvetica;color:#3E3E3E\"><br/> 2<\/span><span style=\";color:#3E3E3E\">、您需要仔细阅读业务须知内容，了解预约考试的相关条款，最后须在底部的<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">阅读并同意<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201d<\/span><span style=\";color:#3E3E3E\">前打<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">√<\/span><span style=\";color:#3E3E3E\">，点击下一步；<\/span><\/p><p style=\"min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-size:16px;font-family:宋体\"><img src=\"http://elindriving.oss-cn-hangzhou.aliyuncs.com/img/1493103175390.jpg\" title=\"1493103175390.jpg\" alt=\"1493103175390.jpg\"/><\/span><\/p><p style=\";margin-bottom:0;line-height:25px\"><span style=\"font-family:Helvetica;color:#3E3E3E\"><br/> 3<\/span><span style=\";color:#3E3E3E\">、再次确认个人信息，主要确认证件号码、姓名、申请准驾车型等，确认无误后点击下一步；<\/span><\/p><p style=\"min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-size:16px;font-family:宋体\"><img src=\"http://elindriving.oss-cn-hangzhou.aliyuncs.com/img/1493103184581.jpg\" title=\"1493103184581.jpg\" alt=\"1493103184581.jpg\"/><\/span><\/p><p style=\";margin-bottom:0;line-height:25px\"><span style=\"font-family:Helvetica;color:#3E3E3E\"><br/> 4<\/span><span style=\";color:#3E3E3E\">、选择考试科目和考场。<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">公交驾校使用考试场点：<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp; &nbsp; &nbsp;<\/span><span style=\";color:#3E3E3E\">科目一：<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">临沂市科目一第一考场<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201d<\/span><\/p><p style=\"margin: 0 0 0 88px;line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp; &nbsp; &nbsp;<\/span><span style=\";color:#3E3E3E\">科目二：<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">临沂市科目二第十七考场（正直）、临沂市科目二第二十四考场（长城）<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp; &nbsp; &nbsp;<\/span><span style=\";color:#3E3E3E\">科目三华健考场：<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">临沂市科目三第一考场（经济技术开发区华夏路）<\/span><span style=\"font-family: Helvetica;color:#3E3E3E\">&nbsp;&nbsp;<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp; &nbsp; &nbsp;<\/span><span style=\";color:#3E3E3E\">科目三长城考场：<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">临沂市科目三第十三考场（经济技术开发区杭州路）<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\"><br/> 5<\/span><span style=\";color:#3E3E3E\">、选择考试场次，根据考试日期选择（比如<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">2<\/span><span style=\";color:#3E3E3E\">月<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">23<\/span><span style=\";color:#3E3E3E\">号考试，那么考试日期就是<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">2016.2.23\u20142016.2.23<\/span><span style=\";color:#3E3E3E\">），考试场次切记选择上午，待选框变为蓝色即表示选择成功，选好考场和时间，输入验证码，点击下一步；<img src=\"http://elindriving.oss-cn-hangzhou.aliyuncs.com/img/1493103208932.jpg\" title=\"1493103208932.jpg\" alt=\"1493103208932.jpg\"/><\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\"><br/> 6<\/span><span style=\";color:#3E3E3E\">、再次核对预考信心，查看考试科目、场次、时间、场地等，确认无误后点击完成。<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\"><br/> 7<\/span><span style=\";color:#3E3E3E\">、约考成功后，系统会自动生成一个<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">业务流水号<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201d<\/span><span style=\";color:#3E3E3E\">，请学员朋友记住该流水号，方便以后在<\/span><span style=\"font-family: Helvetica;color:#3E3E3E\">\u201c<\/span><span style=\";color:#3E3E3E\">网办进度<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">\u201d<\/span><span style=\";color:#3E3E3E\">中查询业务状态，一般为考试前两天可以查到。<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp;<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">面签的意义<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp;&nbsp;&nbsp;&nbsp;<\/span><span style=\";color:#3E3E3E\">面签是为自主约考做准备，本人携带相关证件到临沂市车管所录入个人信息至交通安全综合服务管理平台，面对面签约。面签成功后可在网上办理相关业务，如学车约考、处理违章、预选号牌、换证、查询违章等业务，方便快捷。<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp;<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">如何办理面签<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp;&nbsp;&nbsp;<\/span><span style=\";color:#3E3E3E\">本人携带身份证（外地人员另加居住证），填写互联网个人用户注册申请表。驾校学员可由驾校统一组织到临沂市车管所办理。若自行前往可在临沂市车管所一楼业务大厅<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">29<\/span><span style=\";color:#3E3E3E\">号窗口办理。面签成功后，会收到提醒短信告知用户名和密码。<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">&nbsp;<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\";color:#3E3E3E\">注：<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">1<\/span><span style=\";color:#3E3E3E\">、自主约考需至少提前<\/span><span style=\"font-family:Helvetica;color:#3E3E3E\">3<\/span><span style=\";color:#3E3E3E\">个工作日进行预约；<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">2<\/span><span style=\";color:#3E3E3E\">、预约成功后系统会自动生成业务流水号，可自行登录查询办理进度；<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">3<\/span><span style=\";color:#3E3E3E\">、如需在线约考，建议在办理完面签业务后再进行在线约考；通过网络自行注册约考的，必须在考试前到临沂市车管所办理面签业务方可约考成功，否则约考失败（现暂不提倡本条操作，因后续业务很麻烦，易出现异常）；<\/span><\/p><p style=\";line-height: 25px;min-height: 1em;font-stretch: normal;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important\"><span style=\"font-family:Helvetica;color:#3E3E3E\">4<\/span><span style=\";color:#3E3E3E\">、选择考试场点时，请务必选择本驾校的考试场点。<\/span><\/p><p><br/><\/p>","dynamic_date":"2017-01-07","type":"1","intro":null,"music":null,"school_id":"1","coach_infor":"","coach_phone":"","coach_wx":"","coach_wx_qrcode":""},{"inner_id":"5","dynamic_img":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e64768d6f4.png","dynamic_title":"科目一考试","dynamic_content":"<p><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">科目一，又称<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/view/9678000.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">科目一<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">理论考试、驾驶员理论考试，是机动车<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/view/658668.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">驾驶证<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">考核的一部分。<\/span><\/p><p><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">根据《<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/view/242279.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">机动车驾驶证申领和使用规定<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">》，考试内容包括驾车理论基础、道路安全法律法规、地方性法规等相关知识。<\/span><\/p><p><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">考试形式为上机考试，100道题，90分及以上过关。<\/span><\/p>","dynamic_date":"2017-01-07","type":"1","intro":null,"music":null,"school_id":"1","coach_infor":"","coach_phone":"","coach_wx":"","coach_wx_qrcode":""},{"inner_id":"6","dynamic_img":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e6547198c3.png","dynamic_title":"科目二考试","dynamic_content":"<p><a target=\"_blank\" href=\"http://baike.baidu.com/view/10803040.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">科目二<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">，又称小<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/subview/856049/13112803.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">路考<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">，是机动车<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/view/658668.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">驾驶证<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">考核的一部分，是<\/span><span style=\"font-weight: 700; color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">场地驾驶技能考试科目<\/span><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">的简称，<\/span><\/p><p><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">考试项目包括<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/view/7961511.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">倒车入库<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">、<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/view/3250287.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">侧方停车<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">、<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/view/5639227.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">坡道定点停车和起步<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">、<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/view/1625511.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">直角转弯<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">、<\/span><a target=\"_blank\" href=\"http://baike.baidu.com/view/6177881.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none; font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; white-space: normal; background-color: rgb(255, 255, 255);\">曲线行驶<\/a><span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; font-size: 14px; line-height: 24px; text-indent: 28px; background-color: rgb(255, 255, 255);\">（俗称S弯）五项必考。<\/span><\/p>","dynamic_date":"2017-01-07","type":"1","intro":null,"music":null,"school_id":"1","coach_infor":"","coach_phone":"","coach_wx":"","coach_wx_qrcode":""},{"inner_id":"7","dynamic_img":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e6579e9814.png","dynamic_title":"科目三考试","dynamic_content":"<p>科目三，又称大路考，是机动车<a target=\"_blank\" href=\"http://baike.baidu.com/view/658668.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none;\">驾驶证<\/a>考核的一部分，是机动车驾驶人考试中道路驾驶技能和安全文明驾驶常识考试科目的简称。<\/p><p>不同的准驾车型道路驾驶技能考试内容不同，一般包括：上车准备、<a target=\"_blank\" href=\"http://baike.baidu.com/view/1439460.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none;\">起步<\/a>、<a target=\"_blank\" href=\"http://baike.baidu.com/view/8193467.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none;\">直线行驶<\/a>、加减挡位操作、变更车道、靠边停车、直行通过路口、路口左转弯、路口右转弯、通过<a target=\"_blank\" href=\"http://baike.baidu.com/view/911777.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none;\">人行横道线<\/a>、通过学校区域、通过公共汽车站、<a target=\"_blank\" href=\"http://baike.baidu.com/view/954198.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none;\">会车<\/a>、<a target=\"_blank\" href=\"http://baike.baidu.com/view/1746461.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none;\">超车<\/a>、<a target=\"_blank\" href=\"http://baike.baidu.com/view/430813.htm\" style=\"color: rgb(19, 110, 194); text-decoration: none;\">掉头<\/a>、夜间行驶。<\/p><p>安全文明驾驶常识考试内容包括：安全文明驾驶操作要求、恶劣气象和复杂道路条件下的安全驾驶知识、爆胎等紧急情况下的临危处置方法以及发生交通事故后的处置知识等<\/p><p><br/><\/p>","dynamic_date":"2017-01-07","type":"1","intro":null,"music":null,"school_id":"1","coach_infor":"","coach_phone":"","coach_wx":"","coach_wx_qrcode":""},{"inner_id":"8","dynamic_img":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e5948d0e6e.png","dynamic_title":"驾考宝典","dynamic_content":"<p><span style=\"color: rgb(68, 68, 68); line-height: 30px; font-size: 20px; font-family: 宋体, SimSun;\">&nbsp; &nbsp; 驾考宝典是专业的交规学习软件，集交规章节练习、顺序练习、随机练习、模拟考试于一身，便于随时阅读交规做练习。驾考宝典，全国千家驾校推荐，学车考驾照必备！独家内部绝密题库、星级教练专业指导、驾驶员法培专家专业解读《机动车驾驶教学与考试大纲》，剖析新交规讲解考驾照技巧，驾驶证、资格证理论、操作全部内容。<\/span><\/p><p><span style=\"color: rgb(68, 68, 68); line-height: 30px; font-size: 20px; font-family: 宋体, SimSun;\">&nbsp; &nbsp; &nbsp; 网址：<\/span><\/p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<a href=\"http://www.jiakaobaodian.com/mnks/kemu1/car-linyi.html\" target=\"_self\">http://www.jiakaobaodian.com/mnks/kemu1/car-linyi.html<\/a><\/p>","dynamic_date":"2017-01-07","type":"1","intro":null,"music":null,"school_id":"1","coach_infor":"","coach_phone":"","coach_wx":"","coach_wx_qrcode":""}]
     */

    private int rc;
    private String des;
    private List<CarouselsBean> carousels;
    private List<DataListBean> data_list;

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

    public List<CarouselsBean> getCarousels() {
        return carousels;
    }

    public void setCarousels(List<CarouselsBean> carousels) {
        this.carousels = carousels;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class CarouselsBean {
        /**
         * inner_id : 8
         * carousel_img : http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e6164c968c.jpg
         * carousel_url :
         * school_id : 1
         */

        private String inner_id;
        private String carousel_img;
        private String carousel_url;
        private String school_id;

        public String getInner_id() {
            return inner_id;
        }

        public void setInner_id(String inner_id) {
            this.inner_id = inner_id;
        }

        public String getCarousel_img() {
            return carousel_img;
        }

        public void setCarousel_img(String carousel_img) {
            this.carousel_img = carousel_img;
        }

        public String getCarousel_url() {
            return carousel_url;
        }

        public void setCarousel_url(String carousel_url) {
            this.carousel_url = carousel_url;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }
    }

    public static class DataListBean {
        /**
         * inner_id : 3
         * dynamic_img : http://elindriving.oss-cn-hangzhou.aliyuncs.com/2017-05-31/592e64c4dc235.png
         * dynamic_title : 预约考试
         * dynamic_content : <p><span style="font-size: 18px;">车管所受理业务后，</span></p><p><span style="font-size: 18px;"><span style="font-size: 14px;">请学员登陆网址：</span><span style="font-size: 14px; text-decoration: none;"><a href="http://sd.122.gov.cn" target="_self"><strong>http://sd.122.gov.cn</strong></a><strong>&nbsp;</strong></span><span style="font-size: 14px;">考试预约</span></span></p><p><img src="/ueditor/php/upload/image/20170613/1497343160609908.png" title="1485156051299.png" alt="1485156051299.png"/></p>
         * dynamic_date : 2017-01-07
         * type : 1
         * intro : null
         * music : null
         * school_id : 1
         * coach_infor :
         * coach_phone :
         * coach_wx :
         * coach_wx_qrcode :
         */

        private String inner_id;
        private String dynamic_img;
        private String dynamic_title;
        private String dynamic_content;
        private String dynamic_date;
        private String type;
        private Object intro;
        private Object music;
        private String school_id;
        private String coach_infor;
        private String coach_phone;
        private String coach_wx;
        private String coach_wx_qrcode;

        public String getInner_id() {
            return inner_id;
        }

        public void setInner_id(String inner_id) {
            this.inner_id = inner_id;
        }

        public String getDynamic_img() {
            return dynamic_img;
        }

        public void setDynamic_img(String dynamic_img) {
            this.dynamic_img = dynamic_img;
        }

        public String getDynamic_title() {
            return dynamic_title;
        }

        public void setDynamic_title(String dynamic_title) {
            this.dynamic_title = dynamic_title;
        }

        public String getDynamic_content() {
            return dynamic_content;
        }

        public void setDynamic_content(String dynamic_content) {
            this.dynamic_content = dynamic_content;
        }

        public String getDynamic_date() {
            return dynamic_date;
        }

        public void setDynamic_date(String dynamic_date) {
            this.dynamic_date = dynamic_date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getIntro() {
            return intro;
        }

        public void setIntro(Object intro) {
            this.intro = intro;
        }

        public Object getMusic() {
            return music;
        }

        public void setMusic(Object music) {
            this.music = music;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getCoach_infor() {
            return coach_infor;
        }

        public void setCoach_infor(String coach_infor) {
            this.coach_infor = coach_infor;
        }

        public String getCoach_phone() {
            return coach_phone;
        }

        public void setCoach_phone(String coach_phone) {
            this.coach_phone = coach_phone;
        }

        public String getCoach_wx() {
            return coach_wx;
        }

        public void setCoach_wx(String coach_wx) {
            this.coach_wx = coach_wx;
        }

        public String getCoach_wx_qrcode() {
            return coach_wx_qrcode;
        }

        public void setCoach_wx_qrcode(String coach_wx_qrcode) {
            this.coach_wx_qrcode = coach_wx_qrcode;
        }
    }
}
