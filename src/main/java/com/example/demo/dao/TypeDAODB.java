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
import com.example.demo.entity.Type;

@Repository
public class TypeDAODB implements TypeDAO {

    @Autowired
    private DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;

    // jdbcTemplate
    public Type findOne(int type_id) {
        try {
            Connection connection = dataSource.getConnection();
        } catch (Exception e) {
            System.out.println("Error in findOne:" + e);
        }
        Type aType = jdbcTemplate.queryForObject(
                "select type_id,type_name,seller_id from Type where type_id = ?",
                new typeMapper(), type_id);
        return aType;
    }

    public Type findTypeId(String type_name) {
        try {
            Connection connection = dataSource.getConnection();
        } catch (Exception e) {
            System.out.println("Error in findOne:" + e);
        }
        Type aType = jdbcTemplate.queryForObject(
                "select type_id,type_name,seller_id from Type where type_name = ?",
                new typeMapper(), type_name);
        return aType;
    }

/*Hader section顯示---------------------------------------------------------------------------------------------*/
    public List<Type> findAll() {
        return this.jdbcTemplate.query(
                "select type_id,type_name,seller_id from Type",
                new typeMapper());
    }
/*---------------------------------------------------------------------------------------------*/
    public List<Type> findSellerAll(int seller_id) {
        return this.jdbcTemplate.query(
            "select type_id,type_name,seller_id from Type where seller_id = ?",
            new typeMapper(), seller_id);
    }

    private static final class typeMapper implements RowMapper<Type> {

        public Type mapRow(ResultSet rs, int rowNum) throws SQLException {
            Type type = new Type();
            type.setTypeId(rs.getInt("type_id"));
            type.setTypeName(rs.getString("type_name"));
            type.setSellerId(rs.getInt("seller_id"));
            return type;
        }
    }

    public int insert(Type type) throws SQLException {
        return jdbcTemplate.update(
                "insert into type (type_id,type_name,seller_id) values(?,?,?,?,?,?,?)",
                type.getTypeId(), type.getTypeName(), type.getSellerId());
    }

    public int update(Type type) {
        return jdbcTemplate.update(
                "update type set type_name=?, seller_id=? where type_id =?",
                type.getTypeId(), type.getTypeName(), type.getSellerId());
    }

    public int delete(int type_id) {
        return jdbcTemplate.update("delete from type where type_id =?", type_id);
    }

}
