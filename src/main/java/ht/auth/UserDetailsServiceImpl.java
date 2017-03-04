package ht.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("authService")
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  LoginDao loginDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    CustomizedUserDetails user = loginDao.findOneByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("username " + username + " not found");
    }
    CustomizedUserDetails userDetails = new CustomizedUserDetails("haho", "haho", null);
    return userDetails;
  }

}
