package com.example.demo.dao;

import com.example.demo.entity.Reply;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyDAO {
    public List<Reply> findAll();
   
    public Reply findOne(int seller_id);
}