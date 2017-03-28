package ht.api.rest.admin.images;

import ht.api.rest.admin.images.beans.Image;
import ht.api.rest.admin.images.interfaces.IImageDao;
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
@Repository("adminImageDao")
public class ImageDao implements IImageDao {
  private static final Logger LOGGER = LogManager.getLogger(ImageDao.class);

  private final NamedParameterJdbcTemplate namedTemplate;

  @Autowired
  public ImageDao(NamedParameterJdbcTemplate namedTemplate) {
    Assert.notNull(namedTemplate);
    this.namedTemplate = namedTemplate;
  }

  @Override
  public List<Image> getImages() {
    final String sql = "SELECT                                               "
                     + "	id, name, image_url, image_info, description, date "
                     + "FROM                                                 "
                     + "	image                                              "
        ;

    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    List<Image> images = namedTemplate.query(sql, paramsMap, (rs, rowNum) -> {
      Image image = new Image();
      image.setId(rs.getInt("id"));
      image.setName(rs.getString("name"));
      image.setImageURL(rs.getString("image_url"));
      image.setImageInfo(rs.getString("image_info"));
      image.setDescription(rs.getString("description"));
      image.setCreationDate(rs.getDate("date"));

      return image;
    });

    return images;
  }

}