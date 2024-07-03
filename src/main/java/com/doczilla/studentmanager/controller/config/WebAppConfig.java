package com.doczilla.studentmanager.controller.config;

import com.doczilla.studentmanager.repository.Repository;
import com.doczilla.studentmanager.repository.StudentRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class WebAppConfig extends WebMvcAutoConfiguration {

    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/student")
                .username("postgres")
                .password("postgres")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Bean(name = "applicationJdbcTemplate")
    public JdbcTemplate applicationDataConnection() {
        return new JdbcTemplate(dataSource());
    }

    @Bean(name = "applicationNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public Repository studentRepository() {
        return new StudentRepository(applicationDataConnection(), namedParameterJdbcTemplate(applicationDataConnection()));
    }

}