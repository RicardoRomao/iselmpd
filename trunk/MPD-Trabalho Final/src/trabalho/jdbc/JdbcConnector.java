package trabalho.jdbc;

import exceptions.JdbcConnectorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

public class JdbcConnector {

    private final DataSource _ds;
    private Connection _conn = null;

    public JdbcConnector(DataSource ds) {
        _ds = ds;
    }

    public <DType> DType exec(JdbcAction<DType> action) {
        PreparedStatement stm = null;
        Connection conn = null;
        try {
            try {
                conn = getConnection();
                stm = action.getStatement(conn);
                return action.exec(stm);
            }finally {
                if(stm != null) stm.close();
                if(conn != null) releaseConnection(conn);
            }
        }catch(SQLException e) {
            e.printStackTrace();
            throw new JdbcConnectorException();
        }
    }

    public Connection getConnection() throws SQLException {
        if (_conn != null) return _conn;
        return _ds.getConnection();
    }

    public void releaseConnection(Connection conn) throws SQLException {
        if (_conn != null) return;
        conn.close();
    }

    public void beginTransaction() throws SQLException {
        if (_conn != null) throw new IllegalStateException();
        _conn = _ds.getConnection();
        _conn.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        if (_conn == null) throw new IllegalStateException();
        _conn.commit();
        _conn.close();
        _conn = null;
    }

    public void rollbackTransaction() throws SQLException {
        if (_conn == null) throw new IllegalStateException();
        _conn.rollback();
        _conn.close();
        _conn = null;
    }
}
