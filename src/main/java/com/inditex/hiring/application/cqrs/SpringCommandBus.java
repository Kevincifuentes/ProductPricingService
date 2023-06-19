package com.inditex.hiring.application.cqrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpringCommandBus implements CommandBus {

    private static final Logger logger = LoggerFactory.getLogger(SpringCommandBus.class);

    private final Map<Class<?>, CommandHandler> commandHandlers;

    @Autowired
    public SpringCommandBus(final List<CommandHandler> commandHandlers) {
        this.commandHandlers = new HashMap<>();
        commandHandlers.forEach(commandHandler -> {
            final var commandClass = getCommandClass(commandHandler);
            this.commandHandlers.put(commandClass, commandHandler);
        });
    }

    public void execute(final Command command) {
        if (!commandHandlers.containsKey(command.getClass())) {
            throw new CommandNotFoundException(command.getClass().getSimpleName());
        }
        commandHandlers.get(command.getClass()).execute(command);
    }

    private Class<?> getCommandClass(final CommandHandler handler) {
        final var commandInterface =
                ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        final var typeName = commandInterface.getTypeName();
        try {
            return Class.forName(commandInterface.getTypeName());
        } catch (ClassNotFoundException e) {
            logger.error("Command class not found", e);
            throw new CommandClassNotFoundException(typeName);
        }
    }
}
