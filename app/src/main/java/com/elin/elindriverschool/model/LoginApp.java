package com.elin.elindriverschool.model;

import com.elin.elindriverschool.util.MD5Util;

/**
 * Created by imac on 16/12/28.
 */

public class LoginApp {
    private String coach_idnum;
    private String coach_password;
//    private String coach_clientid;
    private String coach_client_type;

    public String getCoach_client_type() {
        return coach_client_type;
    }

    public void setCoach_client_type(String coach_client_type) {
        this.coach_client_type = coach_client_type;
    }

    public String getCoach_idnum() {
        return coach_idnum;
    }

    public void setCoach_idnum(String coach_idnum) {
        this.coach_idnum = coach_idnum;
    }

    public String getCoach_password() {
        return coach_password;
    }

    public void setCoach_password(String coach_password) {
        this.coach_password = coach_password;
    }

//    @Override
//    public String toString() {
//        return "{"+"\"coach_idnum\""+":"+"\""+coach_idnum+"\""+","+"\"coach_password\""+":"+"\""+ MD5Util.get32MD5Str(coach_password)+"\""+"}";
//    }


    @Override
    public String toString() {
        return "{"+"\"coach_idnum\""+":"+"\""+coach_idnum+"\""+","
                +"\"coach_password\""+":"+"\""+ MD5Util.get32MD5Str(coach_password)+"\""+","
//                +"\"coach_clientid\""+":"+"\""+coach_clientid+"\""+","
                +"\"coach_client_type\""+":"+"\""+coach_client_type+"\""
                +"}";
    }

//    public String getCoach_clientid() {
//        return coach_clientid;
//    }
//
//    public void setCoach_clientid(String coach_clientid) {
//        this.coach_clientid = coach_clientid;
//    }
}
