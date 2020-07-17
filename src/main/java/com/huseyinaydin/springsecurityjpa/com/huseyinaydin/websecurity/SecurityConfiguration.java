package com.huseyinaydin.springsecurityjpa.com.huseyinaydin.websecurity;

import com.huseyinaydin.springsecurityjpa.com.huseyinaydin.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER","ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin();
        /*ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry e = http.authorizeRequests();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl1 = e.antMatchers("/admin");
        authorizedUrl1.hasRole("ADMIN");
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl2 = e.antMatchers("/user");
        authorizedUrl2.hasAnyRole("USER","ADMIN");
        e.and().formLogin();*/

        /*MySimpleUrlAuthenticationSuccessHandler handler = applicationContext.getBean("myAuthenticationSuccessHandler",MySimpleUrlAuthenticationSuccessHandler.class);
        MyLogoutSuccessHandler handler2 = applicationContext.getBean("myLogoutSuccessHandler",MyLogoutSuccessHandler.class);
        SessionRegistry sessionRegistry = applicationContext.getBean("sessionRegistry",SessionRegistry.class);
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER","ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin().successHandler(handler)
                .and().logout().logoutSuccessHandler(handler2)
                .and().formLogin().and().rememberMe();*/
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public MyUserDetailsService getMyUserDetailsService() {
        return myUserDetailsService;
    }

    public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }
}
