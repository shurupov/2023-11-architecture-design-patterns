package ru.shurupov.otus.architecture.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shurupov.otus.architecture.activity.actor.Rotatable;
import ru.shurupov.otus.architecture.activity.entity.Angle;
import ru.shurupov.otus.architecture.activity.entity.AngularVelocity;
import ru.shurupov.otus.architecture.activity.exception.UnableToGetDirectionException;
import ru.shurupov.otus.architecture.activity.exception.UnableToGetVelocityException;
import ru.shurupov.otus.architecture.activity.exception.UnableToRotateException;
import ru.shurupov.otus.architecture.generator.ClassStructure.FieldTemplate;
import ru.shurupov.otus.architecture.ioc.IoC;

@ExtendWith(MockitoExtension.class)
class AdapterGeneratorGenerateRotatableTest {

  @Mock
  private IoC ioc;

  @Mock
  private Angle angle;
  @Mock
  private AngularVelocity velocity;

  private AdapterGenerator adapterGenerator;

  @BeforeEach
  public void init() {
    adapterGenerator = new AdapterGenerator(
        new ClassStructureCollector(
            List.of(
                FieldTemplate.builder()
                    .canonicalType("ru.shurupov.otus.architecture.ioc.IoC")
                    .type("IoC")
                    .name("ioc")
                    .build(),

                FieldTemplate.builder()
                    .canonicalType("java.util.Map")
                    .type("Map<String, Object>")
                    .name("object")
                    .build()
            ),
            new IocResolveMethodBodyGenerator(
                "ioc",
                "resolve",
                "object",
                4
            )
        ),
        new JavaCodeClassGenerator(),
        new JavaCodeClassCompiler()
    );
  }

  @Test
  void givenIoCWithDirectionAndGenerator_whenGetDirection_thenDirectionResolvedAndReturned() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Rotatable.Direction.Get"), eq(object))).thenReturn(angle);
    Rotatable rotatable = adapterGenerator.generate(Rotatable.class, ioc, object);
    Angle returnedDirection = rotatable.getDirection();

    assertThat(returnedDirection).isEqualTo(angle);
    verify(ioc, times(1)).resolve(eq("Rotatable.Direction.Get"), eq(object));
  }

  @Test
  void givenIoCAndGenerator_whenGetDirectionAndResolveThrowsException_thenThrowsException() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Rotatable.Direction.Get"), eq(object)))
        .thenThrow(UnableToGetDirectionException.class);

    Rotatable rotatable = adapterGenerator.generate(Rotatable.class, ioc, object);

    assertThatThrownBy(() ->  rotatable.getDirection())
        .isInstanceOf(UnableToGetDirectionException.class);
    verify(ioc, times(1)).resolve(eq("Rotatable.Direction.Get"), eq(object));
  }

  @Test
  void givenIoCWithoutDirectionAndGenerator_whenGetDirection_thenDirectionResolvedAndReturned() throws Exception {
    Map<String, Object> object = new HashMap<>();

    Rotatable rotatable = adapterGenerator.generate(Rotatable.class, ioc, object);
    Angle returnedDirection = rotatable.getDirection();

    assertThat(returnedDirection).isNull();
    verify(ioc, times(1)).resolve(eq("Rotatable.Direction.Get"), eq(object));
  }

  @Test
  void givenIoCWithVelocityAndGenerator_whenGetVelocity_thenVelocityResolvedAndReturned() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Rotatable.Velocity.Get"), eq(object))).thenReturn(velocity);
    Rotatable rotatable = adapterGenerator.generate(Rotatable.class, ioc, object);
    AngularVelocity returnedVelocity = rotatable.getVelocity();

    assertThat(returnedVelocity).isEqualTo(velocity);
    verify(ioc, times(1)).resolve(eq("Rotatable.Velocity.Get"), eq(object));
  }

  @Test
  void givenIoCAndGenerator_whenGetVelocityAndResolveThrowsException_thenThrowsException() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Rotatable.Velocity.Get"), eq(object)))
        .thenThrow(UnableToGetVelocityException.class);

    Rotatable rotatable = adapterGenerator.generate(Rotatable.class, ioc, object);

    assertThatThrownBy(() ->  rotatable.getVelocity())
        .isInstanceOf(UnableToGetVelocityException.class);
    verify(ioc, times(1)).resolve(eq("Rotatable.Velocity.Get"), eq(object));
  }

  @Test
  void givenIoCWithoutVelocityAndGenerator_whenGetVelocity_thenDirectionVelocityAndReturned() throws Exception {
    Map<String, Object> object = new HashMap<>();

    Rotatable rotatable = adapterGenerator.generate(Rotatable.class, ioc, object);
    AngularVelocity returnedVelocity = rotatable.getVelocity();

    assertThat(returnedVelocity).isNull();
    verify(ioc, times(1)).resolve(eq("Rotatable.Velocity.Get"), eq(object));
  }

  @Test
  void givenIoCAAndGenerator_whenSucceedRotate_thenRotated() throws Exception {
    Map<String, Object> object = new HashMap<>();

    Rotatable rotatable = adapterGenerator.generate(Rotatable.class, ioc, object);
    rotatable.rotate(velocity);

    verify(ioc, times(1)).resolve(eq("Rotatable.Rotate"), eq(object), eq(velocity));
  }

  @Test
  void givenIoCAndGenerator_whenRotateThrowsException_thenThrowsException() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Rotatable.Rotate"), eq(object), eq(velocity)))
        .thenThrow(UnableToRotateException.class);

    Rotatable rotatable = adapterGenerator.generate(Rotatable.class, ioc, object);

    assertThatThrownBy(() ->  rotatable.rotate(velocity))
        .isInstanceOf(UnableToRotateException.class);
    verify(ioc, times(1)).resolve(eq("Rotatable.Rotate"), eq(object), eq(velocity));
  }
}