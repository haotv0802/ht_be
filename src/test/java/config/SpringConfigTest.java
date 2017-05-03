package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class SpringConfigTest
{

  @Autowired
  private NamedParameterJdbcTemplate namedTemplate;

  @Bean(name = "tstMsgSource")
  public MessageSource messageSource()
  {
    ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasenames(
      "i18n.LoginResource"
    );
    ms.setUseCodeAsDefaultMessage(true);
    return ms;
  }

  /*
  @Bean
  public Map<String, String> imxInstanceParam() throws SQLException
  {
    return new ImxTestInstanceParametersDao(this.namedTemplate).getImxInstanceParameters();
  }
  */
}