package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLWhere extends SQLClause {
    //=================================================================
    List<SQLClause> items = new ArrayList();
    String logicaloperator = "AND";
    public void setLogicalOperator (String operator) { logicaloperator = operator; }
    public void addCondition (SQLCondition condition) { items.add(condition); }
    public void addConditionSet (SQLConditionSet cs) { items.add(cs); }
    public void addExpression (SQLExpression expression) { items.add(expression); }
    //=================================================================
    @Override
    public String getText () {
        StringBuilder ret = new StringBuilder();
        ret.append("WHERE (");
        boolean isfirst = true;        
        for (SQLClause item : items) {
            if (!isfirst) {
                ret.append(" ");
                ret.append(logicaloperator);
                ret.append(" ");
            }
            else isfirst = false;
            ret.append(item.getText());
        }        
        ret.append(")");
        return ret.toString();
    }
    //=======================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        for (SQLClause item : items)
            index = item.setParameters(st, index);        
        return index; 
    }
    //=======================================================================    
}
//***************************************************************************