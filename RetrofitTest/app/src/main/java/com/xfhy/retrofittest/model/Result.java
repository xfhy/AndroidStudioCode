package com.xfhy.retrofittest.model;

/**
 * Created by xfhy on 2017/8/5.
 */

public class Result {

    private String shouji;
    private String province;
    private String city;
    private String company;
    private String cardtype;
    private String areacode;

    public String getShouji() {
        return shouji;
    }

    public void setShouji(String shouji) {
        this.shouji = shouji;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    @Override
    public String toString() {
        return "Result{" +
                "shouji='" + shouji + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", company='" + company + '\'' +
                ", cardtype='" + cardtype + '\'' +
                ", areacode='" + areacode + '\'' +
                '}';
    }
}
