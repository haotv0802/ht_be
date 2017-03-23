package ht.api.rest.admin.rooms;

import ht.api.rest.admin.BaseAdminResource;
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
public class RoomsResource extends BaseAdminResource {

  private final IRoomService roomService;

  @Autowired
  public RoomsResource(@Qualifier("adminRoomService") IRoomService roomService) {
    Assert.notNull(roomService);
    this.roomService = roomService;
  }

  @GetMapping("/rooms/roomTypes")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<RoomTypeBean> getUsers(
      @AuthenticationPrincipal UserDetailsImpl userDetails
      ,@HeaderLang String lang) {
    return roomService.getRoomTypes();
  }
}
