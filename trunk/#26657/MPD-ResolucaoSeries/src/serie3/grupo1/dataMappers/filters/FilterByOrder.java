package serie3.grupo1.dataMappers.filters;

import exceptions.Serie3_FilterException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FilterByOrder implements IFilter{

    private final int _idVal;

    public FilterByOrder(int id) { _idVal = id; }

    public String getWhereClause() {
        return " where orderId = ?";
    }

    public void bindValues(PreparedStatement st) {
        try{
            st.setInt(1,_idVal);
        }
        catch(SQLException se){
            throw new Serie3_FilterException();
        }
    }

}
