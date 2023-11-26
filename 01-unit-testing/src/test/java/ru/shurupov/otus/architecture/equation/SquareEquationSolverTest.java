package ru.shurupov.otus.architecture.equation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shurupov.otus.architecture.equation.exception.CoefficientAEqualsZeroException;
import ru.shurupov.otus.architecture.equation.exception.IllegalCoefficientException;
import ru.shurupov.otus.architecture.equation.exception.NegativeOrZeroEException;
import ru.shurupov.otus.architecture.equation.service.SquareEquationSolver;

class SquareEquationSolverTest {

  @Test
  void givenCoefficientsWithoutRoots_whenSolveExecuted_thenEmptyArrayReturned() {
    assertThat(SquareEquationSolver.solve(1d, 0d, 1d, 0.001d)).isEmpty();
  }

  @Test
  void givenCoefficientsForTwoRoots_whenSolveExecuted_thenTwoRootsArrayReturned() {
    assertThat(SquareEquationSolver.solve(1d, 0d, -1d, 0.001d))
        .usingComparatorWithPrecision(0.001)
        .containsExactlyInAnyOrder(1d, -1d);
  }

  @Test
  void givenCoefficientsForOnRoot_whenSolveExecuted_thenOneRootArrayReturned() {
    assertThat(SquareEquationSolver.solve(1d, 2.00001, 1d, 0.001d))
        .usingComparatorWithPrecision(0.001)
        .containsExactlyInAnyOrder(-1d);
  }

  @Test
  void givenACoefficientEqualsZero_whenSolveExecuted_thenExceptionThrown() {
    assertThatThrownBy(() -> SquareEquationSolver.solve(0d, 2d, 1d, 0.001d))
        .isOfAnyClassIn(CoefficientAEqualsZeroException.class);
  }

  @ParameterizedTest
  @ArgumentsSource(CoefficientsArgumentsProvider.class)
  void givenIllegalCoefficientValue_whenSolveExecuted_thenIllegalCoefficientExceptionThrown(Coeffificients coeffificients) {
    assertThatThrownBy(() -> SquareEquationSolver.solve(coeffificients.a, coeffificients.b, coeffificients.c, coeffificients.e))
        .isOfAnyClassIn(IllegalCoefficientException.class);
  }

  @ParameterizedTest
  @ValueSource(doubles = {0d, -0.1})
  void givenZeroOrNegativeE_whenSolveExecuted_thenNegativeOrZeroEExceptionThrown(double e) {
    assertThatThrownBy(() -> SquareEquationSolver.solve(1, 1, 1, e))
        .isOfAnyClassIn(NegativeOrZeroEException.class);
  }

  static class CoefficientsArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of(new Coeffificients(Double.NaN, 1, 1, 0.0001)),
          Arguments.of(new Coeffificients(1, Double.NaN, 1, 0.0001)),
          Arguments.of(new Coeffificients(1, 1, Double.NaN, 0.0001)),
          Arguments.of(new Coeffificients(1, 1, 1, Double.NaN)),
          Arguments.of(new Coeffificients(Double.NaN, Double.NaN, Double.NaN, Double.NaN)),
          Arguments.of(new Coeffificients(Double.NEGATIVE_INFINITY, 1, 1, 0.0001)),
          Arguments.of(new Coeffificients(1, Double.NEGATIVE_INFINITY, 1, 0.0001)),
          Arguments.of(new Coeffificients(1, 1, Double.NEGATIVE_INFINITY, 0.0001)),
          Arguments.of(new Coeffificients(1, 1, 1, Double.NEGATIVE_INFINITY)),
          Arguments.of(new Coeffificients(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)),
          Arguments.of(new Coeffificients(Double.POSITIVE_INFINITY, 1, 1, 0.0001)),
          Arguments.of(new Coeffificients(1, Double.POSITIVE_INFINITY, 1, 0.0001)),
          Arguments.of(new Coeffificients(1, 1, Double.POSITIVE_INFINITY, 0.0001)),
          Arguments.of(new Coeffificients(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
      );
    }
  }

  @Value
  static class Coeffificients {
    double a;
    double b;
    double c;
    double e;
  }
}