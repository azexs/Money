package com.example.Currency.base.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    @Value("${amqp.connection.username:guest}")
    private String amqpConnectionUsername;

    @Value("${amqp.connection.password:guest")
    private String amqpConnectionPassword;

    @Value("${amqp.connection.host:host}")
    private String amqpConnectionHost;

    @Value("${currency.queue.name:queue}")
    private String currencyQueueName;

    @Value("${currency.exchange.name:exchange}")
    private String currencyExchangeName;

    @Bean(name = "amqpConnectionFactory")
    public CachingConnectionFactory amqpConnectionFactory() throws Exception {
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
        factory.setUsername(amqpConnectionUsername);
        factory.setPassword(amqpConnectionPassword);
        factory.setHost(amqpConnectionHost);
        factory.afterPropertiesSet();

        return new CachingConnectionFactory(factory.getObject());
    }

    @Bean
    RabbitTemplate rabbitTemplate() throws Exception {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(amqpConnectionFactory());
        rabbitTemplate.setExchange(currencyExchangeName);
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin(@Qualifier("amqpConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queue() {
        return new Queue(currencyQueueName);
    }

    @Bean
    public Binding binding() {
        Map<String, Object> map = new HashMap<>();
        map.put("headerName", "headerValue");
        return new Binding(currencyQueueName,
                Binding.DestinationType.QUEUE,
                currencyExchangeName, "", map);
    }

}






