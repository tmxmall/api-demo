package com.tmxmall.fileapi.model;

import java.io.Serializable;
import java.util.Objects;

public class Atom implements Serializable {
    private static final long serialVersionUID = 1L;

    private String atomId;
    private String data;
    private String textStyle;

    private boolean isHidden = false;
    private String tagType;
    private Integer tagId;

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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "Atom{" +
                "atomId='" + atomId + '\'' +
                ", data='" + data + '\'' +
                ", textStyle='" + textStyle + '\'' +
                ", isHidden=" + isHidden +
                ", tagType='" + tagType + '\'' +
                ", tagId=" + tagId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atom atom = (Atom) o;
        return isHidden == atom.isHidden &&
                Objects.equals(atomId, atom.atomId) &&
                Objects.equals(data, atom.data) &&
                Objects.equals(textStyle, atom.textStyle) &&
                Objects.equals(tagType, atom.tagType) &&
                Objects.equals(tagId, atom.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atomId, data, textStyle, isHidden, tagType, tagId);
    }
}

