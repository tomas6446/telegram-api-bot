package org.churk.telegrambot.handler;

import org.churk.telegrambot.builder.MessageBuilderFactory;
import org.churk.telegrambot.config.BotProperties;
import org.churk.telegrambot.model.Command;
import org.churk.telegrambot.model.news.Article;
import org.churk.telegrambot.service.DailyMessageService;
import org.churk.telegrambot.service.NewsService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

import java.util.List;

@Component
public class NewsHandler extends Handler {
    private final NewsService newsService;

    public NewsHandler(BotProperties botProperties, DailyMessageService dailyMessageService, MessageBuilderFactory messageBuilderFactory, NewsService newsService) {
        super(botProperties, dailyMessageService, messageBuilderFactory);
        this.newsService = newsService;
    }

    @Override
    public List<Validable> handle(HandlerContext context) {
        Integer messageId = context.getUpdate().getMessage().getMessageId();
        Long chatId = context.getUpdate().getMessage().getChatId();
        List<String> args = context.getArgs();

        if (args.isEmpty()) {
            return getReplyMessage(chatId, messageId, "Please provide a query (use /news <query>");
        }

        String query = args.stream().reduce("", (a, b) -> a + " " + b);
        List<Article> articles = newsService.getNewsByCategory(query);

        if (articles.isEmpty()) {
            return getReplyMessage(chatId, messageId, "No news available");
        }
        String text = getNews(articles);
        return getMessage(chatId, text);
    }
    private static String getNews(List<Article> articles) {
        return articles.stream()
                .limit(3)
                .map(article -> article.getTitle() + "\n" + article.getUrl())
                .reduce("", (a, b) -> a + "\n\n" + b);
    }

    @Override
    public Command getSupportedCommand() {
        return Command.NEWS;
    }
}
