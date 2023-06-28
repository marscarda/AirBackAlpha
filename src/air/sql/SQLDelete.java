package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
//***************************************************************************
public class SQLDelete extends SQLClause
{
    //================================================================
    public SQLDelete (String fromtable) { fromtab = fromtable; }
    //================================================================
    String fromtab;
    //================================================================
    @Override
    public String getText () {
        StringBuilder ret = new StringBuilder("DELETE FROM ");
        ret.append(fromtab);
        return ret.toString();
    }
    //================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        return index;    
    }
    //================================================================
}
//***************************************************************************

