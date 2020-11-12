//package com.epam.esm.configuration;
//
//import org.springframework.context.annotation.*;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import javax.sql.DataSource;
//
///**
// * The type Data configuration.
// */
//@Configuration
//@ComponentScan("com.epam.esm")
//public class DataConfiguration {
//
//    @Bean
//    @Profile("test")
//    public DataSource dataSource() {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        return builder
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("dump/epam_certificate.sql")
//                .addScript("dump/epam_tag.sql")
//                .addScript("dump/epam_gift_certificate_tag.sql")
//                .build();
//    }
//
//}
