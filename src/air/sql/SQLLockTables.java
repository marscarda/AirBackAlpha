package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLLockTables extends SQLClause
{
    //=======================================================================
    public static String UNLOCK = "UNLOCK TABLES";
    //=======================================================================
    String database = null;
    List<String> tables = new ArrayList();
    //=======================================================================
    public void setDataBase (String db) {
        database = db; 
    }
    //=======================================================================
    public void addTable (String table) {
        tables.add(table);
    }
    //=======================================================================    
    @Override
    public String getText () {
        //===================================================================
        StringBuilder ret = new StringBuilder("LOCK TABLES ");
        boolean isfirst = true;
        for (String table : tables) {
            if (!isfirst) ret.append(", ");
            else isfirst = false;
            ret.append(database);
            ret.append(".");
            ret.append(table);
            ret.append(" WRITE");
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
    //=======================================================================    
}
//***************************************************************************

