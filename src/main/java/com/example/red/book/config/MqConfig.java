package com.example.red.book.config;


import com.example.red.book.constant.NoteConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Bean
    @ConditionalOnSingleCandidate(ConnectionFactory.class)
    @ConditionalOnMissingBean({RabbitOperations.class})
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate();
        configurer.configure(template, connectionFactory);
        return template;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(NoteConstant.EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue insertQueue() {
        return new Queue(NoteConstant.INSERT_QUEUE_NAME, true);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue(NoteConstant.DELETE_QUEUE_NAME, true);
    }

    @Bean
    public Binding insertQueueBinding() {
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(NoteConstant.INSERT_KEY);
    }

    @Bean
    public Binding deleteQueueBinding() {
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(NoteConstant.DELETE_KEY);
    }
}
