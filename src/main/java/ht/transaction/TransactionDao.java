package ht.transaction;

import ht.common.dao.DaoUtils;
import ht.transaction.beans.TransactionCommit;
import ht.transaction.interfaces.ITransactionDao;
import io.jsonwebtoken.lang.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by haho on 3/22/2017.
 */
@Repository("transactionDao")
public class TransactionDao implements ITransactionDao {
  private static final Logger LOGGER = LogManager.getLogger(TransactionDao.class);

  private final NamedParameterJdbcTemplate namedTemplate;

  @Autowired
  public TransactionDao(NamedParameterJdbcTemplate namedTemplate) {
    Assert.notNull(namedTemplate);
    this.namedTemplate = namedTemplate;
  }

  @Override
  public void createTransaction(TransactionCommit bean) {
    final String sql = "INSERT INTO tx_token (auth_token, possible_to_commit, exp_date)"
                     + " VALUES (:authToken, :possibleToCommit, :expDate)              "
        ;
    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();
    paramsMap.addValue("authToken", bean.getAuthToken());
    paramsMap.addValue("possibleToCommit", bean.getPossibleToCommit());
    paramsMap.addValue("expDate", bean.getExpDate());

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    namedTemplate.update(sql, paramsMap);
  }

  @Override
  public void updateCommit(String authToken, boolean possibleToCommit) {
    final String sql = "UPDATE tx_token set possible_to_commit = :possibleToCommit where auth_token = :authToken"
        ;
    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();
    paramsMap.addValue("authToken", authToken);
    paramsMap.addValue("possibleToCommit", possibleToCommit);

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    namedTemplate.update(sql, paramsMap);
  }

  @Override
  public TransactionCommit getTransaction(String authToken) {
    final String sql = "SELECT id, auth_token, possible_to_commit, exp_date FROM tx_token WHERE auth_token = :authToken";
    final MapSqlParameterSource paramsMap = new MapSqlParameterSource();
    paramsMap.addValue("authToken", authToken);

    DaoUtils.debugQuery(LOGGER, sql, paramsMap.getValues());

    TransactionCommit transaction = namedTemplate.queryForObject(sql, paramsMap, (RowMapper<TransactionCommit>) (rs, rowNum) -> {
      TransactionCommit bean = new TransactionCommit();
      bean.setId(rs.getInt("id"));
      bean.setAuthToken(rs.getString("auth_token"));
      bean.setPossibleToCommit(rs.getBoolean("possible_to_commit"));
      bean.setExpDate(rs.getDate("exp_date"));
      return bean;
    });

    return transaction;
  }
}