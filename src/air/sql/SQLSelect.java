package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLSelect extends SQLClause
{
    //===================================================================
    public SQLSelect (){}
    //-------------------------------------------------------------------
    public SQLSelect (String table) {
        prefix = true;
        tab = table;
    }
    //-------------------------------------------------------------------
    /**
     * Initializes it with the table name.
     * @param table
     * @param selectprefix If "Select must be added
     */
    public SQLSelect (String table, boolean selectprefix) {
        prefix = selectprefix;
        tab = table;
    }
    //===================================================================
    boolean prefix;
    String tab = null;
    List<SQLClause> listitems = new ArrayList();
    //===================================================================
    public void addItem (String agregate, String column, String asname) {
        SQLExpression expression = new SQLExpression(agregate, asname);
        expression.addNotQuoteddString(column);
        listitems.add(expression);
    }
    //-------------------------------------------------------------------
    public void addItem (String agregate, String table, String column, String asname) {
        StringBuilder colname = new StringBuilder(table);
        colname.append(".");
        colname.append(column);
        SQLExpression expression = new SQLExpression(agregate, asname);
        expression.addNotQuoteddString(colname.toString());
        listitems.add(expression);
    }
    //-------------------------------------------------------------------
    public void addItem (String column) {
        SQLColumn col = new SQLColumn();
        col.tab = tab;
        col.col = column;
        listitems.add(col);
    }
    //-------------------------------------------------------------------
    public void addItem (String table, String column) {
        SQLColumn col = new SQLColumn();
        col.tab = table;
        col.col = column;
        listitems.add(col);
    }
    //-------------------------------------------------------------------
    public void addItem (SQLExpression expression){ listitems.add(expression); }
    //===================================================================
    @Override
    public String getText () {
        StringBuilder ret = new StringBuilder();
        if (prefix) ret.append("SELECT ");
        boolean isfirst = true;
        if (listitems.isEmpty() && tab != null) {
            ret.append(tab);
            ret.append(".*");
        }
        else {
            for (SQLClause item : listitems) {
                //-------------------------------------------------------
                if (!isfirst) ret.append(", ");
                else isfirst = false;
                ret.append(item.getText());
                //-------------------------------------------------------
            }
        }
        ret.append(" FROM ");
        ret.append(tab);
        return ret.toString();
    }
    //=======================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        for (SQLClause item : listitems)
            index = item.setParameters(st, index);
        return index;    
    }
    //=======================================================================
}
//***************************************************************************

