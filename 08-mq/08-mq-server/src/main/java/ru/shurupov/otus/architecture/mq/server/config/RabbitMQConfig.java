package ru.shurupov.otus.architecture.mq.server.config;

import java.util.List;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Bean
  public SimpleMessageConverter converter() {
    SimpleMessageConverter converter = new SimpleMessageConverter();
    converter.setAllowedListPatterns(
        List.of("ru.shurupov.*", "java.util.*")
    );
    return converter;
  }
}
