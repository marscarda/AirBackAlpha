package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLUpdate extends SQLClause
{
    //========================================================================
    public SQLUpdate (String table) { tab = table; }
    //========================================================================
    String tab;
    List<SQLUpdateSetColumn> sets = new ArrayList();
    //========================================================================
    public void addSetColumn (String column) {
        SQLUpdateSetColumn set = new SQLUpdateSetColumn(column);
        sets.add(set);
    }
    //========================================================================
    public void addSetColumn (String column, int value) {
        SQLUpdateSetColumn set = new SQLUpdateSetColumn(column, value);
        sets.add(set);
    }
    //========================================================================
    public void addSetColumn (String column, long value) {
        SQLUpdateSetColumn set = new SQLUpdateSetColumn(column, value);
        sets.add(set);
    }
    //========================================================================
    public void addSetColumn (String column, float value) {
        SQLUpdateSetColumn set = new SQLUpdateSetColumn(column, value);
        sets.add(set);
    }
    //========================================================================
    public void addSetColumn (String column, double value) {
        SQLUpdateSetColumn set = new SQLUpdateSetColumn(column, value);
        sets.add(set);
    }
    //========================================================================
    public void addSetColumn (String column, String value) {
        SQLUpdateSetColumn set = new SQLUpdateSetColumn(column, value);
        sets.add(set);
    }
    //========================================================================
    public void addSetColumn (String column, byte[] value) {
        SQLUpdateSetColumn set = new SQLUpdateSetColumn(column, value);
        sets.add(set);
    }
    //========================================================================
    public void addSetColumnFromColumn (String columndest, String columnsrc) {
        SQLUpdateSetColumn set = new SQLUpdateSetColumn(columndest, columnsrc, true);
        sets.add(set);
    }
    //========================================================================
    @Override
    public String getText () {
        StringBuilder ret = new StringBuilder("UPDATE ");
        ret.append(tab);
        ret.append("\nSET ");
        boolean isfirst = true;
        for (SQLUpdateSetColumn set : sets)
        {
            if (!isfirst) ret.append(", ");
            else isfirst = false;
            ret.append(set.getText());
        }
        return ret.toString();
    }
    //========================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        for (SQLUpdateSetColumn set : sets)
            index = set.setParameters(st, index);
        return index;
    }
    //========================================================================
}
//***************************************************************************
