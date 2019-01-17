package com.jd.appoint.web.eggshell;

import com.jd.adminlte4j.annotation.Form;

/**
 * Created by luqiang3 on 2018/5/28.
 */
@Form(span = 3)
public class EggshellModel {

    private String command;

    private String hasConfirm;

    private String error;

    private String info;

    //get set
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getHasConfirm() {
        return hasConfirm;
    }

    public void setHasConfirm(String hasConfirm) {
        this.hasConfirm = hasConfirm;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
