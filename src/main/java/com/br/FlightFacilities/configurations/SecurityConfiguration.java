package com.br.FlightFacilities.configurations;

import com.br.FlightFacilities.security.FiltroAutenticacaoJWT;
import com.br.FlightFacilities.security.FiltroAutorizacaoJWT;
import com.br.FlightFacilities.security.JWTUtil;
import com.br.FlightFacilities.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTUtil jwtUtil;

    /*
    COLOCAR ENDPOINTS LIBERADOS NOS ARRAYS ABAIXO
     */
    private static final String[] PUBLIC_MATCHERS_POST = {
            "/usuarios",
            "/passagens"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/empresas/**",
            "/voos/**"
    };

    private static final String[] PUBLIC_MATCHERS_PUT = {
            "/simulacao",
    };

    private static final String[] PUBLIC_MATCHERS_DELETE = {
            "/passagem/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .antMatchers(HttpMethod.PUT, PUBLIC_MATCHERS_PUT).permitAll()
                .antMatchers(HttpMethod.DELETE, PUBLIC_MATCHERS_DELETE).permitAll()
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(new FiltroAutenticacaoJWT(jwtUtil, authenticationManager()));
        http.addFilter(new FiltroAutorizacaoJWT(authenticationManager(), usuarioService, jwtUtil));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
