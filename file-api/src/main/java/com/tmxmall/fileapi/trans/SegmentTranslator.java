package com.tmxmall.fileapi.trans;

import com.tmxmall.fileapi.model.Atom;
import com.tmxmall.fileapi.model.SimpleSegment;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SegmentTranslator {       // TODO 非译元素的特殊处理

    private static final String REGULAR_STYLE = "regular";

    private List<SimpleSegment> toBeTranslated;

    private Translator translator;

    public SegmentTranslator(List<SimpleSegment> toBeTranslated, Translator translator) {
        this.toBeTranslated = toBeTranslated;
        this.translator = translator;
    }

    public List<SimpleSegment> trans(){
        if(toBeTranslated == null || toBeTranslated.isEmpty()){
            return toBeTranslated;
        }

        for(SimpleSegment segment : toBeTranslated){
            segment.setTgtSegmentAtoms(analysisAtom(segment.getSrcSegmentAtoms()));
        }

        return toBeTranslated;
    }

    private  List<Atom> analysisAtom(List<Atom> list){
        int length = 0;
        int count = 0;
        for(Atom atom : list){
            if(StringUtils.equals(REGULAR_STYLE, atom.getTextStyle())){
                length += atom.getData().length();
                if(atom.getData().length() <= 3){
                    count++;
                }
            }
        }
        if(length > 100 && count > 6){
            return extractText(list);
        }
        if(length > 100 && count >= 3 && count <= 5){
            return appendText(list);
        }
        if(length > 100 && count < 3){
            return segmentedText(list);
        }
        if(length > 20 && length <= 100 && count > 15){
            return list;
        }
        if(length > 20 && length <= 100 && count < 2){
            return segmentedText(list);
        }
        if(length > 20 && length <= 100){
            return specialAppendText(list);
        }
        if(length <= 20 && count < 2){
            return segmentedText(list);
        }
        if(length <= 20 && count > 5){
            // 小文本且小单词数量过多则放弃翻译直接原文填充
            return list;
        }
        return specialAppendText(list);
    }

    // 文本提取方法，将纯文本内容提取出进行翻译，并存放到对应原文中的第一个文本atom中
    private  List<Atom> extractText(List<Atom> list) {
        StringBuilder sb = new StringBuilder();
        List<Atom> tgtSegmentAtoms = new ArrayList<Atom>();
        Atom tgtAtom = null;
        Atom firstAtom = new Atom();
        int temp = 0;
        for(Atom atom : list){
            tgtAtom = new Atom();
            if(StringUtils.equals(atom.getTextStyle(), REGULAR_STYLE)){
                sb.append(atom.getData());
                if(temp == 0){
                    firstAtom.setData(atom.getData());
                    firstAtom.setTagId(atom.getTagId());
                    firstAtom.setTagType(atom.getTagType());
                    firstAtom.setTextStyle(atom.getTextStyle());
                    firstAtom.setHidden(atom.isHidden());
                    firstAtom.setAtomId(atom.getAtomId());
                    temp ++;
                }
                continue;
            }
            tgtAtom.setData(atom.getData());
            tgtAtom.setTagId(atom.getTagId());
            tgtAtom.setTagType(atom.getTagType());
            tgtAtom.setTextStyle(atom.getTextStyle());
            tgtAtom.setHidden(atom.isHidden());
            tgtAtom.setAtomId(atom.getAtomId());
            tgtSegmentAtoms.add(tgtAtom);
        }
        String res = sb.toString();
        if(StringUtils.isNotEmpty(res) && !res.matches("\\s+")){
            res = translator.trans(res);
        }
        firstAtom.setData(res);
        tgtSegmentAtoms.add(0, firstAtom);
        return tgtSegmentAtoms;
    }

    // 文本追加方法，必须保证发送的内容长度必须大于5否则将该段追加到下一个文本atom中
    private  List<Atom> appendText(List<Atom> list) {
        StringBuilder temp = new StringBuilder();
        int count = 0;
        List<Atom> tgtSegmentAtoms = new ArrayList<Atom>();
        Atom tgtAtom = null;
        for(int i = 0; i < list.size(); i++){
            Atom atom = list.get(i);
            tgtAtom = new Atom();
            String data = "";
            if(StringUtils.equals(atom.getTextStyle(), REGULAR_STYLE)){
                // count用来记录每次文本atom的位置，循环结束后count的值就是最后一个文本atom的位置
                count = i;
                temp.append(atom.getData());
                if(temp.length() > 5){
                    data = translator.trans(temp.toString());
                    temp = new StringBuilder();
                }
            }else {
                // 非文本atom直接取本身的内容
                data = atom.getData();
            }
            tgtAtom.setData(data);
            tgtAtom.setTagId(atom.getTagId());
            tgtAtom.setTagType(atom.getTagType());
            tgtAtom.setTextStyle(atom.getTextStyle());
            tgtAtom.setHidden(atom.isHidden());
            tgtAtom.setAtomId(atom.getAtomId());
            tgtSegmentAtoms.add(tgtAtom);
        }

        // temp还存在残余内容，要找到tgtAtoms中最后一个文本atom将其内容替换
        if(StringUtils.isNotEmpty(temp.toString())){
            Atom tempAtom = tgtSegmentAtoms.get(count);
            tempAtom.setData(translator.trans(temp.toString()));
            tgtSegmentAtoms.set(count, tempAtom);
        }
        return tgtSegmentAtoms;
    }

    // 按照原本atom断句发送
    private  List<Atom> segmentedText(List<Atom> list) {
        List<Atom> tgtSegmentAtoms = new ArrayList<Atom>();
        Atom tgtAtom = null;
        for(Atom atom : list){
            tgtAtom = new Atom();
            if(StringUtils.equals(atom.getTextStyle(), REGULAR_STYLE)
                    && StringUtils.isNotEmpty(atom.getData()) && !atom.getData().matches("\\s+")){
                tgtAtom.setData(translator.trans(atom.getData()));
            }else {
                tgtAtom.setData(atom.getData());
            }
            tgtAtom.setTagId(atom.getTagId());
            tgtAtom.setTagType(atom.getTagType());
            tgtAtom.setTextStyle(atom.getTextStyle());
            tgtAtom.setHidden(atom.isHidden());
            tgtAtom.setAtomId(atom.getAtomId());
            tgtSegmentAtoms.add(tgtAtom);
        }
        return tgtSegmentAtoms;
    }

    // 针对小段文本字符的特殊追加方法
    private  List<Atom> specialAppendText(List<Atom> list) {
        StringBuilder temp = new StringBuilder();
        int count = 0;
        Atom tgtAtom = null;
        List<Atom> tgtSegmentAtoms = new ArrayList<Atom>();
        for(int i = 0; i < list.size(); i++){
            Atom atom = list.get(i);
            tgtAtom = new Atom();
            if(StringUtils.equals(atom.getTextStyle(), REGULAR_STYLE)){
                temp.append(atom.getData());
                if(temp.length() > 5){
                    tgtAtom.setData(translator.trans(temp.toString()));
                    temp = new StringBuilder();
                }else {
                    // 不满足时要将内容置空
                    tgtAtom.setData("");
                }
                tgtAtom.setTagId(atom.getTagId());
                tgtAtom.setTagType(atom.getTagType());
                tgtAtom.setTextStyle(atom.getTextStyle());
                tgtAtom.setHidden(atom.isHidden());
                tgtAtom.setAtomId(atom.getAtomId());
                tgtSegmentAtoms.add(tgtAtom);
                // count用来记录每次文本atom的位置，循环结束后count的值就是最后一个文本atom的位置
                count = i;
                continue;
            }
            tgtAtom.setData(atom.getData());
            tgtAtom.setTagId(atom.getTagId());
            tgtAtom.setTagType(atom.getTagType());
            tgtAtom.setTextStyle(atom.getTextStyle());
            tgtAtom.setHidden(atom.isHidden());
            tgtAtom.setAtomId(atom.getAtomId());
            tgtSegmentAtoms.add(tgtAtom);
        }

        // temp还存在残余内容，要找到tgtAtoms中最后一个文本atom将其内容替换
        if(StringUtils.isNotEmpty(temp.toString()) && !temp.toString().matches("\\s+")){
            Atom tempAtom = tgtSegmentAtoms.get(count);
            tempAtom.setData(translator.trans(temp.toString()));
            tgtSegmentAtoms.set(count, tempAtom);
        }
        return tgtSegmentAtoms;
    }
}
