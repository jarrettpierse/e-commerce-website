package ie.ucd.le_barbz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  MyUserDetailsService MyUserDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.anonymous().and().cors().and().csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    http.authorizeRequests()
              .antMatchers("/admin/sign-up").permitAll()
              .antMatchers("/admin/add").permitAll()
              .antMatchers("/admin/**").hasAuthority("ADMIN")
              .antMatchers("/account/add").permitAll()
              .antMatchers("/account/**").hasAnyAuthority("ADMIN","USER")
              .antMatchers("/").permitAll()
              .antMatchers("/checkout").hasAnyAuthority("ADMIN","USER")
              .and().httpBasic().and().csrf().disable().formLogin().loginPage("/login").permitAll().successHandler(authenticationSuccessHandler())
              .and().logout().logoutSuccessUrl("/login?logout");
  }

  @Bean
  public MyAuthenticationSuccessHandler authenticationSuccessHandler() {
    return new MyAuthenticationSuccessHandler();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(MyUserDetailsService).passwordEncoder(getPasswordEncoder());
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
