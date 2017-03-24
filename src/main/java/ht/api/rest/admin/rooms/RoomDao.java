package ht.api.rest.admin.rooms;

import ht.api.rest.admin.rooms.interfaces.IRoomDao;
import ht.common.dao.DaoUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    final String sql = "SELECT  id, name FROM room_type"
        ;

    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    List<RoomTypeBean> roomTypes = namedTemplate.query(sql, paramsMap, (rs, rowNum) -> {
      RoomTypeBean roomType = new RoomTypeBean();
      roomType.setId(rs.getInt("id"));
      roomType.setName(rs.getString("name"));
      return roomType;
    });

    for (RoomTypeBean room : roomTypes) {
      List<RoomTypeImageBean> images = this.getImages(room.getId());
      room.setImages(images);
    }
    return roomTypes;
  }

  private List<RoomTypeImageBean> getImages(int roomTypeId) {
    final String sql =  "SELECT                     "
                      + "	id, image_url, image_info "
                      + "FROM                       "
                      + "	room_type_image           "
                      + "WHERE                      "
                      + "	room_type_id = :roomTypeId"
        ;
    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();
    paramsMap.addValue("roomTypeId", roomTypeId);

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    List<RoomTypeImageBean> images = namedTemplate.query(sql, paramsMap, (rs, rowNum) -> {
      RoomTypeImageBean image = new RoomTypeImageBean();
      image.setId(rs.getInt("id"));
      image.setImageURL(rs.getString("image_url"));
      image.setImageInfo(rs.getString("image_info"));
      return image;
    });

    return images;
  }
}