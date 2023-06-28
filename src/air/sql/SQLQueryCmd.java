package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLQueryCmd extends SQLClause
{
    //=======================================================================
    List<SQLClause> clauses = new ArrayList();
    //List<SQLClauseBase> clauses = new ArrayList();
    //=======================================================================
    public void addClause (SQLClause clause){clauses.add(clause);}
    //=======================================================================
    @Override
    public String getText () {
        StringBuilder ret = new StringBuilder();
        boolean isfirst = true;
        for (SQLClause clause : clauses)
        {
            if (!isfirst) ret.append("\n");
            else isfirst = false;
            ret.append(clause.getText());
        }
        return ret.toString();
    }
    //=======================================================================
    /**
     * Fills the parameters
     * @param st
     * @param index index of parameter.
     * @return
     * @throws SQLException 
     */
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        for (SQLClause clause : clauses)
            index = clause.setParameters(st, index);
        return index;
    }
    //=======================================================================
}
//***************************************************************************
