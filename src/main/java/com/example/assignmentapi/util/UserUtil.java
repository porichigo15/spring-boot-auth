package com.example.assignmentapi.util;

import com.example.assignmentapi.model.User;
import com.example.assignmentapi.model.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class UserUtil {

    @Autowired
    private DataSource dataSource;

    public List<User> getUser(String email) {
        String sql = "SELECT * FROM USER WHERE EMAIL = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query(sql, new Object[]{ email }, new UserRowMapper());
    }
}
