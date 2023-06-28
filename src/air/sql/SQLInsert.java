package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLInsert extends SQLClause
{
    //================================================================
    public SQLInsert (String intotable) { into = intotable; }
    //================================================================
    String into;
    SQLQueryCmd sql = null;
    List<SQLInsertSetColumn> values = new ArrayList();
    //================================================================
    public void setSqlFrom (SQLQueryCmd sqlfrom) { sql = sqlfrom; }
    //================================================================
    public void addValue(String column, int value) {
        SQLInsertSetColumn setcol = new SQLInsertSetColumn(column, value);
    values.add(setcol);
    }
    //----------------------------------------------------------------
    public void addValue(String column, long value) {
        SQLInsertSetColumn setcol = new SQLInsertSetColumn(column, value);
    values.add(setcol);
    }
    //----------------------------------------------------------------
    public void addValue(String column, float value) {
        SQLInsertSetColumn setcol = new SQLInsertSetColumn(column, value);
    values.add(setcol);
    }
    //----------------------------------------------------------------
    public void addValue(String column, double value) {
        SQLInsertSetColumn setcol = new SQLInsertSetColumn(column, value);
    values.add(setcol);
    }
    //----------------------------------------------------------------
    public void addValue(String column, String value) {
        SQLInsertSetColumn setcol = new SQLInsertSetColumn(column, value);
    values.add(setcol);
    }
    //----------------------------------------------------------------
    public void addValue(String column, byte[] value) {
        SQLInsertSetColumn setcol = new SQLInsertSetColumn(column, value);
        values.add(setcol);
    }
    //================================================================
    @Override
    public String getText () {
        StringBuilder ret = new StringBuilder("INSERT INTO ");
        ret.append(into);
        ret.append("\n(");
        boolean isfirst = true;
        for (SQLInsertSetColumn value : values)
        {
            if (!isfirst) ret.append(", ");
            else isfirst = false;
            ret.append(value.getColumnName());
        }
        ret.append(")\n");
        //--------------------------------------------------------
        if (sql != null)
            ret.append(sql.getText());
        else
        {
            ret.append("VALUES(");
            isfirst = true;
            for (SQLInsertSetColumn value : values)
            {
                if (!isfirst) ret.append(", ");
                else isfirst = false;
                ret.append(value.getText());
            }
            ret.append(")");
        }
        return ret.toString();
    }
    //================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        if (sql != null)
            index = sql.setParameters(st, index);
        else
        {
            for (SQLClause item : values)
                index = item.setParameters(st, index);
        }
        return index;    
    }
    //================================================================
}
//***************************************************************************

