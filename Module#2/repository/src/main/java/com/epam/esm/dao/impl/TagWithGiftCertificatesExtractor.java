package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * The type Tag with gift certificates extractor.
 */
public class TagWithGiftCertificatesExtractor implements ResultSetExtractor<Tag> {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

    @Override
    public Tag extractData(ResultSet rs) throws SQLException {
        Tag tag = null;

        while (rs.next()) {
            if (tag == null) {
                tag = new Tag();
                tag.setId(rs.getLong("tag.id"));
                tag.setName(rs.getString("tag.name"));
            }

            long giftCertificateId = rs.getLong("certificate.id");
            if (giftCertificateId > 0) {
                GiftCertificate giftCertificate = new GiftCertificate();
                giftCertificate.setId(giftCertificateId);
                giftCertificate.setName(rs.getString("certificate.name"));
                giftCertificate.setDescription(rs.getString("certificate.description"));
                giftCertificate.setPrice(rs.getDouble("certificate.price"));
                giftCertificate.setCreateDate(convertTimestampToString(rs.getTimestamp("certificate.create_date")));
                giftCertificate.setLastUpdateDate(convertTimestampToString(rs.getTimestamp("certificate.last_update_date")));
                giftCertificate.setDuration(rs.getInt("certificate.duration"));
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
