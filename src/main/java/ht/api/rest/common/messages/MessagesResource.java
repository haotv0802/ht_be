package ht.api.rest.common.messages;

import ht.api.rest.common.BaseResource;
import ht.auth.UserDetailsImpl;
import ht.common.beans.HeaderLang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haho on 6/7/2017.
 */
@RestController
public class MessagesResource extends BaseResource {

  @Autowired
  @Qualifier("messagesService")
  private MessagesService messagesService;

  @GetMapping("/messages")
  @PreAuthorize("hasAuthority('ADMIN')")
  public void getMessages(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @HeaderLang String lang
  ) {
    messagesService.getAdminMessages();
  }

}
