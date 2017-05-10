package com.wtc.xmut.taoschool.domain;

public class InquiryExt extends User {

    private int inquiryid;
    private String ishopname;
    private String iprice;
    private String idescription;

    public int getInquiryid() {
        return inquiryid;
    }

    public void setInquiryid(int inquiryid) {
        this.inquiryid = inquiryid;
    }

    private String itime;

    public String getIshopname() {
        return ishopname;
    }

    public void setIshopname(String ishopname) {
        this.ishopname = ishopname;
    }

    public String getIprice() {
        return iprice;
    }

    public void setIprice(String iprice) {
        this.iprice = iprice;
    }

    public String getIdescription() {
        return idescription;
    }

    public void setIdescription(String idescription) {
        this.idescription = idescription;
    }

    public String getItime() {
        return itime;
    }

    public void setItime(String itime) {
        this.itime = itime;
    }
}
