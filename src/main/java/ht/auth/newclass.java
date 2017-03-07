//package ht.auth;
//
//import io.jsonwebtoken.lang.Assert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
///**
// * Created by haho on 3/7/2017.
// */
//@Api(value = "/core/groups", description = "The Core Group API")
//@Path("/core/groups")
//public class GroupsCoreEndpoint extends AbstractEndpoint<GroupsCoreEndpointDefinition> {
//
//  // TODO Global variables definition section.
//  // Choose the scoop / naming / ...
//  private static final String STATUS_MESSAGE_OK = "OK";
//  private static final String STATUS_MESSAGE_NO_CONTENT = "NO_CONTENT";
//  private static final String STATUS_MESSAGE_CREATED = "CREATED";
//
//
//  private final GroupService groupService;
//
//  @Autowired
//  protected GroupsCoreEndpoint(GroupsCoreEndpointDefinition endpointDefinition, GroupService groupService) {
//    // Make sure that they are not null;
//    Assert.notNull(endpointDefinition);
//    Assert.notNull(groupService);
//    super(endpointDefinition);
//    this.groupService = groupService;
//  }
//
//  @POST
//  @Path("/createGroup")
//  @Consumes({ MediaType.APPLICATION_JSON })
//  @Produces({ MediaType.APPLICATION_JSON })
//  @ApiOperation(value = "Create a Group", notes = "Post a new Group")
//  @ApiResponses(value = {
//      @ApiResponse(code = 200, message = STATUS_MESSAGE_OK, response = Group.class)
//
//  })
//  /**
//   * Create a {@link Group}.
//   * The {@link Group} has to be validated before his creation.
//   * In case of issue (service call, invalid group) a {@link Response.Status.BAD_REQUEST} has to be send back as {@link Response}.
//   * In case of success, the {@link Group} has to be inserted to the {@link Response}.
//   */
//  public Response createGroup(Group group) {
//    Response response = null;
//    Assert.notNull(group); // make sure that group is not null
//    groupService.validateGroup(group);
//    if (!groupService.checkIfGroupExisting(group)) {
//      groupService.createGroup(group);
//      response = new Response(STATUS_MESSAGE_CREATED);
//    } else {
//      response = new Response(STATUS_MESSAGE_NO_CONTENT);;  // Group is existing
//    }
//    return Response;
//  }
//
//}
///**
// * Basic group service definition.
// */
//public interface GroupService {
//
//  // TODO Add the service contract methods
//  Boolean validateGroup(Group group);
//  Boolean checkIfGroupExisting(Group group);
//  void createGroup(Group group);
//}
//
