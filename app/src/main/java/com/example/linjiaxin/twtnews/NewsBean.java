package com.example.linjiaxin.twtnews;

/**
 * Created by linjiaxin on 2017/11/9.
 */

public class NewsBean {
    private String index;
    private String subject;
    private String pic;
    private String visitcount;
    private String comments;
    private String summary;

    public NewsBean(String index, String subject, String pic, String visitcount, String comments,String summary)
    {
        this.index = index;
        this.subject = subject;
        this.pic = pic;
        this.visitcount = " 点击量:" + visitcount;
        this.comments = " 回复量:" + comments;
        this.summary = summary;
    }

    public String getIndex(){ return index; }
    public String getSubject(){ return subject; }
    public String getPic(){ return pic; }
    public String getVisitcount(){ return visitcount; }
    public String getComments(){ return comments; }
    public String getSummary(){ return summary; }
}
