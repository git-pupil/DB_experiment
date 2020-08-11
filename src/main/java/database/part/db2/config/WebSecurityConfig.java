package database.part.db2.config;

import database.part.db2.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    
    //配置安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/teacher/**").hasRole("TEACHER")
                .antMatchers("/student/**").hasRole("STUDENT")
                .antMatchers("/manager/**").hasRole("MANAGER")
                .antMatchers("/myLogin").permitAll()
                .antMatchers("/logout").permitAll()
                //.anyRequest().authenticated()//其它地址都放行
                .and()
                .formLogin()
                .loginPage("/myLogin")//登录网页的URL
                .loginProcessingUrl("/login")//网页POST的URL
                .successForwardUrl("/loginSuccess")//登录成功后转向URL
                .failureForwardUrl("/loginFailure")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logoutSuccess");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("开始密码验证");
        auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
