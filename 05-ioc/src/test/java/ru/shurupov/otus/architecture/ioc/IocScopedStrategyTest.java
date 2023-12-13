package ru.shurupov.otus.architecture.ioc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.ioc.strategy.StrategyFactory;

public class IocScopedStrategyTest {
  private IoC ioc;

  @BeforeEach
  void setUp() {
    ioc = new IoC(StrategyFactory.scoped());
  }

  @Test
  public void givenIoC_whenResolveRegisterResolveSumFunction_thenRegisteredAndSumCalculatedUsingParams() {

    Function<Object[], Double> sum = (Object[] args) -> {
      double result = 0d;
      for (Object argn : args) {
        result += ((Number) argn).doubleValue();
      }
      return result;
    };

    Boolean sumAdded = ioc.<Boolean>resolve("IoC.Register", "sum", sum);

    assertThat(sumAdded)
        .isNotNull()
        .isTrue();

    Double sumResult = ioc.<Double>resolve("sum", 1d, 2d, 3.5);

    assertThat(sumResult).isEqualTo(6.5);
  }

  @Test
  public void givenIoC_whenResolveNotExistingKey_thenReturnedNull() {
    Object value = ioc.resolve("nothing");

    assertThat(value)
        .isNull();
  }

  @Test
  public void givenIoC_whenRegisterAndGet_thenRegisteredAndGot() {
    Boolean nameAdded = ioc.<Boolean>resolve("IoC.Register", "name", "Evgeny");

    assertThat(nameAdded)
        .isNotNull()
        .isTrue();

    String name = ioc.<String>resolve("name");

    assertThat(name).isEqualTo("Evgeny");
  }

  @Test
  public void givenIoC_whenRegisterAndExecuteWithoutParams_thenRegisteredAndExecutedWithoutParams() {

    Random random = new Random();

    Supplier<Integer> randomizer = random::nextInt;

    Boolean randomAdded = ioc.<Boolean>resolve("IoC.Register", "randomInt", randomizer);

    assertThat(randomAdded)
        .isNotNull()
        .isTrue();

    Integer intValue1 = ioc.<Integer>resolve("randomInt");
    Integer intValue2 = ioc.<Integer>resolve("randomInt");

    assertThat(intValue1).isInstanceOf(Integer.class);
    assertThat(intValue2).isInstanceOf(Integer.class);

    assertThat(intValue1).isNotEqualTo(intValue2);
  }

  @Test
  public void givenIoC_whenCreateScopeAndAddToItAndGet_thenGot() {
    ioc.<Boolean>resolve("Scope.Add", "scope1");
    ioc.<Boolean>resolve("Scope.Select", "scope1");
    ioc.<Boolean>resolve("IoC.Register", "name", "Evgeny");

    String name = ioc.<String>resolve("name");
    assertThat(name).isEqualTo("Evgeny");
  }

  @Test
  public void givenIoC_whenCreateScopesAndAddToOneAndGetFromAnother_thenGotNull() {
    ioc.<Boolean>resolve("Scope.Add", "scope1");
    ioc.<Boolean>resolve("Scope.Add", "scope2");
    ioc.<Boolean>resolve("Scope.Select", "scope1");
    ioc.<Boolean>resolve("IoC.Register", "name", "Evgeny");
    ioc.<Boolean>resolve("Scope.Select", "scope2");

    String name = ioc.<String>resolve("name");
    assertThat(name).isNull();
  }

  @Test
  public void givenIoC_whenCreateScopesAndAddToOneAndGetFromChild_thenGotNull() {
    ioc.<Boolean>resolve("Scope.Add", "scope1");
    ioc.<Boolean>resolve("Scope.Select", "scope1");
    ioc.<Boolean>resolve("Scope.Add", "scope2");
    ioc.<Boolean>resolve("Scope.Select", "scope2");
    ioc.<Boolean>resolve("Scope.Add", "scope3");
    ioc.<Boolean>resolve("Scope.Select", "scope1");
    ioc.<Boolean>resolve("IoC.Register", "name", "Evgeny");
    ioc.<Boolean>resolve("Scope.Select", "scope3");

    String name = ioc.<String>resolve("name");
    assertThat(name).isEqualTo("Evgeny");
  }
}
