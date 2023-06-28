package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLExpression extends SQLClause {
    //===================================================================
    public SQLExpression(){}
    //---------------------------------------------------------------
    public SQLExpression(String agregate) {
        agreg = agregate;
    }
    //---------------------------------------------------------------
    public SQLExpression(String agregate, String asname) {
        agreg = agregate;
        as = asname;
    }
    //===================================================================
    String agreg = null;
    List<SQLClause> entities = new ArrayList();
    String as = null;
    //===================================================================
    public void addQuotedString (String value) {
        EntityExpresionItem entity = new EntityExpresionItem();
        entity.text = value;
        entity.quoted = true;
        entities.add(entity);
    }
    public void addNotQuoteddString (String value) {
        EntityExpresionItem entity = new EntityExpresionItem();
        entity.text = value;
        entity.quoted = false;
        entities.add(entity);
    }
    public void addInt (int value) {
        EntityExpresionItem entity = new EntityExpresionItem();
        entity.text = String.valueOf(value);
        entity.quoted = false;
        entities.add(entity);
    }
    public void addLong (long value) {
        EntityExpresionItem entity = new EntityExpresionItem();
        entity.text = String.valueOf(value);
        entity.quoted = false;
        entities.add(entity);
    }    
    public void addFloat (float value) {
        EntityExpresionItem entity = new EntityExpresionItem();
        entity.text = String.valueOf(value);
        entity.quoted = false;
        entities.add(entity);
    }
    public void addDouble (double value) {
        EntityExpresionItem entity = new EntityExpresionItem();
        entity.text = String.valueOf(value);
        entity.quoted = false;
        entities.add(entity);
    }
    public void addBoolean (boolean value) {
        EntityExpresionItem entity = new EntityExpresionItem();
        entity.text = String.valueOf(value);
        entity.quoted = false;
        entities.add(entity);
    }
    public void addExpression (SQLExpression expression) {
        entities.add(expression);
    }
    //===================================================================
    @Override
    public String getText () {
        StringBuilder ret = new StringBuilder();
        if (agreg != null) {
            ret.append(agreg);
            ret.append("(");
        }
        boolean isfirst = true;
        for (SQLClause entity : entities){
            //-------------------------------------------------------
            if (!isfirst) ret.append(" ");
            else isfirst = false;
            ret.append(entity.getText());
            //-------------------------------------------------------
        }
        if (agreg != null) ret.append(")");
        //-----------------------------------------------------------
        if (as != null) {
            ret.append(" AS ");
            ret.append(as);
        }
        //-----------------------------------------------------------
        return ret.toString();
    }
    //=======================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException {
        for (SQLClause entity : entities)
            index = entity.setParameters(st, index);
        return index;        
    }
    //=======================================================================
}
//***************************************************************************