package air.root;
//*******************************************************************************
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import air.sql.SQLCondition;
import air.sql.SQLQueryCmd;
import air.sql.SQLSelect;
import air.sql.SQLWhere;
import java.sql.Statement;
//*******************************************************************************
public class Alcyone {
    //*************************************************************************
    protected Electra electra = null;
    protected String databasename = "db_name_is_not_set";
    protected Connection connection = null;
    //*************************************************************************
    public void setDataBaseName (String db) { databasename = db; }
    //=========================================================================
    /**
     * Sets the database that must be used.
     * @throws SQLException 
     */
    protected void setDataBase () throws SQLException {
        Statement st = connection.createStatement();
        StringBuilder use = new StringBuilder("use ");
        use.append(databasename);
        st.execute(use.toString());
    }    
    //*************************************************************************    
    public void setElectra (Electra electra) {
        this.electra = electra;
    }
    //*************************************************************************
    /**
     * Checks the count of a string value in the table.
     * @param table
     * @param column
     * @param value
     * @return
     * @throws Exception 
     */
    public int checkValueCount (String table, String column, String value) throws Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(table);
        select.addItem("COUNT", "*", "count");
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(column, "=", value));
        sql.addClause(whr);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            rs.next();
            return rs.getInt("count");
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select count of " + column + " from table" + table + "\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
        //-------------------------------------------------------
    }
    //*************************************************************************
}
//*******************************************************************************