package ru.shurupov.otus.architecture.equation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SquareEquationSolverTest {

  @Test
  void givenCoefficientsWithoutRoots_whenSolveExecuted_thenEmptyArrayReturned() {
    assertThat(SquareEquationSolver.solve(1d, 0d, 1d, 0.001d)).isEmpty();
  }

  @Test
  void givenCoefficientsForTwoRoots_whenSolveExecuted_thenTwoRootsArrayReturned() {
    assertThat(SquareEquationSolver.solve(1d, 0d, -1d, 0.001d)).contains(1d, -1d);
  }
}