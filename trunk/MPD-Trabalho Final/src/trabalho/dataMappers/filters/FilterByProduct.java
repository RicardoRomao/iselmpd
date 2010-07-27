package trabalho.dataMappers.filters;

import exceptions.FilterException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FilterByProduct implements IFilter{

    private final int _idVal;

    public FilterByProduct(int idVal) { _idVal = idVal; }

    public String getWhereClause() {
        return " where productId = ?";
    }

    public void bindValues(PreparedStatement st) {
        try{
            st.setInt(1, _idVal);
        }
        catch(SQLException se){
            throw new FilterException();
        }
    }
}
