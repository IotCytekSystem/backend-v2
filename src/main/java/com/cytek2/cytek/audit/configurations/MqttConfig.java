package com.cytek2.cytek.audit.configurations;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.eclipse.paho.client.mqttv3.MqttClient;

@Configuration
public class MqttConfig {

    @Value("${mqtt.brokerUrl}")
    private String brokerUrl;

    @Value("${mqtt.clientID}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Bean
    public MqttClient mqttClient() {
        try {
            MqttClient mqttClient = new MqttClient(brokerUrl, clientId);

            // Create MQTT connection options and set username and password
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setAutomaticReconnect(true);
            options.setConnectionTimeout(10);
            options.setAutomaticReconnect(true);


            // Set the connection options for the MQTT client
            mqttClient.connect(options);

            return mqttClient;
        } catch (Exception e) {
            throw new RuntimeException("Error creating MQTT client", e);
        }
    }
}
