package org.churk.telegrambot.handler;

import org.churk.telegrambot.handler.game.RandomResponseHandler;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class HandlerFactory {
    private final RandomResponseHandler randomResponseHandler;
    private final Map<Command, CommandHandler> handlerMap = new EnumMap<>(Command.class);

    public HandlerFactory(List<CommandHandler> handlers, RandomResponseHandler randomResponseHandler) {
        this.randomResponseHandler = randomResponseHandler;
        handlers.stream()
                .filter(handler -> handler.getSupportedCommand() != null)
                .forEachOrdered(handler -> handlerMap.put(handler.getSupportedCommand(), handler));
    }

    public CommandHandler getHandler(Command command) {
        if (command == null) {
            return randomResponseHandler;
        }
        return handlerMap.get(command);
    }
}
