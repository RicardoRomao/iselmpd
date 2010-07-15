package serie3.grupo1.dataConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface JdbcAction<DType> {
    PreparedStatement getStatement(Connection conn) throws SQLException;
    DType exec(PreparedStatement st) throws SQLException;
}
