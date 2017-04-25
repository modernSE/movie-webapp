package org.mse.moviewebapp.db;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by ferdi on 4/25/17.
 */
@Component
public class AwesomeJdbcTemplate extends JdbcTemplate {

    public int[] batchUpdate(String sql, BatchPreparedStatementConsumer c, int batchSize) {
        BatchPreparedStatementSetter bpss = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                c.accept(preparedStatement, i);
            }

            @Override
            public int getBatchSize() {
                return batchSize;
            }
        };
        return batchUpdate(sql, bpss);
    }
}
