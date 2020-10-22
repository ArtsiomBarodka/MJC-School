package dao.impl;

import entity.GiftCertificate;
import entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = null;
        ArrayList<Tag> tags = new ArrayList<>();
        Tag tag;

        while (rs.next()){
            if(giftCertificate == null){
                giftCertificate = new GiftCertificate();
                giftCertificate.setId(rs.getLong("gift_certificate.id"));
                giftCertificate.setName(rs.getString("gift_certificate.name"));
                giftCertificate.setDescription(rs.getString("gift_certificate.description"));
                giftCertificate.setPrice(rs.getDouble("gift_certificate.price"));
                giftCertificate.setCreateDate(convertTimestampToString(rs.getTimestamp("gift_certificate_create.date")));
                giftCertificate.setLastUpdateDate(convertTimestampToString(rs.getTimestamp("gift_certificate.last_update_date")));
                giftCertificate.setDuration(rs.getInt("gift_certificate.duration"));
            }

            tag = new Tag();
            tag.setId(rs.getLong("tag.id"));
            tag.setName(rs.getString("tag.name"));
            tags.add(tag);
        }

        if(giftCertificate != null){
            giftCertificate.setTags(tags);
        }

        return giftCertificate;
    }

    private static String convertTimestampToString(Timestamp timestamp){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(DATE_FORMAT); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(timestamp);
    }
}
