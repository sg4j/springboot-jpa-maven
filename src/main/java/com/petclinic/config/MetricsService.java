package com.petclinic.config;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MessageChannelMetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MetricsService {

	@Bean
	public MessageChannel metricsChannel() {
	    return new DirectChannel();
	}

	@Bean
	@ExportMetricWriter
	public MessageChannelMetricWriter messageChannelMetricWriter() {
	    return new MessageChannelMetricWriter(metricsChannel());
	}

/*	@ServiceActivator(inputChannel = "metricsChannel")
    public void handleMessage(org.springframework.messaging.Message<?> message) throws org.springframework.messaging.MessagingException {
        for (String headerKey : message.getHeaders().keySet()) {
            System.out.println("Header: "+headerKey+"="+message.getHeaders().get(headerKey));
        }
        System.out.println("Payload: "+message.getPayload());
    }*/
}