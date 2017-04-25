package org.mse.moviewebapp.db;

import java.sql.PreparedStatement;

/**
 * Created by ferdi on 4/25/17.
 */
@FunctionalInterface
public interface BatchPreparedStatementConsumer {
    void accept(PreparedStatement ps, int i);
}
