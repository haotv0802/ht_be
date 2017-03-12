package ht.auth;

import org.springframework.security.core.GrantedAuthority;

/**
 * Property of CODIX Bulgaria EAD
 * Created by vtodorov
 * Date:  22/03/2016 Time: 9:57 PM
 */
public class AuthorityImpl implements GrantedAuthority {
  final String authority;

  public AuthorityImpl(String authority) {
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return authority;
  }

  @Override
  public String toString() {
    return authority;
  }
}