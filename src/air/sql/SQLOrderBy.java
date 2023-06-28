package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLOrderBy extends SQLClause
{
    //=======================================================================
    List<OrderItem> orderitems = new ArrayList();
    //=======================================================================
    public void addColumn (String name) {
        OrderItem item = new OrderItem();
        item.name = name;
        orderitems.add(item);
    }
    //-----------------------------------------------------------------------
    public void addColumn (String name, boolean desc) {
        OrderItem item = new OrderItem();
        item.name = name;
        item.desc = desc;
        orderitems.add(item);
    }    
    @Override
    public String getText () {
        //===================================================================
        StringBuilder ret = new StringBuilder("ORDER BY ");
        boolean isfirst = true;
         
        for (OrderItem item : orderitems)
        {
            if (!isfirst) ret.append(", ");
            else isfirst = false;
            ret.append(item.name);
            if (item.desc) ret.append(" DESC");
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
class OrderItem {
    String name;
    boolean desc = false;
}
//***************************************************************************
