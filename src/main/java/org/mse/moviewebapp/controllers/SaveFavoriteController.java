package org.mse.moviewebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ferdinand.Szekeresch on 25.04.2017.
 */

@Controller
public class SaveFavoriteController {

    @Autowired
    JdbcTemplate template;

    @RequestMapping("/saveFavorite")
    public @ResponseBody
    String saveFavorite(@RequestParam(name = "title", required = true) String title, @RequestParam(name = "id", required = true) String id) {
        String checkForExistingIdStmt = "SELECT COUNT(*) FROM FAVORITES WHERE ID=?";
        final Integer existingId = template.queryForObject(checkForExistingIdStmt, Integer.class, id);

        if (existingId.equals(0)) {
            doSaveFavorite(title, id);
        }

        final String extractFavoritesStmt = "SELECT id, title FROM FAVORITES;";
        List<String> list = template.query(extractFavoritesStmt, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1) + ": " + resultSet.getString(2);
            }
        });

        return list.stream().collect(Collectors.joining("<br/>"));
    }

    private void doSaveFavorite(@RequestParam(name = "title", required = true) String title,
        @RequestParam(name = "id", required = true) String id) {
        String insertStmt = "INSERT INTO favorites (title, id) VALUES (?,?)";
        template.batchUpdate(insertStmt, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, id);
            }

            @Override
            public int getBatchSize() {
                return 1;
            }
        });
    }

}
