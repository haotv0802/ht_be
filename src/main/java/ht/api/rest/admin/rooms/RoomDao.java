package ht.api.rest.admin.rooms;

import ht.api.rest.admin.users.UserBean;
import ht.api.rest.admin.users.intefaces.IUserDao;
import ht.common.dao.DaoUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
@Repository("adminRoomDao")
public class RoomDao {
  private static final Logger LOGGER = LogManager.getLogger(RoomDao.class);

  private final NamedParameterJdbcTemplate namedTemplate;

  @Autowired
  public RoomDao(NamedParameterJdbcTemplate namedTemplate) {
    Assert.notNull(namedTemplate);
    this.namedTemplate = namedTemplate;
  }

//  @Override
//  public List<RoomTypeBean> getRoomTypes() {
//    final String sql = "SELECT                                               "
//                     + "	u.id, u.user_name, r.role_name                     "
//                     + "FROM                                                 "
//                     + "	(user_role r                                       "
//                     + "	INNER JOIN user_role_details d ON r.id = d.role_id)"
//                     + "		INNER JOIN                                       "
//                     + "	user_table u ON u.id = d.user_id                   "
//                     + "ORDER BY r.id                                        "
//        ;
//
//    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();
//
//    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());
//
//    List<UserBean> users = namedTemplate.query(sql, paramsMap, (rs, rowNum) -> {
//      UserBean user = new UserBean();
//      user.setId(rs.getInt("id"));
//      user.setName(rs.getString("user_name"));
//      user.setRole(rs.getString("role_name"));
//      return user;
//    });
//
//    return users;
//  }
}
