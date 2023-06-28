package air.sql;
//***************************************************************************
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//===========================================================================
enum DataType {
    UNDEF,
    NULL,
    COLUMN,
    EXPRESSION,
    INTEGER,
    LONG,
    FLOAT,
    DOUBLE,
    STRING,
    UNQUOTEDTEXT,
    BINARY;
}
//===========================================================================
class EntityExpresionItem extends SQLClause {
    public String text = null;
    public boolean quoted = false;
    @Override
    public String getText() {
        StringBuilder strRet = new StringBuilder();
        if (quoted) {
            strRet.append("'");
            strRet.append(text);
            strRet.append("'");
        }
        else strRet.append(text);
        return strRet.toString();
    }
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException { return index; }
}
//===========================================================================
class SQLInsertSetColumn extends SQLClause {
    //=======================================================================
    public SQLInsertSetColumn (String column, int value) {
        setcolumn = column;
        val = value;
        dtype = DataType.INTEGER;
    }
    //-----------------------------------------------------------------------
    public SQLInsertSetColumn (String column, long value) {
        setcolumn = column;
        val = value;
        dtype = DataType.LONG;
    }
    //-----------------------------------------------------------------------
    public SQLInsertSetColumn (String column, float value) {
        setcolumn = column;
        val = value;
        dtype = DataType.FLOAT;
    }
    //-----------------------------------------------------------------------
    public SQLInsertSetColumn (String column, double value) {
        setcolumn = column;
        val = value;
        dtype = DataType.DOUBLE;
    }
    //-----------------------------------------------------------------------
    public SQLInsertSetColumn (String column, String value) {
        setcolumn = column;
        val = value;
        dtype = DataType.STRING;
    }
    //-----------------------------------------------------------------------
    public SQLInsertSetColumn (String column, byte[] value) {
        setcolumn = column;
        val = value;
        dtype = DataType.BINARY;
    }
    //-----------------------------------------------------------------------
    /**
     * @param column
     * @param value MUST BE AN OBJECT OF A CLASS THAT IMPLEMENTS Serializable.
     */
    public SQLInsertSetColumn (String column, Object value) {
        //====================================================
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytesarray;
        //====================================================
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(value);
            bytesarray = bos.toByteArray();
        }
        catch (Exception e) {
            System.out.println("Faled to serialize a value to insert DB");
            System.out.println(e.getMessage());
            bytesarray = new byte[0];
        }
        finally {
            try { if (out != null) out.close(); }
            catch (Exception e) {}
        }
        //====================================================
        setcolumn = column;
        val = bytesarray;
        dtype = DataType.BINARY;
        //====================================================
    }
    //=======================================================================
    String setcolumn = null;
    Object val = null;
    DataType dtype = DataType.UNDEF;
    //=======================================================================
    public String getColumnName () {return setcolumn;}
    //=======================================================================
    @Override
    public String getText() {
        switch (dtype) {
            case INTEGER: {
                int v = (int)val;
                return String.valueOf(v);
            }
            case LONG: {
                long v = (long)val;
                return String.valueOf(v);
            }
            case FLOAT: {
                float v = (float)val;
                return String.valueOf(v);
            }
            case DOUBLE: {
                double v = (double)val;
                return String.valueOf(v);
            }
            case STRING:
            case BINARY:
                return "?";
            default: return "";
        }
    }
    //=======================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        switch (dtype) {
            case STRING: {
                String value = (String)val;
                st.setString(index, value);
                index++;
                break;
            }
            case BINARY: {
                byte[] value = (byte[])val;
                st.setBytes(index, value);
                index++;
                break;
            }
            default: break;
        }
        return index;
    }
    //=======================================================================
}
//===========================================================================
class SQLUpdateSetColumn extends SQLClause {
    //=======================================================================
    public SQLUpdateSetColumn (String column) {
        setcolumn = column;
        val = null;
        dtype = DataType.NULL;
    }
    //=======================================================================
    public SQLUpdateSetColumn (String column, int value) {
        setcolumn = column;
        val = value;
        dtype = DataType.INTEGER;
    }
    //=======================================================================
    public SQLUpdateSetColumn (String column, long value) {
        setcolumn = column;
        val = value;
        dtype = DataType.LONG;
    }
    //=======================================================================
    public SQLUpdateSetColumn (String column, float value) {
        setcolumn = column;
        val = value;
        dtype = DataType.FLOAT;
    }
    //=======================================================================
    public SQLUpdateSetColumn (String column, double value) {
        setcolumn = column;
        val = value;
        dtype = DataType.DOUBLE;
    }
    //=======================================================================
    public SQLUpdateSetColumn (String column, String value) {
        setcolumn = column;
        val = value;
        dtype = DataType.STRING;
    }
    //=======================================================================
    public SQLUpdateSetColumn (String column, byte[] value) {
        setcolumn = column;
        val = value;
        dtype = DataType.BINARY;
    }
    //=======================================================================
    public SQLUpdateSetColumn (String column, String value, boolean valiscolname) {
        if (!valiscolname) {
            setcolumn = column;
            val = value;
            dtype = DataType.STRING;
            return;
        }
        setcolumn = column;
        val = value;
        dtype = DataType.COLUMN;
    }
    //=======================================================================
    String setcolumn = null;
    Object val = null;
    DataType dtype = DataType.UNDEF;    
    //=======================================================================
    @Override
    public String getText() {
        StringBuilder ret = new StringBuilder(setcolumn);
        ret.append(" = ");
        switch (dtype) {
            case NULL:
                ret.append("NULL");
                break;
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
                ret.append(val);
                break;
            case STRING:
            case BINARY:
                ret.append("?");
                break;
            case COLUMN:
                ret.append(val);
                break;
        }
        return ret.toString();
    }
    //=======================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        switch (dtype) {
            case STRING: {
                String value = (String)val;
                st.setString(index, value);
                index++;
                break;
            }
            case BINARY: {
                byte[] value = (byte[])val;
                st.setBytes(index, value);
                index++;
                break;
            }
            default: break;
        }
        return index;
    }
    //=======================================================================
}
//***************************************************************************


