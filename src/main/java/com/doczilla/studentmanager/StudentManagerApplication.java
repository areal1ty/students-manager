package com.doczilla.studentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
        org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class,
        org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration.class
})
@EnableJdbcRepositories(basePackages = "com.doczilla.studentmanager.repository")
@EntityScan(basePackages = "com.doczilla.studentmanager.model")
public class StudentManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagerApplication.class, args);
    }

}
