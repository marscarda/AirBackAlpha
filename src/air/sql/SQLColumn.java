package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
//***************************************************************************
public class SQLColumn extends SQLClause {
    //==================================================================
    public SQLColumn () {}
    public SQLColumn (String column) { col = column; }
    public SQLColumn (String table, String column) { tab = table; col = column; }
    //==================================================================
    public String tab = null;
    public String col = null;
    @Override
    public String getText() {
        StringBuilder strRet = new StringBuilder();
        if (tab != null) {
            strRet.append(tab);
            strRet.append(".");
        }
        strRet.append(col);
        return strRet.toString();
    }
    //==================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException { return index; }
    //==================================================================
}
//***************************************************************************
