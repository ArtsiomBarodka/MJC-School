package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type List gift certificates with tags extractor.
 */
public class ListGiftCertificatesWithTagsExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException {
        Map<Long, GiftCertificate> map = new LinkedHashMap<>();
        GiftCertificate giftCertificate;

        while (rs.next()) {
            long id = rs.getLong("certificate.id");
            giftCertificate = map.get(id);
            if (giftCertificate == null) {
                giftCertificate = new GiftCertificate();
                giftCertificate.setId(id);
                giftCertificate.setName(rs.getString("certificate.name"));
                giftCertificate.setDescription(rs.getString("certificate.description"));
                giftCertificate.setPrice(rs.getDouble("certificate.price"));
                giftCertificate.setCreateDate(convertTimestampToString(rs.getTimestamp("certificate.create_date")));
                giftCertificate.setLastUpdateDate(convertTimestampToString(rs.getTimestamp("certificate.last_update_date")));
                giftCertificate.setDuration(rs.getInt("certificate.duration"));
                map.put(id, giftCertificate);
            }

            long tagId = rs.getLong("tag.id");
            if (tagId > 0) {
                Tag tag = new Tag();
                tag.setId(tagId);
                tag.setName(rs.getString("tag.name"));
                giftCertificate.addTag(tag);
            }
        }

        return new ArrayList<>(map.values());
    }

    private static String convertTimestampToString(Timestamp timestamp) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(DATE_FORMAT); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(timestamp);
    }
}
