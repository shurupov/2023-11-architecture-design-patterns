package ru.shurupov.otus.architecture.ioc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.shurupov.otus.architecture.ioc.strategy.StrategyFactory;

@Execution(ExecutionMode.CONCURRENT)
public class IoCScopedStrategyConcurrentTest {

  private static final IoC ioc = new IoC(StrategyFactory.scoped());

  private static final List<ScopeData> scopeParams = List.of(
      new ScopeData("root", "rootScopeVariableValue"),
      new ScopeData("scope1", "firstScopeVariableValue"),
      new ScopeData("scope2", "secondScopeVariableValue")
  );

  @BeforeAll
  static void setUp() {
    ioc.resolve("IoC.Register", "variable", "rootScopeVariableValue");
    ioc.resolve("Scope.Add", "scope1");
    ioc.resolve("Scope.Select", "scope1");
    ioc.resolve("IoC.Register", "variable", "firstScopeVariableValue");
    ioc.resolve("Scope.Select", "root");
    ioc.resolve("Scope.Add", "scope2");
    ioc.resolve("Scope.Select", "scope2");
    ioc.resolve("IoC.Register", "variable", "secondScopeVariableValue");
  }

  @RepeatedTest(300)
  public void givenScope_whenCreateAndRemoved_thenItsAddedAndRemoved(RepetitionInfo repetitionInfo) {
    ScopeData data = scopeParams.get(repetitionInfo.getCurrentRepetition() % scopeParams.size());
    ioc.<Boolean>resolve("Scope.Select", data.getScope());
    System.out.println("scope " + data.getScope() + " start " + Thread.currentThread().getName());
    String variable = ioc.resolve("variable");
    assertThat(variable).isEqualTo(data.getValue());
    ioc.resolve("IoC.Register", "variable", data.getValue());
    System.out.println("scope " + data.getScope() + " end " + Thread.currentThread().getName());
  }

  @RequiredArgsConstructor
  @Getter
  static class ScopeData {
    private final String scope;
    private final String value;
  }

}
