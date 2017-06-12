package ht.api.rest.common.messages;

import ht.api.rest.common.messages.interfaces.IMessagesDao;
import ht.api.rest.common.messages.interfaces.IMessagesService;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * Created by haho on 6/7/2017.
 */
@Service("messagesService")
public class MessagesService implements IMessagesService {

  private final IMessagesDao messagesDao;

  public MessagesService(@Qualifier("adminMessagesDao") IMessagesDao messagesDao) {
    Assert.notNull(messagesDao);

    this.messagesDao = messagesDao;
  }

  public Map<String, String> getAdminMessages(String lang) {
    ResourceBundle props = ResourceBundle.getBundle(
        "i18n.admin_messages" + (StringUtils.isEmpty(lang) ? "" : "_" + lang.toLowerCase())
    );

    Map<String, String> messages = new TreeMap<String, String>();

    Enumeration propsKeys = props.getKeys();
    while (propsKeys.hasMoreElements()) {
      String key = (String) propsKeys.nextElement();
      messages.put(key, String.valueOf(props.getObject(key)));
    }

    return messages;
//    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//    messageSource.setBasenames(
//        "i18n.admin_messages"
//    );
//    messageSource.setUseCodeAsDefaultMessage(true);
  }

  @Override
  public Map<String, Map<String, String>> getAdminMessages(String lang, String name) {
    return this.messagesDao.getMessages(lang);
  }
}