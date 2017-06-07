package ht.api.rest.common.messages;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by haho on 6/7/2017.
 */
@Service("messagesService")
public class MessagesService {
  void getAdminMessages() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames(
        "i18n.admin_messages"
    );
    messageSource.setUseCodeAsDefaultMessage(true);

//    ReloadableResourceBundleMessageSource messageSource1 = new ReloadableResourceBundleMessageSource();
//    messageSource1.setBasenames("i18n.admin_messages");
//    Properties properties = messageSource1.getMergedProperties
  }
}
