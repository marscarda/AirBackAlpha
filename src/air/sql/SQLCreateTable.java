package air.sql;
//***************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//***************************************************************************
public class SQLCreateTable extends SQLClause
{
    //==================================================================
    public static final String DEFAULTIDFIELD = "Id";
    //==================================================================
    public SQLCreateTable (String table) {
        tab = table;
    }
    //==================================================================
    String tab = null;
    List<String> fields = new ArrayList<>();
    MySQLEngine engine = MySQLEngine.NONE;
    boolean id = true;
    //==================================================================
    public void setNoID () { id = false; }
    public void setEngine (MySQLEngine eng) { engine = eng; }
    public void addField (String name, String definition) {
        StringBuilder field = new StringBuilder();
        field.append(name);
        field.append(" ");
        field.append(definition);
        fields.add(field.toString());
    }
    public void addUnique (String field) {
        StringBuilder constraint = new StringBuilder();
        constraint.append("UNIQUE (");
        constraint.append(field);
        constraint.append(")");
        fields.add(constraint.toString());
    }
    //==================================================================
    @Override
    public String getText() {
        StringBuilder ret = new StringBuilder();
        ret.append("CREATE TABLE \n");
        ret.append(tab);
        ret.append("\n(\n");
        if (id) ret.append(DEFAULTIDFIELD + " INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,\n");
        int fieldcount = fields.size();
        int fieldnum = 1;
        for (String field : fields) {
            ret.append(field);
            if (!id && fieldnum == fieldcount) ret.append("\n");
            else ret.append(",\n");
            fieldnum++;
        }
        if (id) ret.append("PRIMARY KEY (" + DEFAULTIDFIELD + ")\n");
        if (engine == MySQLEngine.NONE) ret.append(");");
        else {
            ret.append(")\n");
            ret.append("ENGINE = ");
            switch (engine) {
                case INNODB: ret.append("InnoDB;"); break;
                case MEMORY: ret.append("MEMORY;"); break;
            }
        }
        return ret.toString();
    }    
    //==================================================================
    @Override
    public int setParameters (PreparedStatement st, int index) throws SQLException { return index; }
    //==================================================================
}
//***************************************************************************

