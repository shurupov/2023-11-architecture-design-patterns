package ru.shurupov.otus.architecture.equation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import ru.shurupov.otus.architecture.equation.exception.CoefficientAEqualsZeroException;

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
    assertThat(SquareEquationSolver.solve(1d, 2d, 1d, 0.001d))
        .usingComparatorWithPrecision(0.001)
        .containsExactlyInAnyOrder(-1d);
  }

  @Test
  void givenACoefficientEqualsZero_whenSolveExecuted_thenExceptionThrown() {
    assertThatThrownBy(() -> SquareEquationSolver.solve(0d, 2d, 1d, 0.001d))
        .isOfAnyClassIn(CoefficientAEqualsZeroException.class);
  }
}