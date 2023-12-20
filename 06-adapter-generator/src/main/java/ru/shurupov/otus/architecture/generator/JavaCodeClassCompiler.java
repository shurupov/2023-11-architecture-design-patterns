package ru.shurupov.otus.architecture.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import ru.shurupov.otus.architecture.ioc.IoC;

public class JavaCodeClassCompiler {
  public <I> I compile(String javaCode, Class<I> i, IoC ioc, Map<String, Object> objectMap)
      throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

    String file = writeToFile(javaCode);

    File compiledClass = compile(file);

    Class<?> loadedClass = loadClass(compiledClass);

    return (I) loadedClass.getDeclaredConstructor(IoC.class, Map.class).newInstance(ioc, objectMap);
  }

  private String writeToFile(String text) throws IOException {
    String fileName = System.getProperty("java.io.tmpdir") + "/" + RandomStringUtils.randomAlphanumeric(5) + ".java";

//    File tempDir = new File(System.getProperty("java.io.tmpdir"));
//    File tempFile = File.createTempFile(RandomStringUtils.randomAlphanumeric(5), ".tmp", tempDir);
    FileWriter fileWriter = new FileWriter(fileName, true);
//    System.out.println(tempFile.getAbsolutePath());
    BufferedWriter bw = new BufferedWriter(fileWriter);
    bw.write(text);
    bw.flush();

    return fileName;
  }

  private File compile(String source) throws IOException {
    Path srcPath = Paths.get(URI.create(source));

    File tempDir = new File(System.getProperty("java.io.tmpdir"));
    File tempFile = File.createTempFile(RandomStringUtils.randomAlphanumeric(5), ".class", tempDir);
    FileWriter fileWriter = new FileWriter(tempFile, true);
    BufferedWriter bw = new BufferedWriter(fileWriter);

    List<File> files = Files.list(srcPath)
        .map(Path::toFile)
        .collect(Collectors.toList());
    //получаем компилятор
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    //получаем новый инстанс fileManager для нашего компилятора
    try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
      //получаем список всех файлов описывающих исходники
      Iterable<? extends JavaFileObject> javaFiles = fileManager.getJavaFileObjectsFromFiles(files);

      DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
      //заводим задачу на компиляцию
      JavaCompiler.CompilationTask task = compiler.getTask(
          bw,
          fileManager,
          diagnostics,
          null,
          null,
          javaFiles
      );
      //выполняем задачу
      task.call();
      bw.close();
      //выводим ошибки, возникшие в процессе компиляции
      for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
        System.out.format("Error on line %d in %s%n",
            diagnostic.getLineNumber(),
            diagnostic.getSource());
      }

    }

    return tempFile;
  }

  private Class<?> loadClass(File file) throws MalformedURLException, ClassNotFoundException {
    ClassLoader classLoader = System.class.getClassLoader();

    URLClassLoader urlClassLoader = new URLClassLoader(
        new URL[]{file.toURI().toURL()},
        classLoader
    );

    Class<?> loadedClass = urlClassLoader.loadClass("someClass");

    return loadedClass;
  }
}
