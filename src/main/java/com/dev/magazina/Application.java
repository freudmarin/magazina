
package com.dev.magazina;

import com.dev.magazina.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;
import java.util.Map;

@SpringBootApplication
@Controller
public class Application implements WebMvcConfigurer {

	@GetMapping("/")
	public String home(Map<String, Object> model) {
		model.put("message", "Hello World");
		model.put("title", "Hello Home");
		model.put("date", new Date());
		return "home";
	}

	@RequestMapping("/foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).run(args);
	}

	@Configuration
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					.anyRequest()
					.fullyAuthenticated().and().formLogin()
					.loginPage("/login")
					.successHandler(loginSuccessHandler())
					.failureHandler(loginFailureHandler())
					.permitAll().and()
					.logout().permitAll().logoutSuccessUrl("/login")
					.and()
					.csrf();
		}


		private AuthenticationFailureHandler loginFailureHandler() {
			return ((request, response, exception) -> {
				request.getSession().setAttribute("error", "Username ose password i gabuar!");
				response.sendRedirect("/login");
			});
		}

		private AuthenticationSuccessHandler loginSuccessHandler() {
			return ((request, response, authentication) -> response.sendRedirect("/"));
		}


		@Autowired
		private UserService userService;

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder(10);
		}

		@Bean
		public EvaluationContextExtension securityExtension() {
			return new EvaluationContextExtension() {
				@Override
				public String getExtensionId() {
					return "security";
				}

				@Override
				public Object getRootObject() {
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					return new SecurityExpressionRoot(authentication) {};
				}
			};
		}
	}

}
