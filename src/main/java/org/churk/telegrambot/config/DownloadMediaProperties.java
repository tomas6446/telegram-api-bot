package org.churk.telegrambot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("media")
public class DownloadMediaProperties {
    private String path;
    private String fileName;
    private String schedule;
    private String instagramToken;
    private String instagramUserId;
}
