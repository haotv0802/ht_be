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
    final String sql = "SELECT user_name, password FROM security_db.user_table where user_name = :username and password = :password";

    final MapSqlParameterSource paramsMap = new MapSqlParameterSource()
        .addValue("username", credentials.getUserName())
        .addValue("password", credentials.getUserPass());
    CredentialsResult result = namedTemplate.queryForObject(sql, paramsMap, new RowMapper<CredentialsResult>() {

      @Override
      public CredentialsResult mapRow(ResultSet resultSet, int i) throws SQLException {
        CredentialsResult result = new CredentialsResult();
        result.setUserLang(resultSet.getString("user_name"));
        return result;
      }
    });

    return result;
  }

  public CustomizedUserDetails findOneByUsername(String username) {

    final String sql = "SELECT user_name, password FROM security_db.user_table where user_name = :username";

    final MapSqlParameterSource paramsMap = new MapSqlParameterSource()
        .addValue("userName", username);

    CustomizedUserDetails ud = null;
    try {
      ud = namedTemplate.queryForObject(sql, paramsMap, new RowMapper<CustomizedUserDetails>() {
        @Override
        public CustomizedUserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

          //TODO geting lang from authentication object is plain stupid. So, AN by default
          CustomizedUserDetails iud = new CustomizedUserDetails(rs.getString("user_name"), rs.getString("password"), null);

          return iud;
        }
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
              "INSERT INTO auth_token    "
            + "  (TYPE                   "
            + "  , auth_obj              "
            + "  , dtexpiration_dt)      "
            + "VALUES                    "
            + "  ( ?                     "
            + "  , ?                     "
            + "  , ?)                    ";

//    final Long id = jdbcTemplate.queryForObject(getIdSql, Long.class);

    final SqlLobValue sqlLobValue = new SqlLobValue(SerializationUtils.serialize(user));

    DaoUtils.debugQuery(log, addTokenSql, new Object[]{tokenType.value(), "SIPPED_BLOB", expDate});
    jdbcTemplate.update(
        addTokenSql
        , new Object[]{tokenType.value(), sqlLobValue, expDate}
        , new int[]{Types.NUMERIC, Types.VARCHAR, Types.BLOB, Types.TIMESTAMP}
    );

    final String sql = "SELECT ID FROM AUTH_TOKEN WHERE row_count() = 1 ORDER BY ID DESC";
    DaoUtils.debugQuery(log, sql);

    return namedTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
  }

  public UserDetails readUserDetailsForToken(Integer id) {
    final String getTokenSql = "SELECT TOKEN_TYPE, AUTH_OBJECT, EXP_DATE FROM AUTH_TOKEN WHERE ID = ?";
    final Object[] args = {id};

    DaoUtils.debugQuery(log, getTokenSql, args);
    return jdbcTemplate.queryForObject(getTokenSql, args, new RowMapper<UserDetails>() {
      @Override
      public UserDetails mapRow(ResultSet resultSet, int i) throws SQLException {
        final Blob blob = resultSet.getBlob(1);
        return (UserDetails) SerializationUtils.deserialize(blob.getBytes(1, (int) blob.length()));
      }
    });

  }

}


