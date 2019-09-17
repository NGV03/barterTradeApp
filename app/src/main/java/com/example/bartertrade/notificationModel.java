package com.example.bartertrade;

public class notificationModel {
    String nid, ntitle, ncontext;
    Boolean nstatus;

    public notificationModel(){

    }

    public notificationModel(String id, String title, String context, Boolean status){
        this.nid = id;
        this.ntitle = title;
        this.ncontext = context;
        this.nstatus = status;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public String getNcontext() {
        return ncontext;
    }

    public void setNcontext(String ncontext) {
        this.ncontext = ncontext;
    }

    public Boolean getNstatus() {
        return nstatus;
    }

    public void setNstatus(Boolean nstatus) {
        this.nstatus = nstatus;
    }
}
