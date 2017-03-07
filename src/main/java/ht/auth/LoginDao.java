package ht.auth;

import ht.common.dao.DaoUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SerializationUtils;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

@Repository
public class LoginDao {
  private static final Logger log = LogManager.getLogger(LoginDao.class);

  @Autowired
  NamedParameterJdbcTemplate namedTemplate;

  /**
   * Row jdbc for advanced use
   */
  @Autowired
  JdbcTemplate jdbcTemplate;

  public CredentialsResult checkCredentials(Credentials credentials) {
    final String sql =
        "SELECT user_name, password FROM security_db.user_table where user_name = :username and password = :password";

    final MapSqlParameterSource paramsMap = new MapSqlParameterSource()
        .addValue("username", credentials.getUserName())
        .addValue("password", credentials.getUserPass());
    CredentialsResult result = namedTemplate.queryForObject(sql, paramsMap, (resultSet, i) -> {
      CredentialsResult result1 = new CredentialsResult();
      result1.setUserLang(resultSet.getString("user_name"));
      return result1;
    });

    return result;
  }

  public CustomizedUserDetails findOneByUsername(String username) {

    final String sql = "SELECT user_name, password FROM security_db.user_table where user_name = :username";

    final MapSqlParameterSource paramsMap = new MapSqlParameterSource()
        .addValue("username", username);

    DaoUtils.debugQuery(log, sql, paramsMap.getValues());

    CustomizedUserDetails ud = null;
    try {
      ud = namedTemplate.queryForObject(sql, paramsMap, (rs, rowNum) -> {

        //TODO geting lang from authentication object is plain stupid. So, AN by default
        CustomizedUserDetails iud = new CustomizedUserDetails(rs.getString("user_name"), rs.getString("password"), null);

        return iud;
      });
    } catch (EmptyResultDataAccessException e) {
      log.warn("Credentials not found: ");
    } catch (IncorrectResultSizeDataAccessException e) {
      log.warn("Too many results");
    }

    return ud;
  }

  public Integer storeUserDetailsToToken(TokenType tokenType, UserDetails user, Date expDate) {
//    final String getIdSql = "SELECT v9_auth_token_seq.nextval FROM DUAL";

    final String addTokenSql =
              "INSERT INTO auth_token"
            + "  (TOKEN_TYPE         "
            + "  , AUTH_OBJECT       "
            + "  , EXP_DATE)         "
            + "VALUES                "
            + "  ( ?                 "
            + "  , ?                 "
            + "  , ?)                ";

//    final Long id = jdbcTemplate.queryForObject(getIdSql, Long.class);

    final SqlLobValue sqlLobValue = new SqlLobValue(SerializationUtils.serialize(user));

    DaoUtils.debugQuery(log, addTokenSql, new Object[]{tokenType.value(), "SIPPED_BLOB", expDate});
//    jdbcTemplate.update(addTokenSql, paramsMap);
    jdbcTemplate.update(
        addTokenSql
        , new Object[]{tokenType.value(), sqlLobValue, expDate}
        , new int[]{Types.VARCHAR, Types.BLOB, Types.TIMESTAMP}
    );

    final String sql = "SELECT ID FROM AUTH_TOKEN ORDER BY ID DESC LIMIT 1";
    DaoUtils.debugQuery(log, sql);

    int id = namedTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);

    final String sql2 = "SELECT ID, TOKEN_TYPE, AUTH_OBJECT, EXP_DATE FROM AUTH_TOKEN ORDER BY ID DESC LIMIT 1";
    namedTemplate.queryForObject(sql2, new MapSqlParameterSource(), new RowMapper<CredentialsResult>() {

      @Override
      public CredentialsResult mapRow(ResultSet resultSet, int i) throws SQLException {
        log.info(resultSet.getString("ID"));
        log.info(resultSet.getString("TOKEN_TYPE"));
        log.info(resultSet.getString("EXP_DATE"));
        log.info(resultSet.getString("AUTH_OBJECT"));
        return null;
      }
    });
    return 1;
  }

  public UserDetails readUserDetailsForToken(Integer id) {
    final String getTokenSql = "SELECT TOKEN_TYPE, AUTH_OBJECT, EXP_DATE FROM AUTH_TOKEN WHERE ID = ?";
    final Object[] args = {id};

    DaoUtils.debugQuery(log, getTokenSql, args);
    return jdbcTemplate.queryForObject(getTokenSql, args, (resultSet, i) -> {
      final Blob blob = resultSet.getBlob(1);
      return (UserDetails) SerializationUtils.deserialize(blob.getBytes(1, (int) blob.length()));
    });

  }

}


