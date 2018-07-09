package model.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;


/**
 * <h1>The Class ExampleDAO.</h1>
 *
 * @author Jean-Aymeric DIET jadiet@cesi.fr
 * @version 1.0
 */
public abstract class GameDAO extends TronDAO{

    private static String sqlParty   = "{call Add_Game(?,?)}";
    
    
    public static void setExampleByInt(final int player, final long time) throws SQLException {
        final CallableStatement callStatement = prepareCall(sqlParty);
        callStatement.setInt(1, player);
        callStatement.setLong(2, time);
        callStatement.executeUpdate();
    }
}
