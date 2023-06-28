package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
//***************************************************************************
public class UnquotedText extends SQLClause
{
    String txt = null;
    public UnquotedText () {};
    public UnquotedText (String text) { txt = text; }
    //================================================================
    @Override
    public String getText () {
        if (txt == null) return "NULL";
        return txt;
    }
    //================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException { return index; }
    //================================================================
}
//***************************************************************************

