package com.example.demo.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Reply; 

@Repository
public class ReplyDAODB implements ReplyDAO {
@Autowired
 private DataSource dataSource;
@Autowired
 JdbcTemplate jdbcTemplate;

 public Reply findOne(int seller_id ) {
  
  try {
  Connection connection = dataSource.getConnection();
  
  }
  catch (Exception e){
    System.out.println("Error in findOne:"+e);
  }
 Reply areply = jdbcTemplate.queryForObject( 
   "select * from reply group by seller_id = ?", 
   new ReplyMapper(), seller_id);
   
 return areply;
}


public Reply findOneSeller(int reply_id ) {
  
  try {
  Connection connection = dataSource.getConnection();
  
  }
  catch (Exception e){
    System.out.println("Error in findOne:"+e);
  }
 Reply areply = jdbcTemplate.queryForObject( 
   "select * from reply where reply_id = ?", 
   new ReplyMapper(), reply_id);
 return areply;
}

public List<Reply> findAll() {
   return this.jdbcTemplate.query( "select * from reply", 
    new ReplyMapper());
}

public List<Reply> findAllQuestion(int reply_id) {
  return this.jdbcTemplate.query( "select * from reply where reply_id=?", 
   new ReplyMapper(), reply_id);
}
public List<Reply> findAllQuestionSeller(int reply_id) {
  return this.jdbcTemplate.query( "select * from reply where reply_id=?", 
   new ReplyMapper(), reply_id);
}

public int insert(Reply Reply) throws SQLException{
  return jdbcTemplate.update(
    "insert into Reply (reply_id, reply_question, reply_answer,seller_id) values(?,?,?,?)",
    Reply.getReplyId(),Reply.getReplyQuestion(), Reply.getReplyAnswer(),Reply.getSellerId());
 }

public int update(Reply Reply) {
  return jdbcTemplate.update(
    "update Reply set reply_question=?, reply_answer=?,seller_id=? where reply_id =?",
    Reply.getReplyQuestion(), Reply.getReplyAnswer(), Reply.getSellerId(),Reply.getReplyId());
 }

private static final class ReplyMapper implements RowMapper<Reply> {

    public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reply Reply = new Reply();
        Reply.setReplyId(rs.getInt("reply_id"));
        Reply.setReplyQuestion(rs.getString("reply_question"));
        Reply.setReplyAnswer(rs.getString("reply_answer"));
        Reply.setSellerId(rs.getInt("seller_id"));
        
       
        return Reply;
    }
}

}
