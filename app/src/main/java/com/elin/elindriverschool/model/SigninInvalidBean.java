package com.elin.elindriverschool.model;

/**
 * Created by 17535 on 2017/10/25.
 */

public class SigninInvalidBean {

    /**
     * title : 登陆失效
     * content : 您的账号已从其他设备登陆！
     */

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
