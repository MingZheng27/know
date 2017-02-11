package com.example.dell.zhihuknows2;

import android.graphics.Bitmap;

/**
 * Created by dell on 2016/5/5.
 */
public class Users {

    private Bitmap touxiang;
    private String usersname;
    private String touxiangurl;
    private String userhash;
    private String signnature;
    private Integer answer;
    private Integer question;
    private Integer post;
    private Integer agree;
    private Integer fav;
    private Integer thx;
    private Integer follower;
    private Integer followee;

    public String getUserhash() {
        return userhash;
    }

    public void setUserhash(String userhash) {
        this.userhash = userhash;
    }

    public Bitmap getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(Bitmap touxiang) {
        this.touxiang = touxiang;
    }

    public String getUsersname() {
        return usersname;
    }

    public void setUsersname(String usersname) {
        this.usersname = usersname;
    }

    public String getTouxiangurl() {
        return touxiangurl;
    }

    public void setTouxiangurl(String touxiangurl) {
        this.touxiangurl = touxiangurl;
    }

    public String getSignnature() {
        return signnature;
    }

    public void setSignnature(String signnature) {
        this.signnature = signnature;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public Integer getAgree() {
        return agree;
    }

    public void setAgree(Integer agree) {
        this.agree = agree;
    }

    public Integer getFav() {
        return fav;
    }

    public void setFav(Integer fav) {
        this.fav = fav;
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public Integer getThx() {
        return thx;
    }

    public void setThx(Integer thx) {
        this.thx = thx;
    }

    public Integer getFollowee() {
        return followee;
    }

    public void setFollowee(Integer followee) {
        this.followee = followee;
    }
}
