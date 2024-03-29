package org.churk.telegrambot.handler.game;

import lombok.RequiredArgsConstructor;
import org.churk.telegrambot.handler.Command;
import org.churk.telegrambot.handler.Handler;
import org.churk.telegrambot.handler.game.stats.Stat;
import org.churk.telegrambot.handler.game.stats.StatsService;
import org.churk.telegrambot.utility.HandlerContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisterHandler extends Handler {
    private final StatsService statsService;

    @Override
    public List<Validable> handle(HandlerContext context) {
        Long chatId = context.getUpdate().getMessage().getChatId();
        Long userId = context.getUpdate().getMessage().getFrom().getId();
        Integer messageId = context.getUpdate().getMessage().getMessageId();
        String firstName = context.getUpdate().getMessage().getFrom().getFirstName();

        return getRegister(chatId, userId, firstName, messageId);
    }

    private List<Validable> getRegister(Long chatId, Long userId, String firstName, Integer messageId) {
        List<Stat> userStats = statsService.getStatsByChatIdAndUserId(chatId, userId);
        if (!userStats.isEmpty()) {
            return getReplyMessage(chatId, messageId,
                    dailyMessageService.getKeyNameSentence("registered_header").formatted(firstName));
        }
        statsService.registerByUserIdAndChatId(userId, chatId, firstName);
        return getReplyMessage(chatId, messageId,
                dailyMessageService.getKeyNameSentence("registered_now_header").formatted(firstName));
    }

    @Override
    public Command getSupportedCommand() {
        return Command.REGISTER;
    }
}
