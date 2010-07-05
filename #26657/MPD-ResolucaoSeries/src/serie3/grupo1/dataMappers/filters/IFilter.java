package serie3.grupo1.dataMappers.filters;

import java.sql.PreparedStatement;

public interface IFilter {
    String getWhereClause();
    void bindValues(PreparedStatement st);
}
