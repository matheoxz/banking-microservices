package com.mthxz.notification_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public static final String TOPIC_TRANSACAO_SOLICITADA = "TransacaoSolicitada";
    public static final String TOPIC_TRANSACAO_CONCLUIDA  = "TransacaoConcluida";

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        JsonDeserializer<String> jsonDeserializer = new JsonDeserializer<>(String.class);
        jsonDeserializer.addTrustedPackages("*");
        Map<String, Object> configs = new HashMap<>();
        configs.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, jsonDeserializer);
        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // Generic consumer factory for Object payloads
    @Bean
    public ConsumerFactory<String, Object> genericConsumerFactory() {
        JsonDeserializer<Object> deserializer = new JsonDeserializer<>(Object.class);
        // Trust bank commons models and any other needed packages
        deserializer.addTrustedPackages("com.mthxz.bankcommons.model", "com.mthxz.bankcommons");
        Map<String, Object> configsAttrs = new HashMap<>();
        configsAttrs.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configsAttrs.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configsAttrs.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        // Use same group for audit service
        configsAttrs.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "audit-service");
        return new DefaultKafkaConsumerFactory<>(configsAttrs, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> genericListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(genericConsumerFactory());
        return factory;
    }

    @Bean
    public NewTopic transacaoSolicitadaTopic() {
        return new NewTopic(TOPIC_TRANSACAO_SOLICITADA, 1, (short) 1);
    }

    @Bean
    public NewTopic transacaoConcluidaTopic() {
        return new NewTopic(TOPIC_TRANSACAO_CONCLUIDA, 1, (short) 1);
    }

}

