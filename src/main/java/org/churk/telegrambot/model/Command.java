package org.churk.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Command {
    START(List.of(".*/start\\b.*"), "Start the bot"),
    HELP(List.of(".*/help\\b.*"), "Get help"),
    REGISTER(List.of(".*/%sreg\\b.*"), "Register yourself as a %s"),
    DAILY_MESSAGE(List.of(".*/%s\\b.*"), "Get today's %s"),
    REELS(List.of(".*/reels\\b.*", ".*/reel\\b.*"), "Random reels video (use /reels [link] for specific reels video)"),
    NEWS(List.of(".*/news\\b.*"), "Get news (use /news [query] for specific news by query)"),

    STATS(List.of(".*/%sstats\\b.*"), "Get stats (use /%sstats [year] for specific year)"),
    STATS_ALL(List.of(".*/%sall\\b.*"), "Get all-time stats"),
    STATS_USER(List.of(".*/%sme\\b.*"), "Get personal stats"),

    FACT(List.of(".*/fact\\b.*"), "Random fact of the day"),
    FACT_ADD(List.of(".*/factadd\\b.*"), "Add fact of the day (use /factadd [fact] to add specific fact)"),

    STICKER(List.of(".*/sticker\\b.*"), "Random sticker from a %s sticker set"),
    STICKER_ADD(List.of(".*/stickeradd\\b.*"), "Add sticker to a %s sticker set (use /stickeradd [stickerName] or [link] for specific sticker)"),
    STICKER_REMOVE(List.of(".*/stickerremove\\b.*"), "Remove sticker from a %s sticker set (use /stickerremove [stickerId] for specific sticker)"),
    STICKER_LIST(List.of(".*/stickerlist\\b.*"), "Get list of stickers from a %s sticker set"),

    REDDIT(List.of(".*/reddit\\b.*", ".*/meme\\b.*"), "Random reddit picture (use /reddit [year] for specific subreddit)"),
    REDDIT_ADD(List.of(".*/redditadd\\b.*"), "Add subreddit to the list (use /redditadd [subreddit] or [link] for specific subreddit)"),
    REDDIT_REMOVE(List.of(".*/redditremove\\b.*"), "Remove subreddit from the list (use /redditremove [subreddit] for specific subreddit)"),
    REDDIT_LIST(List.of(".*/redditlist\\b.*"), "Get list of subreddits");


    private final List<String> patterns;
    private final String description;

    public static Command getTextCommand(String text, String botName) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getPatterns().stream()
                        .anyMatch(pattern -> text.matches(pattern.formatted(botName))))
                .findFirst()
                .orElse(null);
    }
}
