package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
//***************************************************************************
public class SQLCondition extends SQLClause {
    //=======================================================================
    SQLColumn col = null;
    String strcol = null;
    private String scomparition;
    private Object obvalue;
    private DataType dtype = DataType.UNDEF;
    //=======================================================================
    public SQLCondition (SQLColumn column, String comparition, SQLColumn value) {
        col = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.COLUMN;
    }
    //-----------------------------------------------------------------
    public SQLCondition (SQLColumn column, String comparition, int value) {
        col = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.INTEGER;
    }
    //-----------------------------------------------------------------
    public SQLCondition (SQLColumn column, String comparition, long value) {
        col = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.LONG;
    }    
   //-----------------------------------------------------------------
    public SQLCondition (SQLColumn column, String comparition, float value) {
        col = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.FLOAT;
    }    
    //-----------------------------------------------------------------
    public SQLCondition (SQLColumn column, String comparition, double value) {
        col = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.DOUBLE;
    }    
    //-----------------------------------------------------------------
    public SQLCondition (SQLColumn column, String comparition, String value) {
        col = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.STRING;
    }
    //-----------------------------------------------------------------
    
    public SQLCondition (SQLColumn column, String comparition, UnquotedText value) {
        col = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.UNQUOTEDTEXT;
    }
    
    //=================================================================
    public SQLCondition (String column, String comparition, int value) {
        strcol = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.INTEGER;
    }
    //-----------------------------------------------------------------
    public SQLCondition (String column, String comparition, long value) {
        strcol = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.LONG;
    }
    //-----------------------------------------------------------------
    public SQLCondition (String column, String comparition, float value) {
        strcol = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.FLOAT;
    }    
    //-----------------------------------------------------------------
    public SQLCondition (String column, String comparition, double value) {
        strcol = column;
        scomparition = comparition;
        obvalue = (Object)value;
        dtype = DataType.DOUBLE;
    }        
   //-----------------------------------------------------------------
    public SQLCondition (String column, String comparition, String value) {
        strcol = column;
        scomparition = comparition;
        obvalue = value;
        dtype = DataType.STRING;
    }
    //-----------------------------------------------------------------
    public SQLCondition (String column, String comparition, UnquotedText value) {
        strcol = column;
        scomparition = comparition;
        obvalue = value;
        dtype = DataType.UNQUOTEDTEXT;
    }    
    //==================================================================
    @Override
    public String getText () {
        //=============================================================
        StringBuilder ret = new StringBuilder();
        if (strcol != null) ret.append(strcol);
        else ret.append(col.getText());
        ret.append(" ");
        ret.append(scomparition);
        ret.append(" ");
        //=============================================================
        switch (dtype) {
            //-------------------------------------------
            case COLUMN: {
                SQLColumn value = (SQLColumn)obvalue;
                ret.append(value.getText());
                return ret.toString();
            }
            //-------------------------------------------
            case INTEGER: {
                int value = (int)obvalue;
                ret.append(value);
                return ret.toString();
            }
            //-------------------------------------------
            case LONG: {
                long value = (long)obvalue;
                ret.append(value);
                return ret.toString();
            }                
            //-------------------------------------------
            case FLOAT: {
                float value = (float)obvalue;
                ret.append(value);
                return ret.toString();
            }
            //-------------------------------------------
            case DOUBLE: {
                double value = (double)obvalue;
                ret.append(value);
                return ret.toString();
            }
            //-------------------------------------------
            case STRING: {
                ret.append("?");
                return ret.toString();
            }                
            //-------------------------------------------
            case UNQUOTEDTEXT: {
                UnquotedText value = (UnquotedText)obvalue;
                ret.append(value.getText());
                return ret.toString();
            }
            //-------------------------------------------
        }
        //=============================================================
        return ret.toString();
    }
    //==================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        switch (dtype) {
            case STRING: {
                String value = (String)obvalue;
                st.setString(index, value);
                index++;
                break;
            }
            default: break;
        }
        return index; 
    }
}
//***************************************************************************

