package com.mthxz.transaction_service.config;

import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.RecordInterceptor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:transaction-service}")
    private String consumerGroupId;

    public static final String TOPIC_TRANSACAO_SOLICITADA = "TransacaoSolicitada";
    public static final String TOPIC_TRANSACAO_CONCLUIDA = "TransacaoConcluida";

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
    public ConsumerFactory<String, ExecucaoTransacaoModel> consumerFactory() {
    JsonDeserializer<ExecucaoTransacaoModel> jsonDeserializer = new JsonDeserializer<>(ExecucaoTransacaoModel.class);
    // Trust bankcommons models for safe JSON deserialization
    jsonDeserializer.addTrustedPackages("com.mthxz.bankcommons.model", "com.mthxz.bankcommons");
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, jsonDeserializer);
        // Ensure group.id is explicitly set since we define a custom ConsumerFactory
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ExecucaoTransacaoModel> execucaoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ExecucaoTransacaoModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // Add a fixed delay before each record is sent to listener
        factory.setRecordInterceptor(
            (RecordInterceptor<String, ExecucaoTransacaoModel>) (record, consumer) -> {
                try {
                    Thread.sleep(2000); // 2 seconds delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // proceed without modification
                return record;
            }
        );
        return factory;
    }

    @Bean
    public NewTopic topicSolicitada() {
        return new NewTopic(TOPIC_TRANSACAO_SOLICITADA, 1, (short) 1);
    }

    @Bean
    public NewTopic topicConcluida() {
        return new NewTopic(TOPIC_TRANSACAO_CONCLUIDA, 1, (short) 1);
    }
}
