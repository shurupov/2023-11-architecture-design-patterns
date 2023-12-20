package ru.shurupov.otus.architecture.generator;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import ru.shurupov.otus.architecture.generator.ClassStructure.Field;
import ru.shurupov.otus.architecture.generator.ClassStructure.FieldTemplate;
import ru.shurupov.otus.architecture.generator.ClassStructure.MethodParameter;
import ru.shurupov.otus.architecture.generator.ClassStructure.MethodStructure;

@RequiredArgsConstructor
public class ClassStructureCollector {

  private static final Pattern methodActionPattern = Pattern.compile("^(get|set)(.+)$");

  private final List<FieldTemplate> fieldTemplates;
  private final MethodBodyGenerator methodBodyGenerator;

  public <I> ClassStructure collect(Class<I> i) {
    ClassStructure structure = new ClassStructure();

    structure.setClassName(i.getSimpleName() + "$" + RandomStringUtils.randomAlphanumeric(5));
    structure.setPackageName(i.getPackageName() + ".generated");
    structure.setInterfaceName(i.getSimpleName());

    structure.getImports().add(i.getCanonicalName());

    processFieldTemplates(structure);

    collectConstructor(structure);

    collectMethods(i, structure);

    return structure;
  }

  private void processFieldTemplates(ClassStructure structure) {
    for (FieldTemplate fieldTemplate : fieldTemplates) {
      structure.getImports().add(fieldTemplate.getCanonicalType());
      structure.getFields().add(new Field(fieldTemplate.getType(), fieldTemplate.getName()));
    }

    collectConstructor(structure);
  }

  private <I> void collectMethods(Class<I> i, ClassStructure structure) {
    for (Method method : i.getMethods()) {
      collectMethod(method, structure);
    }
  }

  private void collectMethod(Method method, ClassStructure structure) {
    addImportType(method.getReturnType(), structure);
    MethodStructure methodStructure = new MethodStructure();
    methodStructure.setReturnType(method.getReturnType().getSimpleName());
    methodStructure.setName(method.getName());
    methodStructure.setConstructor(false);

    for (Parameter parameter : method.getParameters()) {
      collectParameter(parameter, methodStructure, structure);
    }

    methodStructure.setBody(generateMethodBody(methodStructure, structure.getInterfaceName()));

    structure.getMethods().add(methodStructure);
  }

  //return ioc.resolve("Movable.Position.Get", movableObject);
  private String generateMethodBody(MethodStructure method, String interfaceName) {

    String entity = null;
    String action = method.getName();

    Matcher matcher = methodActionPattern.matcher(action);

    if (matcher.matches()) {
      entity = matcher.group(2);
      action = StringUtils.capitalize(matcher.group(1));
    }

    return methodBodyGenerator.generate(
        "void".equals(method.getReturnType()),
        interfaceName,
        entity,
        action,
        method.getParameters().stream()
            .map(MethodParameter::getName)
            .toList()
      );
  }

  private void collectParameter(Parameter parameter, MethodStructure methodStructure, ClassStructure structure) {
    addImportType(parameter.getType(), structure);
    MethodParameter methodParameter = new MethodParameter();
    methodParameter.setType(parameter.getType().getSimpleName());
    methodParameter.setName(parameter.getName());
    methodStructure.getParameters().add(methodParameter);
  }

  private void collectConstructor(ClassStructure structure) {
    MethodStructure constructor = new MethodStructure();
    constructor.setReturnType(structure.getClassName());
    constructor.setConstructor(true);
    StringBuilder bodyBuilder = new StringBuilder();
    for (Field field : structure.getFields()) {
      constructor.getParameters().add(new MethodParameter(field.getType(), field.getName()));
      bodyBuilder.append("    this.").append(field.getName()).append(" = ").append(field.getName()).append(";\n");
    }
    constructor.setBody(bodyBuilder.toString());
    structure.getMethods().add(constructor);
  }

  private void addImportType(Class<?> cls, ClassStructure structure) {
    if (cls == null) {
      return;
    }

    if (cls.isPrimitive()) {
      return;
    }

    if (cls.isArray()) {
      addImportType(cls.getComponentType(), structure);
      return;
    }

    String canonicalClassName = cls.getCanonicalName();
    if (!structure.getImports().contains(canonicalClassName)) {
      structure.getImports().add(canonicalClassName);
    }
  }
}
