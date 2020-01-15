package com.tmxmall.fileapi.model;

import java.io.Serializable;
import java.util.Objects;

public class Atom implements Serializable {
    private static final long serialVersionUID = 1L;

    // ==== Code 和 Text 共有的属性 =====
    private String atomId;  // 从 0 开始数
    private String data;
    private String textStyle; //类别  regular  tag

    // ===== Code 专属属性 =====
    private boolean isHidden = false; //是否省略
    private String tagType;  //TAG的类型  OPENING | CLOSING | PLACEHOLDER | VIRTUAL_OPENING | VIRTUAL_CLOSING
    private String tagId;
    private boolean isCustom = false;// 是否是自定义

    public Atom(){

    }

    public String getAtomId() {
        return atomId;
    }

    public void setAtomId(String atomId) {
        this.atomId = atomId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    @Override
    public String toString() {
        return "Atom{" +
                "atomId='" + atomId + '\'' +
                ", data='" + data + '\'' +
                ", textStyle='" + textStyle + '\'' +
                ", isHidden=" + isHidden +
                ", tagType='" + tagType + '\'' +
                ", tagId='" + tagId + '\'' +
                ", isCustom=" + isCustom +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atom atom = (Atom) o;
        return isHidden == atom.isHidden &&
                isCustom == atom.isCustom &&
                Objects.equals(atomId, atom.atomId) &&
                Objects.equals(data, atom.data) &&
                Objects.equals(textStyle, atom.textStyle) &&
                Objects.equals(tagType, atom.tagType) &&
                Objects.equals(tagId, atom.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atomId, data, textStyle, isHidden, tagType, tagId, isCustom);
    }
}

