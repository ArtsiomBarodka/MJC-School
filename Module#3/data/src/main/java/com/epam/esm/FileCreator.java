package com.epam.esm;

import java.io.FileWriter;
import java.io.IOException;

public class FileCreator implements Creator {
    private static final String IS_NUMBER_REGEXP = "-?\\d+";

    @Override
    public void create() {
        String[] tags = Generator.createTag().split(Generator.ENTITY_DELIMITER);
        String[] certificates = Generator.createCertificate().split(Generator.ENTITY_DELIMITER);
        String[] certificateTags = Generator.createCertificateTag().split(Generator.ENTITY_DELIMITER);
        String[] users = Generator.createUser().split(Generator.ENTITY_DELIMITER);
        String[] orders = Generator.createOrder().split(Generator.ENTITY_DELIMITER);
        String[] certificateOrders = Generator.createCertificateOrder().split(Generator.ENTITY_DELIMITER);
        try (FileWriter writer = new FileWriter("insert.sql")) {
//            writer.append("delete from gift_certificate_tag; ")
//                    .append("delete from tag; ")
//                    .append("delete from certificate_order; ")
//                    .append("delete from certificate;")
//                    .append("delete from user_order; ")
//                    .append("delete from user; ");

            writer
                    .append("delete from certificate_order; ")
                    .append("delete from user_order; ")
                    .append("delete from user; ");

//            String[] tagFields = {"id", "name"};
//            for (String tag : tags) {
//                writer.append(createSQL(tag, "tag", tagFields, true));
//            }
//            writer.append("\n");
//
//            String[] certificateFields = {"id", "name", "description", "price", "duration"};
//            for (String entity : certificates) {
//                writer.append(createSQL(entity, "certificate", certificateFields, true));
//            }
//            writer.append("\n");
//
//            for (String entity : certificateTags) {
//                writer.append(createSQL(entity, "gift_certificate_tag", null, false));
//            }
//            writer.append("\n");


            String[] userFields = {"id", "name"};
            for (String entity : users) {
                writer.append(createSQL(entity, "user", userFields, true));
            }
            writer.append("\n");


            String[] orderFields = {"id","price","fk_user_id"};
            for (String entity : orders) {
                writer.append(createSQL(entity, "user_order", orderFields, true));
            }
            writer.append("\n");


            String[] certificateOrderFields = {"order_id", "certificate_id"};
            for (String entity : certificateOrders) {
                writer.append(createSQL(entity, "certificate_order", certificateOrderFields, true));
            }

            writer.flush();

        } catch (IOException e) {
            //do nothing
        }
    }

    private String createSQL(String entity, String table, String[] fields, boolean needFields) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ").append(table);
        if (needFields) {
            stringBuilder.append("(");
            for (int i = 0; i < fields.length; i++) {
                stringBuilder.append(fields[i]);
                if (i != fields.length - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(")");
        }

        stringBuilder.append(" VALUES (");

        String[] split = entity.split(Generator.FIELDS_DELIMITER);
        for (int i = 0; i < split.length; i++) {
            if(!isNumber(split[i])){
                stringBuilder.append("'").append(split[i]).append("'");
            } else {
                stringBuilder.append(split[i]);
            }

            if (i != split.length - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(");");

        return stringBuilder.toString();
    }

    private static boolean isNumber(String value){
        return value.matches(IS_NUMBER_REGEXP);
    }
}
