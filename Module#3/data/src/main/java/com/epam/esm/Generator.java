package com.epam.esm;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.epam.esm.DataValues.*;

public final class Generator {
    public static final String FIELDS_DELIMITER = "-";
    public static final String ENTITY_DELIMITER = "#";

    private static String createCertificateName() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ADJECTIVES_2.length; i++) {
            for (int j = 0; j < ADJECTIVES_1.length; j++) {
                for (int k = 0; k < VERBS_2.length; k++) {
                    for (int l = 0; l < VERBS_3.length; l++) {
                        stringBuilder.append("The")
                                .append(ADJECTIVES_2[i])
                                .append(" ")
                                .append(ADJECTIVES_2[j])
                                .append(" ")
                                .append(VERBS_2[k])
                                .append(" ")
                                .append("of")
                                .append(" ")
                                .append(VERBS_3[l])
                                .append(FIELDS_DELIMITER);
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private static String createTagName() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ADJECTIVES_2.length; i++) {
            for (int j = 0; j < ADJECTIVES_1.length; j++) {
                for (int k = 0; k < VERBS_1.length; k++) {
                    stringBuilder.append(ADJECTIVES_2[i])
                            .append(ADJECTIVES_1[j])
                            .append(VERBS_1[k])
                            .append(FIELDS_DELIMITER);
                }
            }
        }
        return stringBuilder.toString();
    }

    private static String createUserName() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ADJECTIVES_2.length; i++) {
            for (int j = 0; j < NAMES.length; j++) {
                stringBuilder.append(ADJECTIVES_2[i])
                        .append(ADJECTIVES_2[i])
                        .append(VERBS_1[j])
                        .append(FIELDS_DELIMITER);
            }
        }
        return stringBuilder.toString();
    }

    public static String createTag() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] names = createTagName().split(FIELDS_DELIMITER);
        for (int i = 0; i < names.length; i++) {
            stringBuilder
                    .append(i+1)
                    .append(FIELDS_DELIMITER)
                    .append(names[i])
                    .append(ENTITY_DELIMITER);
        }
        return stringBuilder.toString();
    }

    public static String createCertificate() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] names = createCertificateName().split(FIELDS_DELIMITER);
        for (int i = 0; i < names.length; i++) {
            stringBuilder
                    .append(i + 1)
                    .append(FIELDS_DELIMITER)
                    .append(names[i])
                    .append(FIELDS_DELIMITER)
                    .append(DESCRIPTION[generateNumber(0, DESCRIPTION.length - 1)])
                    .append(FIELDS_DELIMITER)
                    .append(generateNumber(0, PRICE_MAX))
                    .append(FIELDS_DELIMITER)
                    .append(generateNumber(DURATION_MIN, DURATION_MAX))
                    .append(ENTITY_DELIMITER);
        }
        return stringBuilder.toString();
    }

    public static String createCertificateTag() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] certificates = createCertificate().split(ENTITY_DELIMITER);
        String[] tags = createTag().split(ENTITY_DELIMITER);
        for (int i = 0; i < certificates.length; i++) {
            int countTags = generateNumber(TAGS_MIN, TAGS_MAX);
            Set<Integer> uniqueId = new HashSet<>();
            for (int j = 0; j < countTags; j++) {
                uniqueId.add(generateNumber(1, tags.length));
            }
            for (Integer tagId : uniqueId) {
                stringBuilder
                        .append(i + 1)
                        .append(FIELDS_DELIMITER)
                        .append(tagId)
                        .append(ENTITY_DELIMITER);
            }
        }
        return stringBuilder.toString();
    }

    public static String createUser() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] names = createUserName().split(FIELDS_DELIMITER);
        for (int i = 0; i < names.length; i++) {
            stringBuilder
                    .append(i + 1)
                    .append(FIELDS_DELIMITER)
                    .append(names[i])
                    .append(ENTITY_DELIMITER);
        }
        return stringBuilder.toString();
    }

    public static String createOrder() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] names = createUser().split(ENTITY_DELIMITER);
        int id = 0;
        for (int i = 0; i < names.length; i++) {
            int countOrders = generateNumber(ORDERS_MIN, ORDERS_MAX);
            for (int j = 0; j < countOrders; j++) {
                stringBuilder
                        .append(++id)
                        .append(FIELDS_DELIMITER)
                        .append(generateNumber(PRICE_SUM_MIN, PRICE_SUM_MAX))
                        .append(FIELDS_DELIMITER)
                        .append(i + 1)
                        .append(ENTITY_DELIMITER);
            }
        }
        return stringBuilder.toString();
    }

    public static String createCertificateOrder() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] certificates = createCertificate().split(ENTITY_DELIMITER);
        String[] orders = createOrder().split(ENTITY_DELIMITER);
        for (int i = 0; i < orders.length; i++) {
            int countTags = generateNumber(CERTIFICATES_MIN, CERTIFICATES_MAX);
            Set<Integer> uniqueId = new HashSet<>();
            for (int j = 0; j < countTags; j++) {
                uniqueId.add(generateNumber(1, certificates.length));
            }
            for (Integer certificateId : uniqueId) {
                stringBuilder
                        .append(i + 1)
                        .append(FIELDS_DELIMITER)
                        .append(certificateId)
                        .append(ENTITY_DELIMITER);
            }
        }
        return stringBuilder.toString();
    }


    private static int generateNumber(int low, int high) {
        Random r = new Random();
        return (r.nextInt(high - low) + low);
    }
}
