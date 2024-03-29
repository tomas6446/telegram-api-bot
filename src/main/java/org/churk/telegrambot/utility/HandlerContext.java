package org.churk.telegrambot.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public final class HandlerContext {
    private List<String> args;
    private Update update;
    private boolean isReply;
}
