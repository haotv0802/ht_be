package ht.api.rest.common.messages;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

/**
 * Created by haho on 6/7/2017.
 */
@Service("messagesService")
public class MessagesService {

  void getAdminMessages(String lang) {
    ResourceBundle props = ResourceBundle.getBundle(
        "i18n.admin_messages" + (StringUtils.isEmpty(lang) ? "" : "_" + lang.toLowerCase())
    );

    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames(
        "i18n.admin_messages"
    );
    messageSource.setUseCodeAsDefaultMessage(true);
  }
}