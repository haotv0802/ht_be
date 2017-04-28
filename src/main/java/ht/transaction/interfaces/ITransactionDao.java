package ht.transaction.interfaces;

import ht.transaction.beans.TransactionCommit;

/**
 * Created by haho on 4/28/2017.
 */
public interface ITransactionDao {
  void createTransaction(TransactionCommit bean);

  void updateCommit(String authToken, boolean possibleToCommit);

  TransactionCommit getTransaction(String authToken);
}
