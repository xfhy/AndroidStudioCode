package com.xfhy.filterkeyview.widget.model;

/**
 * Created by feiyang on 2017/12/3 17:58
 * Description : 关键词筛选 model
 */
public class FilterKeyModel {

    /**
     * 关键词名称
     */
    public String keyName;
    /**
     * 该关键词是否能展开
     */
    public boolean canExpand;
    /**
     * 该项当前展开状态
     */
    public boolean isExpand;
    /**
     * 该项是否被选择
     */
    public boolean isChecked;

    @Override
    public String toString() {
        return "FilterKeyModel{" +
                "keyName='" + keyName + '\'' +
                ", canExpand=" + canExpand +
                ", isExpand=" + isExpand +
                ", isChecked=" + isChecked +
                '}';
    }
}
