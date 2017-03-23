package ht.api.rest.admin.rooms;

import ht.api.rest.admin.rooms.interfaces.IRoomDao;
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
public class RoomDao implements IRoomDao {
  private static final Logger LOGGER = LogManager.getLogger(RoomDao.class);

  private final NamedParameterJdbcTemplate namedTemplate;

  @Autowired
  public RoomDao(NamedParameterJdbcTemplate namedTemplate) {
    Assert.notNull(namedTemplate);
    this.namedTemplate = namedTemplate;
  }

  @Override
  public List<RoomTypeBean> getRoomTypes() {
    final String sql = "SELECT                                             "
                     + "    r.name, i.image_url, i.image_info, i.id imageId"
                     + " FROM                                              "
                     + "    room_type r                                    "
                     + "        INNER JOIN                                 "
                     + "    room_type_image i ON r.id = i.room_type_id     "
        ;

    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    List<RoomTypeBean> roomTypes = namedTemplate.query(sql, paramsMap, (rs, rowNum) -> {
      RoomTypeBean roomType = new RoomTypeBean();
      roomType.setName(rs.getString("name"));
      roomType.setImageURL(rs.getString("image_url"));
      roomType.setImageInfo(rs.getString("image_info"));
      roomType.setImageId(rs.getString("imageId"));
      return roomType;
    });

    return roomTypes;
  }
}