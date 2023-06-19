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
public class SpringQueryBus implements QueryBus {

    private static final Logger logger = LoggerFactory.getLogger(SpringQueryBus.class);

    private final Map<Class<?>, QueryHandler> queryHandlers;

    @Autowired
    public SpringQueryBus(final List<QueryHandler> queryHandlers) {
        this.queryHandlers = new HashMap<>();
        queryHandlers.forEach(commandHandler -> {
            final var commandClass = getQueryClass(commandHandler);
            this.queryHandlers.put(commandClass, commandHandler);
        });
    }

    @Override
    public <T> T ask(final Query<T> query) {
        if (!queryHandlers.containsKey(query.getClass())) {
            throw new QueryNotFoundException(query.getClass().getSimpleName());
        }
        return (T) queryHandlers.get(query.getClass()).ask(query);
    }

    private Class<?> getQueryClass(final QueryHandler handler) {
        final var queryInterface =
                ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        final var typeName = queryInterface.getTypeName();
        try {
            return Class.forName(queryInterface.getTypeName());
        } catch (ClassNotFoundException e) {
            logger.error("Command class not found", e);
            throw new QueryClassNotFoundException(typeName);
        }
    }
}
