package ru.shurupov.otus.architecture.ioc.strategy;

import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.ioc.context.CreateScope;
import ru.shurupov.otus.architecture.ioc.context.IocRegister;
import ru.shurupov.otus.architecture.ioc.context.SelectParentScope;
import ru.shurupov.otus.architecture.ioc.context.SelectScope;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocContextProcessorHandler;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocFunctionResolver;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocSupplierResolver;

public class StrategyFactory {
  public static IoCStrategy simple() {
    return new SimpleIoCStrategy(
        Map.of("IoC.Register", new IocRegister()),
        List.of(
            new IocContextProcessorHandler(),
            new IocFunctionResolver<>(),
            new IocSupplierResolver<>()
        )
    );
  }

  public static IoCStrategy scoped() {
    IoCStrategy strategy = new ScopedIoCStrategy(
        Map.of("IoC.Register", new IocRegister()),
        List.of(
            new IocContextProcessorHandler(),
            new IocFunctionResolver<>(),
            new IocSupplierResolver<>()
        )
    );
    strategy.resolve("IoC.Register", "Scope.Add", new CreateScope());
    strategy.resolve("IoC.Register", "Scope.Select", new SelectScope());
    strategy.resolve("IoC.Register", "Scope.Select.Parent", new SelectParentScope());
    return strategy;
  }
}
