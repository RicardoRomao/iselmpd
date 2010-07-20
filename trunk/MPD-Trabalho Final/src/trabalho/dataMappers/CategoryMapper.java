package trabalho.dataMappers;

import exceptions.Serie3_DataMapperException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import trabalho.jdbc.JdbcConnector;
import trabalho.domainObjects.Category;

public class CategoryMapper extends AbstractDataMapper<Integer, Category> {

    public CategoryMapper(JdbcConnector connector) {
        super(connector);
    }

    @Override
    String doGetAllStatement() {
        return "select categoryId, categoryName, description from Categories";
    }

    @Override
    String doGetFindStatement() {
        return doGetAllStatement() + " where categoryId = ?";
    }

    @Override
    void doBindFindStatement(PreparedStatement st, Integer key) {
        try {
            st.setInt(1, key);
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    boolean doInsertRequiresUpdate(Category c) { return false; }

    @Override
    String doGetInsertStatement(Category c) {
        return "insert into Categories (CategoryName, Description)" +
                " values (?,?)";
    }

    @Override
    void doBindInsertStatement(PreparedStatement st, Category c){
        try{
            st.setString(1, c.getCategoryName());
            st.setString(2, c.getDescription());
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetUpdateStatement() {
        return "update Categories set CategoryName = ?, Description = ? " +
                "where CategoryID = ?";
    }

    @Override
    void doBindUpdateStatement(PreparedStatement st, Category c) {
        try {
            st.setString(1, c.getCategoryName());
            st.setString(2, c.getDescription());
            st.setInt(3, c.getId());
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    String doGetDeleteStatement() {
        return "delete from Categories where CategoryID = ?";
    }

    @Override
    void doBindDeleteStatement(PreparedStatement st, Integer key){
        try{
            st.setInt(1, key);
        } catch (SQLException e) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    Integer doGetId(ResultSet rs) {
        try {
            return rs.getInt(1);
        } catch (SQLException se) {
            throw new Serie3_DataMapperException();
        }
    }

    @Override
    Category doLoad(ResultSet rs) {
        try {
            Category c = new Category(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3)
            );
            return c;
        } catch (SQLException se) {
            throw new Serie3_DataMapperException();
        }
    }

}
