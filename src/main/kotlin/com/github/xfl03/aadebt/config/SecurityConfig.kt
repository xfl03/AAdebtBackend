package com.github.xfl03.aadebt.config

import com.github.xfl03.aadebt.component.AuthTokenFilter
import com.github.xfl03.aadebt.service.AuthUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.web.builders.WebSecurity




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var userService: AuthUserDetailService

    @Autowired
    private lateinit var filter: AuthTokenFilter

    @Autowired
    fun configureAuthentication(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
                .userDetailsService<AuthUserDetailService>(this.userService)
                .passwordEncoder(BCryptPasswordEncoder())
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.woff",
                        "/**/*.ttf"
                ).permitAll()
                .antMatchers("/api/info").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()

        httpSecurity.headers().cacheControl()

        httpSecurity
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)

    }

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/js/**","/fonts/**","/css/**")
    }
}