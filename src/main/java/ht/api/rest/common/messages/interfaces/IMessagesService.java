package ht.api.rest.common.messages.interfaces;

import java.util.Map;

/**
 * Created by haho on 6/7/2017.
 */
public interface IMessagesService {
  Map<String, String> getAdminMessages(String lang);
}
