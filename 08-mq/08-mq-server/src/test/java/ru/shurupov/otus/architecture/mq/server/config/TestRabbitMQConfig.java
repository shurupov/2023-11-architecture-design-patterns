package ru.shurupov.otus.architecture.mq.server.config;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.List;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class TestRabbitMQConfig {

  @Bean
  public TestRabbitTemplate template() throws IOException {
    return new TestRabbitTemplate(connectionFactory());
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactory factory = mock(ConnectionFactory.class);
    Connection connection = mock(Connection.class);
    Channel channel = mock(Channel.class);
    willReturn(connection).given(factory).createConnection();
    willReturn(channel).given(connection).createChannel(anyBoolean());
    given(channel.isOpen()).willReturn(true);
    return factory;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(MessageConverter converter) throws IOException {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setMessageConverter(converter);
    return factory;
  }

  /*@Bean
  public SimpleMessageConverter converter() {
    SimpleMessageConverter converter = new SimpleMessageConverter();
    converter.setAllowedListPatterns(
        List.of("ru.shurupov.*", "java.util.*")
    );
    return converter;
  }*/
}
