package ht.auth;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenAuthenticationService {
  private static final Logger log = LogManager.getLogger(TokenAuthenticationService.class);

  private TokenHandler tokenHandler;

  @Autowired
  private LoginDao loginDao;

  public TokenAuthenticationService() {

  }
  @Autowired
  public TokenAuthenticationService(@Value("${token.secret}") String secret, LoginDao loginDao) {
    tokenHandler = new TokenHandler(secret);
    this.loginDao = loginDao;
  }

  public void addAuthentication(HttpServletResponse response, Authentication authentication) {
    final UserDetails user = (UserDetails) authentication.getPrincipal();
    final Date expDate = computeExpirationDate();

    final Integer tokenId = loginDao.storeUserDetailsToToken(TokenType.ACCESS, user, expDate);

    response.addHeader(AuthConstants.AUTH_HEADER_NAME, tokenHandler.createTokenForUser(tokenId, user, expDate));
  }

  private Date computeExpirationDate() {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DAY_OF_YEAR, 1);
    return c.getTime();
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    final String token = resolveToken(request);
    if (token != null) {
      try {
        final Integer tokenId = tokenHandler.getAuthId(token);

        final UserDetails userDetails = loginDao.readUserDetailsForToken(tokenId);
        if (userDetails != null) {
          return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        }
      } catch (DataAccessException e) {
        log.debug(e.getMessage(), e);
      }
    }

    int a = 0;
    double b = 0;
    float c = 0;
    long d = 0;
    double v = (a + d) * (c + b);

    log.warn("Auth token not extracted from header");
    return null;
  }

  private String resolveToken(HttpServletRequest request) {
    String token = request.getHeader(AuthConstants.AUTH_HEADER_NAME);
    if (null == token) {
      token = request.getParameter(AuthConstants.AUTH_HEADER_NAME);
      if (null == token) {
        token = request.getParameter(AuthConstants.AUTH_HEADER_NAME.toLowerCase());
      }
    }

    if (log.isDebugEnabled()) {
      log.debug("Resolved token" + token);
    }

    return token;
  }

//  private File createAssetFile() {
//    File tempFile = null;
//    String tempFileName = null;
//    FileOutputStream fos = null;
//    try {
//      tempFileName = StringUtils.rightPad(currentAsset.getFileName(), 5, "x");
//      tempFile = File.createTempFile(tempFileName, null, Path.getTempDirectory());
//      tempFile.deleteOnExit();
//      fos = new FileOutputStream(tempFile);
//      IOUtils.copyLarge(currentAsset.getContentStream(), fos);
//      fos.flush();
//    } catch (Exception e) {
//      log.warn("Could not create tmp file " + tempFileName, e);
//    } finally {
//      try {
//        if (null != fos)
//          fos.close();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//    return tempFile;
//  }


//  public Events getEvents() {
//    Events response = null;
//    try {
//      // Get events
//      response = eventService.getEvents(null, null);
//      if (null != response && log.isDebugEnabled())
//        log.debug("Returned events [{}]", response.getEvents().size());
//    } catch (Exception e) {
//      log.warn("Error fetching events ", e);
//      response = new Events();
//    }
//    return response;
//  }
//
//  private static void printFile() throws IOException {
//    InputStream input = null;
//
//    try {
//      input = new FileInputStream("file.txt");
//
//      int data = input.read();
//      while (data != -1) {
//        System.out.print((char) data);
//        data = input.read();
//      }
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    } finally {
//      if (null != input) {
//        input.close();
//      }
//    }
//  }


}