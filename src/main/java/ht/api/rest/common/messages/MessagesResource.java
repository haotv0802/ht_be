package ht.api.rest.common.messages;

import ht.api.rest.admin.BaseAdminResource;
import ht.api.rest.common.BaseResource;
import ht.auth.UserDetailsImpl;
import ht.common.beans.HeaderLang;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by haho on 6/7/2017.
 */
@RestController
public class MessagesResource extends BaseResource {

  private final Logger logger = LogManager.getLogger(getClass());

  @Autowired
  @Qualifier("messagesService")
  private MessagesService messagesService;

  @GetMapping("/messages")
  @PreAuthorize("hasAuthority('ADMIN')")
  public Map<String, String> getMessages(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @HeaderLang String lang
  ) {
    Map<String, String> maps = messagesService.getAdminMessages(lang);
    logger.info("maps: " + maps);
    return maps;
  }

  @GetMapping("/{name}/messages")
  @PreAuthorize("hasAuthority('ADMIN')")
  public Map<String, Map<String, String>> getMessagesByPageName(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @HeaderLang String lang,
      @PathVariable(value = "name") String name
  ) {
    return this.messagesService.getAdminMessages(lang, "");
  }
}
