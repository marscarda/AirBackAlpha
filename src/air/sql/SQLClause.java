package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
//***************************************************************************
public abstract class SQLClause {
    ClauseType type;
    public abstract String getText ();
    public abstract int setParameters (PreparedStatement st, int index) throws SQLException;
    public ClauseType getType (){ return type; }
}
//***************************************************************************

