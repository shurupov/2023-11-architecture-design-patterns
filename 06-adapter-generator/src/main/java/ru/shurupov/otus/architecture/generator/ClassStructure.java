package ru.shurupov.otus.architecture.generator;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
public class ClassStructure {
  private String interfaceName;
  private String className;
  private String packageName;
  private List<String> imports = new ArrayList<>();
  private List<Field> fields = new ArrayList<>();
  private List<MethodStructure> methods = new ArrayList<>();

  @Data
  public static class MethodStructure {
    boolean constructor;
    String returnType;
    String name;
    List<MethodParameter> parameters = new ArrayList<>();
    String body;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MethodParameter {
    String type;
    String name;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Field {
    String type;
    String name;
  }

  @Value
  @Builder
  public static class FieldTemplate {
    String canonicalType;
    String type;
    String name;
  }
}
