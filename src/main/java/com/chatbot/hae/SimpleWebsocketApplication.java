package com.chatbot.hae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@CrossOrigin(origins="*")
@SpringBootApplication
public class SimpleWebsocketApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(SimpleWebsocketApplication.class, args);
	}


	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver mustacheViewResolver = new MustacheViewResolver();
		mustacheViewResolver.setPrefix("classpath:/templates/");
		mustacheViewResolver.setSuffix(".html");
		mustacheViewResolver.setCharset("UTF-8");
		mustacheViewResolver.setCache(true);
		mustacheViewResolver.setCacheLimit(1024);

		registry.viewResolver(mustacheViewResolver);
	}

	@Controller
	public static class MainController {
		@GetMapping("/")
		public String main() {
			return "redirect:/chat/bots";
		}
	}
}