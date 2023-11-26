package ru.shurupov.otus.architecture.equation;

public class SquareEquationSolver {
  public static double[] solve(double a, double b, double c, double e) {
    double d = b * b - 4 * a * c;

    if (d <= -e) {
      return new double[0];
    }

    if (d > e) {
      return new double[] {
          (-b + Math.sqrt(d)) / (2 * a),
          (-b - Math.sqrt(d)) / (2 * a)
      };
    }

    if (Math.abs(d) < e) {
      return new double[]{
          -b / (2 * a)
      };
    }

    throw new RuntimeException("Unimplemented case");
  }
}
