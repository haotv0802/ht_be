package ht.transaction;

import javax.servlet.http.HttpSession;

/**
 * Property of CODIX Bulgaria EAD
 * Created by zalexiev
 * Date:  21/12/2016  Time: 10:50
 */
public interface ImxTransactionCommit {

  String commitAttribute = "isPossibleToCommit";

  void createCommit(String authToken);

  void permitCommit();

  void forbidCommit();

  boolean isCommitPermitted();

  void permitCommit(HttpSession session);

  void permitCommit(String authToken);

  void forbidCommit(HttpSession session);

  void forbidCommit(String authToken);

  boolean isCommitPermitted(HttpSession session);

  boolean isCommitPermitted(String authToken);
}
