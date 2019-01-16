package com.tmxmall.fileapi.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class SimpleSegment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String _id;

    private String documentId;

    private String translationUnitId;

    private List<Atom> srcSegmentAtoms;

    private List<Atom> tgtSegmentAtoms;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTranslationUnitId() {
        return translationUnitId;
    }

    public void setTranslationUnitId(String translationUnitId) {
        this.translationUnitId = translationUnitId;
    }

    public List<Atom> getSrcSegmentAtoms() {
        return srcSegmentAtoms;
    }

    public void setSrcSegmentAtoms(List<Atom> srcSegmentAtoms) {
        this.srcSegmentAtoms = srcSegmentAtoms;
    }

    public List<Atom> getTgtSegmentAtoms() {
        return tgtSegmentAtoms;
    }

    public void setTgtSegmentAtoms(List<Atom> tgtSegmentAtoms) {
        this.tgtSegmentAtoms = tgtSegmentAtoms;
    }

    @Override
    public String toString() {
        return "SimpleSegment{" +
                "_id='" + _id + '\'' +
                ", documentId='" + documentId + '\'' +
                ", translationUnitId='" + translationUnitId + '\'' +
                ", srcSegmentAtoms=" + srcSegmentAtoms +
                ", tgtSegmentAtoms=" + tgtSegmentAtoms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleSegment that = (SimpleSegment) o;
        return Objects.equals(_id, that._id) &&
                Objects.equals(documentId, that.documentId) &&
                Objects.equals(translationUnitId, that.translationUnitId) &&
                Objects.equals(srcSegmentAtoms, that.srcSegmentAtoms) &&
                Objects.equals(tgtSegmentAtoms, that.tgtSegmentAtoms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, documentId, translationUnitId, srcSegmentAtoms, tgtSegmentAtoms);
    }
}
