package ht.api.rest.admin.individuals;

import ht.api.rest.admin.BaseAdminResource;
import ht.api.rest.admin.individuals.interfaces.IIndividualService;
import ht.api.rest.admin.rooms.beans.RoomTypeBean;
import ht.api.rest.admin.rooms.interfaces.IRoomService;
import ht.auth.UserDetailsImpl;
import ht.common.beans.HeaderLang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haho on 3/22/2017.
 */
@RestController
public class IndividualsResource extends BaseAdminResource {

  private final IIndividualService individualService;

  @Autowired
  public IndividualsResource(@Qualifier("adminIndividualService") IIndividualService individualService) {
    Assert.notNull(individualService);
    this.individualService = individualService;
  }

  @GetMapping("/individuals")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<IndividualPresenter> getIndividuals(
       @AuthenticationPrincipal UserDetailsImpl userDetails
      ,@HeaderLang String lang) {
    return this.individualService.getIndividuals();
  }
}
