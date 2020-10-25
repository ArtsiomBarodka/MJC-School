package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong("g.id"));
        giftCertificate.setName(rs.getString("g.name"));
        giftCertificate.setDescription(rs.getString("g.description"));
        giftCertificate.setPrice(rs.getDouble("g.price"));
        giftCertificate.setCreateDate(convertTimestampToString(rs.getTimestamp("g.date")));
        giftCertificate.setLastUpdateDate(convertTimestampToString(rs.getTimestamp("g.last_update_date")));
        giftCertificate.setDuration(rs.getInt("g.duration"));
        return giftCertificate;
    }

    private static String convertTimestampToString(Timestamp timestamp){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(DATE_FORMAT); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(timestamp);
    }
}