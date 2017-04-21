package ht.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
  private static final Logger logger = LogManager.getLogger(JdbcUtils.class);
  private static final boolean DEBUG = logger.isDebugEnabled();


  public static void close(Connection conn) {

    if (null != conn) {
      try {
        conn.close();
        if (DEBUG) logger.debug("Closed: " + conn);
      } catch (SQLException e) {
        if (DEBUG) logger.debug("Unable to close connection");
      }
    }
  }

  public static void close(Statement stmt) {

    if (null != stmt) {
      try {
        stmt.close();
      } catch (SQLException e) {
        if (DEBUG) logger.debug("Unable to close statement");
      }
    }
  }

  public static void close(ResultSet rs) {

    if (null != rs) {
      try {
        rs.close();
      } catch (SQLException e) {
        if (DEBUG) logger.debug("Unable to close result set");
      }
    }
  }

  public static void tryCommit(Connection con) throws SQLException {
    if (null != con) {
      try {

        con.commit();
        if (DEBUG) logger.debug("Committed: " + con);
      } catch (SQLException e) {
        if (DEBUG) logger.debug("Unable to commit");
        throw e;
      }
    }
  }

  public static void tryRoll(Connection con) throws SQLException {
    if (null != con) {
      try {
        con.rollback();
        if (DEBUG) logger.debug("Rolled: " + con);
      } catch (SQLException e) {
        if (DEBUG) logger.debug("Unable to rollback");
        throw e;
      }
    }
  }

  /**
   * Gets a object Integer from result set.
   * This allows returning of null as opposed to the standard rs.getInt() which will return 0
   * when the column value is null
   *
   * @param rs       the result set. Use this method inside the while loop of a result set.
   * @param colAlias the column alias
   * @return Integer or null
   * @throws SQLException if the column cannot be converted to integer
   */
  public static Integer getReferencedInteger(ResultSet rs, String colAlias) throws SQLException {
    final String string = rs.getString(colAlias);
    if (string == null) {
      return null;
    }

    try {
      return Integer.parseInt(string);
    } catch (NumberFormatException e) {
      throw new SQLException("Value or number error", "IMX data conversion");
    }
  }

  /**
   * Gets a java.util.Date from java.sql.Date.
   * Useful for retrieving dates from the result set
   *
   * @param dt java.sql.date
   * @return java.util.date or null
   */
  public static java.util.Date getDateFromSQL(java.sql.Date dt) {
    return dt != null ? new java.util.Date(dt.getTime()) : null;
  }

}