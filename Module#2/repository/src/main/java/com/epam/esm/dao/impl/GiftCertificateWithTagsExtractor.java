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
import java.util.*;

public class GiftCertificateWithTagsExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, GiftCertificate> map = new HashMap<>();
        GiftCertificate giftCertificate;

        while (rs.next()){
            long id = rs.getLong("g.id");
            giftCertificate = map.get(id);
            if(giftCertificate == null){
                giftCertificate = new GiftCertificate();
                giftCertificate.setId(id);
                giftCertificate.setName(rs.getString("g.name"));
                giftCertificate.setDescription(rs.getString("g.description"));
                giftCertificate.setPrice(rs.getDouble("g.price"));
                giftCertificate.setCreateDate(convertTimestampToString(rs.getTimestamp("g.create_date")));
                giftCertificate.setLastUpdateDate(convertTimestampToString(rs.getTimestamp("g.last_update_date")));
                giftCertificate.setDuration(rs.getInt("g.duration"));
                map.put(id, giftCertificate);
            }

            long tagId = rs.getLong("t.id");
            if(tagId > 0){
                Tag tag = new Tag();
                tag.setId(tagId);
                tag.setName(rs.getString("t.name"));
                giftCertificate.addTag(tag);
            }
        }

        return new ArrayList<>(map.values());
    }

    private static String convertTimestampToString(Timestamp timestamp){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(DATE_FORMAT); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(timestamp);
    }
}