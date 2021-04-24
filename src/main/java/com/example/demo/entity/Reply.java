package com.example.demo.entity;

public class Reply {
    private int reply_id =0;
    private String reply_question = "";
    private String reply_answer = "";
   
    private int seller_id = 1;

    public int getReplyId(){
        return reply_id;
    }

    public void setReplyId(int reply_id){
        this.reply_id = reply_id;
    }

    public String getReplyQuestion(){
        return reply_question;
    }

    public void setReplyQuestion(String reply_question){
        this.reply_question = reply_question;
    }
     
    public String getReplyAnswer(){
        return reply_answer;
    }

    public void setReplyAnswer(String reply_answer){
        this.reply_answer = reply_answer;
    }
    
    public int getSellerId(){
        return seller_id;
    }

    public void setSellerId(int seller_id){
        this.seller_id = seller_id;
    }
}
