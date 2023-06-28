package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLLeftJoin extends SQLClause {
    //=================================================================
    public SQLLeftJoin(String table) { this.table = table; }
    //=================================================================
    String table = null;
    List<SQLClause> items = new ArrayList();
    //=================================================================
    @Override
    public String getText() {
        StringBuilder ret = new StringBuilder();
        ret.append("LEFT JOIN ");
        ret.append(table);
        ret.append(" ON ");
        boolean isfirst = true;        
        for (SQLClause item : items) {
            if (!isfirst) ret.append(" AND ");
            else isfirst = false;
            ret.append(item.getText());
        }
        return ret.toString();
    }
    //=================================================================
    @Override
    public int setParameters(PreparedStatement st, int index) throws SQLException {
        for (SQLClause item : items)
            index = item.setParameters(st, index);        
        return index; 
    }
    //=================================================================
    public void addCondition (SQLCondition condition) { items.add(condition); }
    public void addConditionSet (SQLConditionSet cs) { items.add(cs); }
    //=================================================================
}
//***************************************************************************
