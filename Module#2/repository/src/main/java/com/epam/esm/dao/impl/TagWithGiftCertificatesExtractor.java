package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TagWithGiftCertificatesExtractor implements ResultSetExtractor<Tag> {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

    @Override
    public Tag extractData(ResultSet rs) throws SQLException, DataAccessException {
        Tag tag = null;

        while (rs.next()) {
            if (tag == null) {
                tag = new Tag();
                tag.setId(rs.getLong("t.id"));
                tag.setName(rs.getString("t.name"));
            }

            long giftCertificateId = rs.getLong("g.id");
            if (giftCertificateId > 0) {
                GiftCertificate giftCertificate = new GiftCertificate();
                giftCertificate.setId(giftCertificateId);
                giftCertificate.setName(rs.getString("g.name"));
                giftCertificate.setDescription(rs.getString("g.description"));
                giftCertificate.setPrice(rs.getDouble("g.price"));
                giftCertificate.setCreateDate(convertTimestampToString(rs.getTimestamp("g.create_date")));
                giftCertificate.setLastUpdateDate(convertTimestampToString(rs.getTimestamp("g.last_update_date")));
                giftCertificate.setDuration(rs.getInt("g.duration"));
                tag.addGiftCertificate(giftCertificate);
            }
        }

        return tag;
    }

    private static String convertTimestampToString(Timestamp timestamp) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(DATE_FORMAT); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(timestamp);
    }
}
