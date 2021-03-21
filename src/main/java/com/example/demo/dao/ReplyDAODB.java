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
   /*
    return this.jdbcTemplate.queryForObject( 
     "select buyer_id,buyer_name, buyer_phone,buyer_mail,buyer_address, pickpoint,pickmoney from Buyer where buyer_id = ?", 
     new BuyerMapper(),buyer_id); */
}
public List<Reply> findAll() {
   return this.jdbcTemplate.query( "select * from reply", 
    new ReplyMapper());
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
