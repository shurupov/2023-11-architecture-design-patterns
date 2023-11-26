package ru.shurupov.otus.architecture.equation;

public class SquareEquationSolver {
  public static double[] solve(double a, double b, double c, double e) {
    double d = b * b - 4 * a * c;

    if (d <= -e) {
      return new double[0];
    }

    throw new RuntimeException("Unimplemented case");
  }
}
