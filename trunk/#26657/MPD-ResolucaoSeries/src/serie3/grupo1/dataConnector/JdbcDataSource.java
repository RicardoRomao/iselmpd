package serie3.grupo1.dataConnector;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.sql.DataSource;

public class JdbcDataSource {

    static final String SERVER_NAME = "Gummie\\SQLSERVER";
    static final String DB_NAME = "Northwind";
    static final String UID = "user_mpd";
    static final String PWD = "mpd";

    private static final Object _monitor = new Object();
    private static SQLServerDataSource _ds;

    public static DataSource getDataSource() {
        synchronized (_monitor) {
            if (_ds == null) {
                _ds = new SQLServerDataSource();
                _ds.setServerName(SERVER_NAME);
                _ds.setDatabaseName(DB_NAME);
                _ds.setUser(UID);
                _ds.setPassword(PWD);
            }
        }
        return _ds;

    }
}
