package ht.transaction;

import ht.auth.AuthConstants;
import ht.common.JdbcUtils;
import ht.transaction.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.UUID;


@RestController
@RequestMapping("/svc/transactions")
public class TransactionResource {
  private Logger log = LogManager.getLogger(getClass());

  @Autowired
  @Qualifier("transactionsList")
  private TransactionsList transactions;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private ImxTransactionCommit imxTransactionCommit;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Transaction create(
      HttpSession httpSession,
      HttpServletRequest request
  ) throws SQLException {
    String txId = request.getHeader(AuthConstants.AUTH_HEADER_NAME);

//    String id = UUID.randomUUID().toString();
    TrackingConnectionWrapper connectionWrapper = new TrackingConnectionWrapper(dataSource.getConnection());
//    TrackingConnectionWrapper trackingConnection = ((ManagedDataSourceProxy) dataSource).getWrappedConnection();
    transactions.add(connectionWrapper, txId);

    log.debug(transactions.findTransaction(txId));

    final Transaction rsp = new Transaction();
    rsp.setTxId(txId);

    log.debug("Successfully created transaction with id '{}'", txId);

    //  Not use HttpSession for the moment
//    imxTransactionCommit.forbidCommit(httpSession);


    imxTransactionCommit.createCommit(txId);

    return rsp;
  }

  /**
   * Note this will END the managed transaction
   *
   * @param txId
   * @throws SQLException
   */
  @PutMapping()
  public void commit(
      @RequestHeader String txId,
      HttpSession httpSession) throws SQLException {
    TrackingConnectionWrapper conn = transactions.findTransaction(txId);
    log.debug("committing {}", txId);

    if (imxTransactionCommit.isCommitPermitted(txId)) {
      // don't catch as i want to send the error to the customer, so it can be aware of the error
      JdbcUtils.tryCommit(conn.getConnection());
    } else {
      throw new ValidationException("wrong.transaction.flow", new String[]{txId});
    }
    // closeTransaction(id);
    log.debug("done {}", txId);

  }

  /**
   * Note this will END the managed transaction
   *
   * @param txId
   * @throws SQLException
   */
  @DeleteMapping()
  public void rollback(@RequestHeader String txId, HttpSession httpSession) throws SQLException {
    TrackingConnectionWrapper conn = transactions.findTransaction(txId);
    // don't catch as i want to send the error to the customer, so it can be aware of the error
    JdbcUtils.tryRoll(conn.getConnection());

//    ((ManagedDataSourceProxy) dataSource).bindCurrentConnection(null);

    imxTransactionCommit.forbidCommit(txId);
    // closeTransaction(id);
  }

  /**
   * A do nothing keep alive ping request
   *
   * @param txId
   * @return
   */
  @RequestMapping(method = RequestMethod.HEAD)
  public void ping(@RequestHeader String txId) {
    TrackingConnectionWrapper conn = transactions.findTransaction(txId);
    conn.markAccessTime();
  }

}