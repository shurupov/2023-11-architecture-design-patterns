package ru.shurupov.otus.architecture.ioc.strategy;

import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.ioc.context.IocRegister;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocContextProcessorHandler;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocFunctionResolver;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocSupplierResolver;

public class StrategyFactory {
  public static IoCStrategy simple() {
    return new IoCStrategySimple(
        Map.of("IoC.Register", new IocRegister()),
        List.of(
            new IocContextProcessorHandler(),
            new IocFunctionResolver<>(),
            new IocSupplierResolver<>()
        )
    );
  }
}
