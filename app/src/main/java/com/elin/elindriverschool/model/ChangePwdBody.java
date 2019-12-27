package com.elin.elindriverschool.model;

/**
 * Created by imac on 16/12/28.
 */

public class ChangePwdBody {
    private String coach_idnum;
    private String coach_phone;
    private String coach_name;
    private String coach_newpwd;

    public String getCoach_idnum() {
        return coach_idnum;
    }

    public void setCoach_idnum(String coach_idnum) {
        this.coach_idnum = coach_idnum;
    }

    public String getCoach_name() {
        return coach_name;
    }

    public void setCoach_name(String coach_name) {
        this.coach_name = coach_name;
    }

    public String getCoach_newpwd() {
        return coach_newpwd;
    }

    public void setCoach_newpwd(String coach_newpwd) {
        this.coach_newpwd = coach_newpwd;
    }

    public String getCoach_phone() {
        return coach_phone;
    }

    public void setCoach_phone(String coach_phone) {
        this.coach_phone = coach_phone;
    }


//    @Override
//    public String toString() {
//        return "{" + "coach_idnum='" + coach_idnum + '\'' + ", coach_password='" + coach_password + '\'' + '}';
//    }
}
