package ht.api.rest.admin.rooms;

import ht.api.rest.admin.rooms.beans.DayPrice;
import ht.api.rest.admin.rooms.beans.RoomTypeBean;
import ht.api.rest.admin.rooms.beans.RoomTypeImageBean;
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
    final String sql = "SELECT  id, name, num_of_people, num_of_bed, type_of_bed FROM room_type"
        ;

    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    List<RoomTypeBean> roomTypes = namedTemplate.query(sql, paramsMap, (rs, rowNum) -> {
      RoomTypeBean roomType = new RoomTypeBean();
      roomType.setId(rs.getInt("id"));
      roomType.setName(rs.getString("name"));
      roomType.setNumOfPeople(rs.getInt("num_of_people"));
      roomType.setNumOfBeds(rs.getInt("num_of_bed"));
      roomType.setTypeOfBed(rs.getString("type_of_bed"));

      return roomType;
    });

    for (RoomTypeBean room : roomTypes) {
      List<RoomTypeImageBean> images = this.getImages(room.getId());
      room.setImages(images);

      List<DayPrice> prices = this.getPrices(room.getId());
      room.setPrices(prices);
    }
    return roomTypes;
  }

  private List<DayPrice> getPrices(int roomTypeId) {
    final String sql =  "SELECT                           "
                      + "	id, name, price, is_active, day "
                      + "FROM                             "
                      + "	price_set                       "
                      + "WHERE                            "
                      + "	room_type_id = 1                "
                      + "ORDER BY day ASC                 "
        ;
    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();
    paramsMap.addValue("roomTypeId", roomTypeId);

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    List<DayPrice> images = namedTemplate.query(sql, paramsMap, (rs, rowNum) -> {
      DayPrice price = new DayPrice();
      int day = rs.getInt("day");
      if (1 == day) {
        price.setDay("Sunday");
      } else if (2 == day) {
        price.setDay("Monday");
      } else if (3 == day) {
        price.setDay("Tuesday");
      } else if (4 == day) {
        price.setDay("Wednesday");
      } else if (5 == day) {
        price.setDay("Thursday");
      } else if (6 == day) {
        price.setDay("Friday");
      } else if (7 == day) {
        price.setDay("Saturday");
      }
      price.setPrice(rs.getDouble("price"));

      return price;
    });

    return images;
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