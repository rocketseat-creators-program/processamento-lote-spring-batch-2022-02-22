package com.rocketseat.springbatchperformance.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Notification(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("dept"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getInt("tracking"),
                rs.getDate("time")
                );
    }
}
