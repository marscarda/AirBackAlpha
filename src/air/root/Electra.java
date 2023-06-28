package air.root;
//*****************************************************************************
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//*****************************************************************************
/**
 * This class holds the actual connection objects to the database
 * Holds 2 connections. One to the master and one to the slave.
 * any connection is created the first time it is requested
 * @author marian
 */
public class Electra {
    //*************************************************************************
    private static String dfltmasterhost = null;
    private static String dfltslavehost = null;
    private static String dfltdbuser = null;
    private static String dfltdbpass = null;
    private static boolean dfltismaster = false;
    //=========================================================================
    public static void setDefaultMasterHost (String h) { dfltmasterhost = h; }
    public static void setDefaultSlaveHost (String h) { dfltslavehost = h; }
    public static void setDefaultDBUser (String u) { dfltdbuser = u; }
    public static void setDefaultDBPass (String p) { dfltdbpass = p; }
    public static void setDefaultMaster (boolean m) { dfltismaster = m; }
    //*************************************************************************
    private Connection masterconn = null;
    private Connection slaveconn = null;
    //=========================================================================
    private String masterhost = dfltmasterhost;
    private String slavehost = dfltslavehost;
    private String dbuser = dfltdbuser;
    private String dbpass = dfltdbpass;
    private boolean ismaster = dfltismaster;
    //*************************************************************************
    public void setMasterHost (String h) { masterhost = h; }
    public void setSlaveHost (String h) { slavehost = h; }
    public void setDBUser (String u) { dbuser = u; }
    public void setDBPass (String p) { dbpass = p; }
    public void setIsMaster (boolean m) { ismaster = m; }
    public boolean isMaster () { return ismaster; }
    //*************************************************************************
    public Connection masterConnection () throws Exception {
        //----------------------------------------------------------
        if (masterconn == null) 
            masterconn = creaateSSLConnection(masterhost, dbuser, dbpass);
        return masterconn;
        //----------------------------------------------------------
    }
    //==============================================================
    public Connection slaveConnection () throws Exception {
        //----------------------------------------------------------
        if (ismaster)
            return masterConnection();
        //----------------------------------------------------------        
        if (slaveconn == null) 
            slaveconn = creaateSSLConnection(slavehost, dbuser, dbpass);
        return slaveconn;
    }
    //*************************************************************************
    public Connection mainSrvConnection () throws Exception {
        //----------------------------------------------------------
        if (masterconn == null) 
            masterconn = creaateSSLConnection(masterhost, dbuser, dbpass);
        return masterconn;
        //----------------------------------------------------------
    }
    //=========================================================================
    public Connection nearSrvConnection () throws Exception {
        //----------------------------------------------------------
        if (ismaster)
            return masterConnection();
        //----------------------------------------------------------        
        if (slaveconn == null) 
            slaveconn = creaateSSLConnection(slavehost, dbuser, dbpass);
        return slaveconn;
    }
    //*************************************************************************
    public void disposeDBConnection () {
        //=========================================
        try { if (slaveconn != null) destroyDBConnection(slaveconn); }
        catch (Exception e)
        {
            System.out.println("appmsg: When closing slave connection");
            System.out.println(e.getMessage());
        }
        //=========================================
        try { if (masterconn != null) destroyDBConnection(masterconn); }
        catch (Exception e)
        {
            System.out.println("appmsg: When closing master connection");
            System.out.println(e.getMessage());
        }
        //=========================================
    }
    //*************************************************************************
    static Connection creaateSSLConnection (String server, String user, String password) throws Exception {
            StringBuilder srvurl = new StringBuilder("jdbc:mysql://");
            srvurl.append(server);
            srvurl.append("?enabledTLSProtocols=TLSv1.2");
            srvurl.append("&verifyServerCertificate=true");
            srvurl.append("&useSSL=true");
            //srvurl.append("&requireSSL=true");
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                return DriverManager.getConnection(srvurl.toString(), user, password);
            }
            catch (SQLException e) {
                StringBuilder msg = new StringBuilder("Could not open DB Connection\n");
                msg.append(e.getMessage());
                throw new Exception(msg.toString());
            }
    }
    //*************************************************************************
    static void destroyDBConnection (Connection connection) {
        if (connection == null) return;
        try { connection.rollback(); }
        catch (SQLException e) {}
        try { connection.close(); }
        catch (SQLException e) {}
    }
    //*************************************************************************
}
//*****************************************************************************
