package com.inditex.hiring.application.cqrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class CommandBus {

    private final List<CommandHandler> commandHandlers;

    @Autowired
    public CommandBus(final List<CommandHandler> commandHandlers) {
        this.commandHandlers = commandHandlers;
    }

    public void execute(final Command command) {
        commandHandlers.stream()
                .filter(commandHandler -> doesHandleType(commandHandler, command))
                .findFirst()
                .orElseThrow(() -> new CommandNotFoundException(command.getClass().getSimpleName()))
                .execute(command);
    }

    private boolean doesHandleType(final CommandHandler commandHandler, final Command command) {
        final var commandHandlerClassGenericInterfaces = commandHandler.getClass().getGenericInterfaces();
        if (commandHandlerClassGenericInterfaces.length == 0) {
            return false;
        }
        return containsCommandType(commandHandlerClassGenericInterfaces, command);
    }

    private boolean containsCommandType(final Type[] commandHandlerTypes, final Command command) {
        return commandHandlerTypes[0].getTypeName().contains(command.getClass().getSimpleName());
    }
}
