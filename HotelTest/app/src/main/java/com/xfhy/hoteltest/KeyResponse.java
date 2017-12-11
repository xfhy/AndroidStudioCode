package com.xfhy.hoteltest;

import java.util.List;

/**
 * Created by feiyang on 2017/11/29 16:14
 * Description : 一个大组
 */
public class KeyResponse {

    /*private String content;
    private boolean isHeader = false;

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }*/

    private boolean isExpand;
    private String headerText;
    private List<String> normalList;

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public List<String> getNormalList() {
        return normalList;
    }

    public void setNormalList(List<String> normalList) {
        this.normalList = normalList;
    }

    @Override
    public String toString() {
        return "KeyResponse{" +
                "isExpand=" + isExpand +
                ", headerText='" + headerText + '\'' +
                ", normalList=" + normalList +
                '}';
    }
}
