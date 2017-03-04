package config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.mysql.cj.jdbc.MysqlDataSource;
import ht.auth.LoggingEnhancingFilter;
import ht.auth.filters.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

//Marks this class as configuration
@Configuration
//Enable API documentation
// Specifies which package to scan
@ComponentScan({"ht"})
// Enables Spring's annotations
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringConfig extends WebMvcConfigurerAdapter {

  private Logger log = LogManager.getLogger(getClass());

  private static final long sessionTimeoutInSec = 1800L;

  public SpringConfig() {
  }

  @Bean
  public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    jsonConverter.setObjectMapper(objectMapper);
    return jsonConverter;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(customJackson2HttpMessageConverter());
  }

  @Bean(name = "dataSource")
  public DataSource dataSource() throws SQLException {

//    final String databaseUrl = "jdbc:oracle:thin:@BULL.codixfr.private:22630/BULL";
//    final String usr = "imxdb";
//    final String pass = "manager";

    final String databaseUrl = "jdbc:mysql://localhost:3306/security_db";
    final String usr = "haho";
    final String pass = "hoanhhao";

    log.debug("databaseUrl=={}", databaseUrl);

//    BasicDataSource dataSource = new BasicDataSource();
//
//    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//    dataSource.setUsername("username");
//    dataSource.setPassword("password");
//    dataSource.setUrl("jdbc:mysql://<host>:<port>/<database>");
//    dataSource.setMaxActive(10);
//    dataSource.setMaxIdle(5);
//    dataSource.setInitialSize(5);
//    dataSource.setValidationQuery("SELECT 1");

//     JdbcDataSource ds = new JdbcDataSource();
    MysqlDataSource ds = new MysqlDataSource();
//    OracleDataSource ds = new OracleDataSource();
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
    registry.addMapping("*/

  /**
   * ").allowCredentials(true).exposedHeaders("Location");
   * }
   */

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    super.addInterceptors(registry);
  }


  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver createMultipartResolver() {
    CommonsMultipartResolver resolver = new CommonsMultipartResolver();
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
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
    resolver.setOneIndexedParameters(true);
    resolver.setFallbackPageable(new PageRequest(0, 25));
    argumentResolvers.add(resolver);

    argumentResolvers.add(new SortHandlerMethodArgumentResolver());
//    argumentResolvers.add(new ImxLangHandlerMethodArgumentResolver());

    super.addArgumentResolvers(argumentResolvers);
  }

  @Configuration
  @EnableHazelcastHttpSession(maxInactiveIntervalInSeconds = (int) sessionTimeoutInSec, sessionMapName = "spring:session:sessions")
  protected static class SessionConfig {
    @Bean
    public HazelcastInstance embeddedHazelcast() {
      Config cfg = new Config();
      cfg.setProperty("hazelcast.logging.type", "slf4j");
      final NetworkConfig netConfig = cfg.getNetworkConfig();
      netConfig.getJoin().getTcpIpConfig().setEnabled(false);
      netConfig.getJoin().getMulticastConfig().setEnabled(false);

      return Hazelcast.newHazelcastInstance(cfg);
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
      return new HeaderHttpSessionStrategy();
    }

  }

  @Bean(name = "jdbcTemplate")
  public JdbcTemplate jdbcTemplate()
      throws SQLException {
    return new JdbcTemplate(dataSource());
  }

  @Bean(name = "namedTemplate")
  public NamedParameterJdbcTemplate namedTemplate()
      throws SQLException {
    return new NamedParameterJdbcTemplate(dataSource());
  }

  @Configuration
  @EnableWebSecurity
  @EnableGlobalMethodSecurity(prePostEnabled = true)
  protected static class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    PlatformTransactionManager txManager;

    @Resource(name = "authService")
    private UserDetailsService userDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private CustomizedAccessDeniedHandler customizedAccessDeniedHandler;

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
      ResourceBundleMessageSource b = new ResourceBundleMessageSource();
      b.setBasenames(
          "i18n.LoginResource"
      );
      b.setUseCodeAsDefaultMessage(true);
      return b;
    }

    @Bean
    public CustomizedAuthenticationFailureHandler customizedAuthenticationFailureHandler() {
      return new CustomizedAuthenticationFailureHandler(messageSource());
    }

    @Bean
    public CustomizedAuthenticationSuccessHandler customizedAuthenticationSuccessHandler() {
      return new CustomizedAuthenticationSuccessHandler();
    }

    @Override
    protected UserDetailsService userDetailsService() {
      return userDetailsService;
    }

    @Bean
    public CustomizedAccessDeniedHandler customizedAccessDeniedHandler() {
      return new CustomizedAccessDeniedHandler(messageSource());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
          .maximumSessions(10)
          .and()
          .and()
          .exceptionHandling()
          .authenticationEntryPoint(restAuthenticationEntryPoint)
          .accessDeniedHandler(customizedAccessDeniedHandler)
          .and()
          .csrf().disable()
          .authorizeRequests()
          //allow anonymous POSTs to login
          .antMatchers(HttpMethod.POST, "/login").permitAll()
          //all other request need to be authenticated
          .antMatchers("/svc/**").authenticated()
          .and()
          .logout()
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.POST.name()))
          .invalidateHttpSession(true)
          .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
          .and()
          // custom CORS filter as the mvc cors config doesn't play well, yet, with security
          .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
          // custom JSON based authentication by POST of {"userName":"<name>","userPass":"<password>"}
          .addFilterBefore(statelessLoginFilter(), UsernamePasswordAuthenticationFilter.class)
          .addFilterAfter(loggingEnhancingFilter(), FilterSecurityInterceptor.class)
      ;
    }

    @Bean
    public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true); // you USUALLY want this
      config.addAllowedOrigin("*");
      config.addAllowedHeader("*");
      config.addAllowedMethod("GET");
      config.addAllowedMethod("HEAD");
      config.addAllowedMethod("POST");
      config.addAllowedMethod("DELETE");
      config.addAllowedMethod("PATCH");
      config.addAllowedMethod("PUT");

      config.addExposedHeader("Location");
      config.addExposedHeader("X-AUTH-TOKEN");
      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
    }

    @Bean
    LoggingEnhancingFilter loggingEnhancingFilter() {
      return new LoggingEnhancingFilter();
    }

    @Bean
    StatelessLoginFilter statelessLoginFilter() throws Exception {
      return new StatelessLoginFilter(
          "/login"
          , authenticationManagerBean()
          , customizedAuthenticationFailureHandler()
          , customizedAuthenticationSuccessHandler());
    }

  }
}
