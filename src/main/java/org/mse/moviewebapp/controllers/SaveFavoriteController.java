package org.mse.moviewebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Ferdinand.Szekeresch on 25.04.2017.
 */

@Controller
public class SaveFavoriteController {

	@Autowired
	JdbcTemplate template;

	@RequestMapping("/saveFavorite")
	public void saveFavorite(@RequestParam(name = "title", required = true) String title, @RequestParam(name = "id", required = true) String id) {
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
