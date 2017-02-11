package com.example.dell.zhihuknows2;

import android.graphics.Bitmap;

/**
 * Created by dell on 2016/6/16.
 */
public class Paper {
    private Bitmap toux;

    private String zhaiyao;

    private String mtitle;

    private String questionId;

    private String answerId;

    private String touxurl;

    private boolean checkboxvisable = false;

    private boolean isselected;

    private boolean isloved = false;

    public boolean isloved() {
        return isloved;
    }

    public void setIsloved(boolean isloved) {
        this.isloved = isloved;
    }

    public boolean isselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public boolean isCheckboxvisable() {
        return checkboxvisable;
    }

    public void setCheckboxvisable(boolean checkboxvisable) {
        this.checkboxvisable = checkboxvisable;
    }

    public String getTouxurl() {
        return touxurl;
    }

    public void setTouxurl(String touxurl) {
        this.touxurl = touxurl;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public Bitmap getToux() {
        return toux;
    }

    public void setToux(Bitmap toux) {
        this.toux = toux;
    }

    public String getZhaiyao() {
        return zhaiyao;
    }

    public void setZhaiyao(String zhaiyao) {
        this.zhaiyao = zhaiyao;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    @Override
    public boolean equals(Object o) {
        if (((Paper)o).getAnswerId().equals(answerId)){
            return true;
        }
        return super.equals(o);
    }
}
