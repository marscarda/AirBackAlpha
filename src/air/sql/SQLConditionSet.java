package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLConditionSet extends SQLClause {
    //=======================================================================
    List<SQLClause> conditions = new ArrayList();
    String logop = "AND";
    public void setLogicalOperator (String operator) { logop = operator; }
    public void addCondition (SQLCondition condition) { conditions.add(condition); }
    public void addExpression (SQLExpression expression) { conditions.add(expression); }
    //=======================================================================
    @Override
    public String getText () {
        StringBuilder ret = new StringBuilder();
        ret.append("(");
        boolean isfirst = true;        
        for (SQLClause condition : conditions) {
            if (!isfirst) {
                ret.append(" ");
                ret.append(logop);
                ret.append(" ");
            }
            else isfirst = false;
            ret.append(condition.getText());
        }        
        ret.append(")");
        return ret.toString();
    }
    //=======================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        for (SQLClause condition : conditions)
            index = condition.setParameters(st, index);        
        return index; 
    }
    //=======================================================================
}
//***************************************************************************