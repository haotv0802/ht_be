package ht.api.rest.admin.individuals;

import ht.common.dao.DaoUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
@Repository("adminIndividualDao")
public class IndividualDao implements ht.api.rest.admin.individuals.interfaces.IIndividualDao {
  private static final Logger LOGGER = LogManager.getLogger(IndividualDao.class);

  private final NamedParameterJdbcTemplate namedTemplate;

  @Autowired
  public IndividualDao(NamedParameterJdbcTemplate namedTemplate) {
    Assert.notNull(namedTemplate);
    this.namedTemplate = namedTemplate;
  }

  @Override
  public List<IndividualModel> getIndividuals() {
    String sql = "SELECT                                                "
               + "	i.id,                                               "
               + "	i.first_name,                                       "
               + "	i.last_name,                                        "
               + "	i.middle_name,                                      "
               + "	i.birthday,                                         "
               + "	i.gender,                                           "
               + "	i.email,                                            "
               + "	i.phone_number,                                     "
               + "	i.image_id,                                         "
               + "	i.user_id,                                          "
               + "	u.user_name,                                        "
               + "	m.image_url,                                        "
               + "	m.image_info,                                       "
               + "	r.role_name                                         "
               + "FROM                                                  "
               + "	(((individual i                                     "
               + "	LEFT JOIN user_table u ON i.user_id = u.id)         "
               + "	LEFT JOIN image m ON m.id = i.image_id)             "
               + "	LEFT JOIN user_role_details d ON d.user_id = u.id)  "
               + "		LEFT JOIN                                         "
               + "	user_role r ON r.id = d.role_id                     "
        ;

    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    List<IndividualModel> users = namedTemplate.query(sql, paramsMap, (rs, rowNum) -> {
      IndividualModel bean = new IndividualModel();
      bean.setId(rs.getInt("id"));
      bean.setFirstName(rs.getString("first_name"));
      bean.setLastName(rs.getString("last_name"));
      bean.setMiddleName(rs.getString("middle_name"));
      bean.setBirthday(rs.getDate("birthday"));
      bean.setGender(rs.getString("gender"));
      bean.setEmail(rs.getString("email"));
      bean.setPhoneNumber(rs.getString("phone_number"));
      bean.setImageURL(rs.getString("image_url"));
      bean.setImageInfo(rs.getString("image_info"));
      bean.setUserName(rs.getString("user_name"));
      bean.setUserId(rs.getInt("user_id"));
      bean.setRole(rs.getString("role_name"));
      return bean;
    });

    return users;
  }

  private List<String> getRoles(int userId) {
    final String sql = "SELECT                                    "
                     + "	r.role_name                             "
                     + "FROM                                      "
                     + "	user_role r                             "
                     + "		INNER JOIN                            "
                     + "	user_role_details d ON d.role_id = r.id "
                     + "WHERE                                     "
                     + "	d.user_id = :userId                     "
        ;
    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();
    paramsMap.addValue("userId", userId);

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    return namedTemplate.queryForList(sql, paramsMap, String.class);
  }
}
