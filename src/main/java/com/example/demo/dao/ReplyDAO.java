package com.example.demo.dao;

import com.example.demo.entity.Reply;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface ReplyDAO {
    public List<Reply> findAll();
    public List<Reply> findAllQuestion(int reply_id);
    public Reply findOne(int seller_id);
    public int insert(Reply Reply) throws SQLException;
}
