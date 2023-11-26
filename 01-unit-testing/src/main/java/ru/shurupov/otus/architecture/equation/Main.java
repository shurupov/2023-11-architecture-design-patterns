package ru.shurupov.otus.architecture.equation;

public class Main {

  public static void main(String[] args) {
    if (args.length != 4) {
      System.out.println("Argument count is wrong. It should be four");
    }
    if (args.length == 4) {
      double[] solution = SquareEquationSolver.solve(
          Double.parseDouble(args[0]),
          Double.parseDouble(args[1]),
          Double.parseDouble(args[2]),
          Double.parseDouble(args[3])
      );
      if (solution.length == 0) {
        System.out.println("Equation has no roots");
      } else {
        for (int i = 0; i < solution.length; i++) {
          System.out.println("x" + (i+1) + "=" + solution[i]);
        }
      }
    }
  }
}