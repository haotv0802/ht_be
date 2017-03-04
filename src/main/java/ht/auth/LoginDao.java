package ht.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

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

  public CustomizedUserDetails findOneByUsername(String username) {

    final String findOneByUsername = "SELECT user_name, password FROM security_db.user_table where user_name = :username";


    final MapSqlParameterSource paramsMap = new MapSqlParameterSource()
        .addValue("userName", username)
        ;

    CustomizedUserDetails ud = null;
    try {
      ud = namedTemplate.queryForObject(findOneByUsername, paramsMap, new RowMapper<CustomizedUserDetails>() {
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

}


