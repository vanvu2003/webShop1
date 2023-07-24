package poly.vanvu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import poly.vanvu.filter.JwtAuthentionFilter;
import poly.vanvu.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserService userService;
	@Autowired
	CustomAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private JwtAuthentionFilter jwtAuthentionFilter;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService::loadUserByUsername);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().
		authorizeHttpRequests().
		antMatchers(
				"/register**","/home","/dien-thoai/**","/json/**","/forgetPassword","/reset_password**","/reset_password",
				"/otp-verification","/otp","/dtdd/**","/search/**",
				"/admin/js/**","/admin/vendor/**","/admin/css/**","/admin/assets/**",
				"/js/**","/css/**","/img/**","/upload/**","/api/**"
				).permitAll()
		.antMatchers("/api/addCart").authenticated()
		.antMatchers("/admin/**").hasAuthority("admin")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login").permitAll()
		.successHandler(authenticationSuccessHandler)
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/home")
		.permitAll();
//		.and()
//		.sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		.and()
//		.authenticationProvider(authenticationProvider)
//		.addFilterBefore(jwtAuthentionFilter, UsernamePasswordAuthenticationFilter.class);
		
		//Cấu hình đăng nhâp bằng OAuth2
//		http
//		.oauth2Login()
//			.loginPage("/login")
//			.defaultSuccessUrl("/oauth2/login/success")
//			.authorizationEndpoint()
//				.baseUri("/login/oauth2/authorization")
//				.authorizationRequestRepository(getRepository())
//			.and().tokenEndpoint()
//				.accessTokenResponseClient(getToken());
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository(){
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}
	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken(){
		return new DefaultAuthorizationCodeTokenResponseClient();
	}
}
