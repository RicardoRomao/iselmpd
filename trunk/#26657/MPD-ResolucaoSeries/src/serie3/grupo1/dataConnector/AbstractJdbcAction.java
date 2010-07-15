package serie3.grupo1.dataConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractJdbcAction<R> implements JdbcAction<R> {

    private final String _sql;
    private final boolean _getKeys;

    public AbstractJdbcAction(String sql) {_sql = sql; _getKeys = false;}
    public AbstractJdbcAction(String sql, int i) {_sql = sql; _getKeys = i == Statement.RETURN_GENERATED_KEYS;}

    @Override public PreparedStatement getStatement(Connection conn) throws SQLException {
        if(_getKeys) return conn.prepareStatement(_sql, Statement.RETURN_GENERATED_KEYS);
        return conn.prepareStatement(_sql);
    }
}
