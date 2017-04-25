package org.mse.moviewebapp.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * Created by ferdi on 4/25/17.
 */
@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @EventListener
    public void initializeDatabase(ApplicationReadyEvent contextRefreshedEvent) {
        jdbcTemplate.execute("CREATE TABLE favorites(title VARCHAR(255), id VARCHAR(255));");
    }

}
