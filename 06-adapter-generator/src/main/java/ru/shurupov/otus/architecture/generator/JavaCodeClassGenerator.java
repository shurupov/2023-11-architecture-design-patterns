package ru.shurupov.otus.architecture.generator;

import ru.shurupov.otus.architecture.generator.ClassStructure.Field;
import ru.shurupov.otus.architecture.generator.ClassStructure.MethodStructure;

public class JavaCodeClassGenerator {
  public String generateJava(ClassStructure structure) {
    StringBuilder sb = new StringBuilder();

    insertPackage(structure, sb);
    sb.append("\n");
    sb.append("\n");
    insertImports(structure, sb);
    sb.append("\n");
    startClass(structure, sb);
    sb.append("\n");
    insertFields(structure, sb);
    sb.append('\n');
    insertMethods(structure, sb);
    endClass(sb);

    return sb.toString();
  }

  public void startClass(ClassStructure structure, StringBuilder sb) {
    sb.append("public class ")
        .append(structure.getClassName())
        .append(" implements ")
        .append(structure.getInterfaceName())
        .append(" {\n");
  }

  public void endClass(StringBuilder sb) {
    sb.append("}\n");
  }

  public void insertPackage(ClassStructure structure, StringBuilder sb) {
    sb.append("package ").append(structure.getPackageName()).append(";\n");
  }

  public void insertFields(ClassStructure structure, StringBuilder sb) {
    for (Field field : structure.getFields()) {
      sb.append("  private final ")
          .append(field.getType())
          .append(" ")
          .append(field.getName())
          .append(";\n");
    }
  }

  public void insertImports(ClassStructure structure, StringBuilder sb) {
    for (String classNameToImport : structure.getImports()) {
      sb.append("import ").append(classNameToImport).append(";\n");
    }
  }

  public void insertMethods(ClassStructure structure, StringBuilder sb) {
    for (MethodStructure method : structure.getMethods()) {
      sb.append("  public ").append(method.getReturnType());
      if (!method.isConstructor()) {
        sb.append(" ").append(method.getName());
      }
      sb.append("(");
      for (int i = 0; i < method.getParameters().size(); i++) {
        sb.append(method.getParameters().get(i).getType())
            .append(" ")
            .append(method.getParameters().get(i).getName());
        if (i < method.getParameters().size() - 1) {
          sb.append(", ");
        }
      }
      sb.append(") {\n");
      sb.append(method.getBody());
      sb.append("  }\n");
      sb.append("\n");
    }
  }
}
