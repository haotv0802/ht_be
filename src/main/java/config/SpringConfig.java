package config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

//Marks this class as configuration
@Configuration
//Enable API documentation
// Specifies which package to scan
@ComponentScan({ "codix" })
// Enables Spring's annotations
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class SpringConfig extends WebMvcConfigurerAdapter {

  private Logger log = LogManager.getLogger(getClass());

  private static final long sessionTimeoutInSec = 1800L;

  public SpringConfig() {
  }

  @Bean
  public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter()
  {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    jsonConverter.setObjectMapper(objectMapper);
    return jsonConverter;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
  {
    converters.add(customJackson2HttpMessageConverter());
  }

  @Bean(name = "dataSource")
  public DataSource dataSource() throws SQLException {

    final String databaseUrl = "jdbc:oracle:thin:@BULL.codixfr.private:22630/BULL";
    final String usr = "imxdb";
    final String pass = "manager";

    log.debug("databaseUrl=={}", databaseUrl);

    // JdbcDataSource ds = new JdbcDataSource();
    OracleDataSource ds = new OracleDataSource();
    ds.setURL(databaseUrl);
    ds.setUser(usr);
    ds.setPassword(pass);

    return ds;
//    return new ManagedDataSourceProxy(ds);
  }

  @Bean(name = "txManager")
  public PlatformTransactionManager txManager() throws SQLException {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean(name = "transactionTemplate")
  public TransactionTemplate transactionTemplate() throws SQLException {
  	return new TransactionTemplate(txManager());
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
    // configurer.favorPathExtension(true);
    // configurer.mediaType("html", MediaType.APPLICATION_JSON);
  }

 /*
 @Bean
  public GlobalExceptionHandler createGlobalExceptionHandler() {
    return new GlobalExceptionHandler(message);
  }
  */


  /* This doesn't play well, yet, with spring security, fallback to filter solution
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    //registry.addMapping("*//*").allowedOrigins("http://localhost:8383");
    registry.addMapping("*//**").allowCredentials(true).exposedHeaders("Location");
  }
   */

  @Override
  public void addInterceptors(InterceptorRegistry registry)
  {
    super.addInterceptors(registry);
  }


  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver createMultipartResolver() {
    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
    resolver.setDefaultEncoding("utf-8");
    return resolver;
  }

  /**
   * RestControllers argument injections
   * 1) Paging and Sorting arguments
   * 2) ImxLang arguments
   *
   * @param argumentResolvers list af argumentResolvers
   */
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
  {
    PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
    resolver.setOneIndexedParameters(true);
    resolver.setFallbackPageable(new PageRequest(0, 25));
    argumentResolvers.add(resolver);

    argumentResolvers.add(new SortHandlerMethodArgumentResolver());
//    argumentResolvers.add(new ImxLangHandlerMethodArgumentResolver());

    super.addArgumentResolvers(argumentResolvers);
  }

  @Configuration
  @EnableHazelcastHttpSession(maxInactiveIntervalInSeconds = (int)sessionTimeoutInSec, sessionMapName = "spring:session:sessions")
  protected static class SessionConfig
  {
    @Bean
    public HazelcastInstance embeddedHazelcast() {
      Config cfg = new Config();
      cfg.setProperty("hazelcast.logging.type","slf4j");
      final NetworkConfig netConfig = cfg.getNetworkConfig();
      netConfig.getJoin().getTcpIpConfig().setEnabled(false);
      netConfig.getJoin().getMulticastConfig().setEnabled(false);

      return Hazelcast.newHazelcastInstance(cfg);
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy(){
      return new HeaderHttpSessionStrategy();
    }

  }
  @Bean(name="jdbcTemplate")
  public JdbcTemplate jdbcTemplate()
    throws SQLException
  {
    return new JdbcTemplate(dataSource());
  }

  @Bean(name="namedTemplate")
  public NamedParameterJdbcTemplate namedTemplate()
    throws SQLException
  {
    return new NamedParameterJdbcTemplate(dataSource());
  }

}
