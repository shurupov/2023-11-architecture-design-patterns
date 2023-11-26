package ru.shurupov.otus.architecture.equation.service;

import ru.shurupov.otus.architecture.equation.exception.CoefficientAEqualsZeroException;
import ru.shurupov.otus.architecture.equation.exception.IllegalCoefficientException;
import ru.shurupov.otus.architecture.equation.exception.NegativeOrZeroEException;

public class SquareEquationSolver {
  public static double[] solve(double a, double b, double c, double e) {

    checkCoefficients(a, b, c, e);

    if (e <= 0) {
      throw new NegativeOrZeroEException();
    }

    double d = b * b - 4 * a * c;

    if (Math.abs(a) < e) {
      throw new CoefficientAEqualsZeroException();
    }

    if (d <= -e) {
      return new double[0];
    }

    if (d > e) {
      return new double[] {
          (-b + Math.sqrt(d)) / (2 * a),
          (-b - Math.sqrt(d)) / (2 * a)
      };
    }

    return new double[]{
        -b / (2 * a)
    };
  }

  private static void checkCoefficients(double ...coefficients) {
    for (double coefficient : coefficients) {
      if (Double.isNaN(coefficient) || Double.isInfinite(coefficient)) {
        throw new IllegalCoefficientException();
      }
    }
  }
}
