package com.example.dell.zhihuknows2;

import android.graphics.Bitmap;

/**
 * Created by dell on 2016/6/12.
 */
public class FrameWork {

    private Bitmap touxiangheji;
    private String neirong;
    private String name;
    private String date;

    public Bitmap getTouxiangheji() {
        return touxiangheji;
    }

    public void setTouxiangheji(Bitmap touxiangheji) {
        this.touxiangheji = touxiangheji;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (((FrameWork)o).getNeirong().equals(neirong)){
            return true;
        }
        return super.equals(o);
    }
}
