package org.churk.telegrambot.factory;

import org.churk.telegrambot.handler.CommandHandler;
import org.churk.telegrambot.model.Command;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class HandlerFactory {
    private final Map<Command, CommandHandler> handlerMap = new EnumMap<>(Command.class);

    public HandlerFactory(List<CommandHandler> handlers) {
        handlers.stream()
                .filter(handler -> handler.getSupportedCommand() != null)
                .forEachOrdered(handler -> handlerMap.put(handler.getSupportedCommand(), handler));
    }

    public CommandHandler getHandler(Command command) {
        return handlerMap.get(command);
    }
}