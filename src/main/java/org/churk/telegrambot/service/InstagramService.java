package org.churk.telegrambot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.churk.telegrambot.client.InstagramClient;
import org.churk.telegrambot.config.DownloadMediaProperties;
import org.churk.telegrambot.model.instagram.InstagramPost;
import org.churk.telegrambot.model.instagram.Shortcode;
import org.churk.telegrambot.utility.FileDownloader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstagramService {
    private final DownloadMediaProperties instagramProperties;
    private final InstagramClient instagramClient;

    public Optional<File> getInstagramMedia(String postCode) throws feign.FeignException.NotFound {
        try {
            String jsonResponse = instagramClient.getVideoPostData(postCode);
            ObjectMapper mapper = new ObjectMapper();
            InstagramPost instagramPost = mapper.readValue(jsonResponse, InstagramPost.class);

            return getFile(instagramPost);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing instagram response", e);
            return Optional.empty();
        }
    }

    private Optional<File> getFile(InstagramPost instagramPost) {
        Shortcode shortcodeMedia = instagramPost.getGraphql().getShortcodeMedia();
        if (shortcodeMedia.isVideo() && shortcodeMedia.getVideoUrl() != null) {
            String apiUrl = shortcodeMedia.getVideoUrl();
            String extension = ".mp4";
            return FileDownloader.downloadAndCompressMedia(apiUrl, instagramProperties, extension);
        }
        return Optional.empty();
    }
}