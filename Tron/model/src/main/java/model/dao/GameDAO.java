package model.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Example;

/**
 * <h1>The Class ExampleDAO.</h1>
 *
 * @author Jean-Aymeric DIET jadiet@cesi.fr
 * @version 1.0
 */
public abstract class GameDAO extends TronDAO{

    private static String sqlParty   = "{call writeParty(?,?,?)}";
    
    
    public static void setExampleByInt(final int player, final int time) throws SQLException {
        final CallableStatement callStatement = prepareCall(sqlParty);
        callStatement.setInt(2, player);
        callStatement.setInt(3, time);
        callStatement.executeUpdate();
    }
}