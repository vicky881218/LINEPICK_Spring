package com.example.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Record;

@Repository
public class RecordDAODB implements RecordDAO{
    @Autowired
 private DataSource dataSource;
@Autowired
 JdbcTemplate jdbcTemplate;

//  public List<Record> findJoinCartAllProduct(int orderlist_id) {
//     return this.jdbcTemplate.query( "select * from cart join product on cart.product_id=product.product_id and buyer_id=?", 
//      new CartInfoMapper(),orderlist_id);
//   }

}
