package air.sql;
//=======================================================================
import java.sql.PreparedStatement;
import java.sql.SQLException;
//=======================================================================
public class SQLLimit extends SQLClause
{
    //===================================================================
    public SQLLimit() {}
    public SQLLimit(int count) { cnt = count; }
    public SQLLimit(int offset, int count) { offst = offset; cnt = count; }
    //===================================================================
    int offst = 0;
    int cnt = 0;
    //===================================================================
    public void serOffset (int offset) {offst = offset;}
    public void setCount (int count) {cnt = count;}
    //===================================================================
    @Override
    public String getText (){
        StringBuilder ret = new StringBuilder("LIMIT ");
        if (offst != 0) {
            ret.append(offst);
            ret.append(", ");
        }
        ret.append(cnt);
        return ret.toString();
    }
    //===================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        return index;        
    }
    //===================================================================
}
//=======================================================================