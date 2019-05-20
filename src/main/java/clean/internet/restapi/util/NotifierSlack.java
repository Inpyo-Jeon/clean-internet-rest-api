package clean.internet.restapi.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NotifierSlack {

    private RestTemplate restTemplate;
    private ObjectMapper mapper;
    private HttpHeaders httpHeaders;

    @Value("${common.notification.exception.webhook-url}")
    String webHookUrl;

    @Value("${common.notification.exception.channel-name}")
    String channel;

    @Value("${common.notification.exception.color}")
    String color;

    @Value("${spring.profiles.active}")
    String profile;

    public NotifierSlack(RestTemplate restTemplate, ObjectMapper mapper, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.httpHeaders = httpHeaders;
    }

    public class basicData {
        String username;
        String text;

        public basicData(String username, String text) {
            this.username = username;
            this.text = text;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public class attachmentData {
        @JsonProperty(value = "username")
        private String userName;

        public String getUsername() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @JsonProperty(value = "attachments")
        List<InnerData> attachments = new ArrayList<>();

        public InnerData createInnerData() {
            return new InnerData();
        }

        public class InnerData {
            @JsonProperty(value = "color")
            private String color;
            @JsonProperty(value = "pretext")
            private String preText;
            @JsonProperty(value = "text")
            private String text;
            @JsonProperty(value = "ts")
            private long ts;

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getPreText() {
                return preText;
            }

            public void setPreText(String preText) {
                this.preText = preText;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public long getTs() {
                return ts;
            }

            public void setTs(long ts) {
                this.ts = ts;
            }
        }
    }

    public void notifySend(String exception, String stackTrace) throws JsonProcessingException {
        attachmentData attachmentData = new attachmentData();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        NotifierSlack.attachmentData.InnerData innerData = attachmentData.createInnerData();
        innerData.setColor(color);
        innerData.setPreText(exception);
        innerData.setText(stackTrace);
        innerData.setTs(new Date().toInstant().getEpochSecond());

        attachmentData.attachments.add(innerData);
        attachmentData.setUserName("[" + profile.toUpperCase() + "] - RestAPI");

        String sendData = mapper.writeValueAsString(attachmentData);

        HttpEntity param = new HttpEntity(sendData, httpHeaders);

        restTemplate.postForObject(webHookUrl, param, String.class);
    }
}