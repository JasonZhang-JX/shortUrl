package com.zjs.url.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.cmic.origin"})
public class ShortUrlApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShortUrlApiApplication.class, args);
  }
}
