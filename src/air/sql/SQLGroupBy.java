package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLGroupBy extends SQLClause
{
    //=======================================================================
    List<String> groupitems = new ArrayList();
    //=======================================================================
    public void addColumn (String name) { groupitems.add(name); }
    //=======================================================================
    @Override
    public String getText () {
        //===================================================================
        StringBuilder ret = new StringBuilder("GROUP BY ");
        boolean isfirst = true;
        for (String item : groupitems)
        {
            if (!isfirst) ret.append(", ");
            else isfirst = false;
            ret.append(item);
        }
        //===================================================================
        return ret.toString();
        //===================================================================
    }
    //=======================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        return index;    
    }    
}
//***************************************************************************
