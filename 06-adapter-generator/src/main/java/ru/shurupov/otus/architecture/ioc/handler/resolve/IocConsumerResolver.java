package ru.shurupov.otus.architecture.ioc.handler.resolve;

import java.util.Map;
import java.util.function.Consumer;

public class IocConsumerResolver implements IocResolveHandler<Consumer<Object[]>, Boolean> {
    @Override
    public boolean canHandle(Object value) {
        return value instanceof Consumer;
    }

    @Override
    public Boolean resolve(Map<String, Object> context, Consumer<Object[]> value, Object[] args) {
        value.accept(args);
        return true;
    }
}
