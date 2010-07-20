package northwind;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Before;
import trabalho.jdbc.JdbcConnector;
import trabalho.jdbc.JdbcDataSource;
import trabalho.unitOfWork.UnitOfWork;

public class NorthwindTest {

    private TestDataSource ds;
    
    private static class ConnectionProxyInvocationHandler implements InvocationHandler{

        private final Connection _conn;
        private final Method _close;
        
        public ConnectionProxyInvocationHandler(Connection conn) throws SecurityException, NoSuchMethodException {
            _conn = conn;
            _close = Connection.class.getMethod("close", new Class[0]);
        }
        
        @Override
        public Object invoke(Object arg0, Method arg1, Object[] arg2)
                throws Throwable {
            // ignore close
            if(arg1.equals(_close)) {                
                return null;
            }
            return arg1.invoke(_conn, arg2);
        }
    }
    private static class TestDataSource implements DataSource{
        private final Connection _realConn; 
        private final Connection _proxyConn;
        
        public TestDataSource(Connection conn) throws IllegalArgumentException, SecurityException, NoSuchMethodException, SQLException {
            _realConn = conn;
            _realConn.setAutoCommit(false);
            _proxyConn = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
                    new Class[] {Connection.class},
                    new ConnectionProxyInvocationHandler(conn));
        }
        
        public void rollbackAndCloseRealConnection() throws SQLException {
            _realConn.rollback();
            _realConn.close();
        }        

        @Override
        public Connection getConnection() throws SQLException {
            return _proxyConn;
        }

        @Override
        public Connection getConnection(String arg0, String arg1)
                throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setLogWriter(PrintWriter arg0) throws SQLException {
            throw new UnsupportedOperationException();            
        }

        @Override
        public void setLoginTimeout(int arg0) throws SQLException {
            throw new UnsupportedOperationException();            
        }

        @Override
        public boolean isWrapperFor(Class<?> arg0) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T unwrap(Class<T> arg0) throws SQLException {
            throw new UnsupportedOperationException();
        }
        
    }
        
    @Before
    public void setup() throws IOException, SQLException, IllegalArgumentException, SecurityException, NoSuchMethodException {
        SQLServerDataSource ods = (SQLServerDataSource) JdbcDataSource.getDataSource();
        
        ds = new TestDataSource(ods.getConnection());
        JdbcConnector connector = new JdbcConnector(ds);
        
        UnitOfWork.setCurrent(new UnitOfWork(connector));
    }
    
    @After
    public void teardown() throws SQLException {        
        //ds.rollbackAndCloseRealConnection();
        
    }

}