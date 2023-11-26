package ru.shurupov.otus.architecture.equation;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shurupov.otus.architecture.equation.SquareEquationSolver.solve;

import org.junit.jupiter.api.Test;

class SquareEquationSolverTest {

  @Test
  void givenCoefficientsWithoutRoots_whenSolveExecuted_thenEmptyArrayReturned() {
    assertThat(solve(1d, 0d, 1d, 0.001d)).isEmpty();
  }
}