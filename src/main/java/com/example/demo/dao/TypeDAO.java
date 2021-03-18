package com.example.demo.dao;

import com.example.demo.entity.Type;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import java.util.List;

@Repository
public interface TypeDAO {
    public int insert(Type type) throws SQLException;
    public List<Type> findAll();
    public List<Type> findSellerAll(int seller_id);
    public Type findOne(int type_id);
    public int update(Type type);
    public int delete(int type_id);
}
