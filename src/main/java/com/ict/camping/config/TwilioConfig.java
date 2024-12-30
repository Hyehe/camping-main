package com.ict.camping.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfig {
  private String accountSid;
  private String authToken;

  private String phoneNumber;
}
