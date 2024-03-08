package ru.shurupov.otus.architecture.ioc;

import java.util.List;
import java.util.Map;
import ru.shurupov.otus.architecture.ioc.context.CreateScope;
import ru.shurupov.otus.architecture.ioc.context.IocRegister;
import ru.shurupov.otus.architecture.ioc.context.IocUnregister;
import ru.shurupov.otus.architecture.ioc.context.RemoveScope;
import ru.shurupov.otus.architecture.ioc.context.SelectParentScope;
import ru.shurupov.otus.architecture.ioc.context.SelectScope;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocConsumerResolver;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocContextFunctionResolver;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocContextProcessorHandler;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocFunctionResolver;
import ru.shurupov.otus.architecture.ioc.handler.resolve.IocSupplierResolver;
import ru.shurupov.otus.architecture.ioc.strategy.IoCStrategy;
import ru.shurupov.otus.architecture.ioc.strategy.ScopedIoCStrategy;
import ru.shurupov.otus.architecture.ioc.strategy.SimpleIoCStrategy;

public class IoCFactory {

  public static IoC simple() {
    return new IoC(simpleStrategy());
  }

  public static IoC scoped() {
    return new IoC(scopedStrategy());
  }

  public static IoCStrategy simpleStrategy() {
    IoCStrategy strategy = new SimpleIoCStrategy(
        Map.of("IoC.Register", new IocRegister()),
        List.of(
            new IocContextProcessorHandler(),
            new IocFunctionResolver<>(),
            new IocContextFunctionResolver<>(),
            new IocSupplierResolver<>(),
            new IocConsumerResolver()
        )
    );
    strategy.resolve("IoC.Register", "IoC.Unregister", new IocUnregister());
    return strategy;
  }

  public static IoCStrategy scopedStrategy() {
    IoCStrategy strategy = new ScopedIoCStrategy(
        Map.of("IoC.Register", new IocRegister()),
        List.of(
            new IocContextProcessorHandler(),
            new IocFunctionResolver<>(),
            new IocContextFunctionResolver<>(),
            new IocSupplierResolver<>(),
            new IocConsumerResolver()
        )
    );
    strategy.resolve("IoC.Register", "Scope.Add", new CreateScope());
    strategy.resolve("IoC.Register", "Scope.Select", new SelectScope());
    strategy.resolve("IoC.Register", "Scope.Select.Parent", new SelectParentScope());
    strategy.resolve("IoC.Register", "Scope.Remove", new RemoveScope());
    return strategy;
  }
}
