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
import ru.shurupov.otus.architecture.abstraction.activity.Movable;
import ru.shurupov.otus.architecture.abstraction.entity.Position;
import ru.shurupov.otus.architecture.abstraction.entity.Velocity;
import ru.shurupov.otus.architecture.abstraction.exception.UnableToGetPositionException;
import ru.shurupov.otus.architecture.abstraction.exception.UnableToGetVelocityException;
import ru.shurupov.otus.architecture.abstraction.exception.UnableToMoveException;
import ru.shurupov.otus.architecture.generator.ClassStructure.FieldTemplate;
import ru.shurupov.otus.architecture.ioc.IoC;

@ExtendWith(MockitoExtension.class)
class AdapterGeneratorGenerateMovableTest {

  @Mock
  private IoC ioc;

  @Mock
  private Position position;
  @Mock
  private Velocity velocity;

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
  void givenIoCWithPositionAndGenerator_whenGetPosition_thenPositionResolvedAndReturned() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Movable.Position.Get"), eq(object))).thenReturn(position);
    Movable movable = adapterGenerator.generate(Movable.class, ioc, object);
    Position returnedPosition = movable.getPosition();

    assertThat(returnedPosition).isEqualTo(position);
    verify(ioc, times(1)).resolve(eq("Movable.Position.Get"), eq(object));
  }

  @Test
  void givenIoCAndGenerator_whenGetPositionAndResolveThrowsException_thenThrowsException() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Movable.Position.Get"), eq(object)))
        .thenThrow(UnableToGetPositionException.class);

    Movable movable = adapterGenerator.generate(Movable.class, ioc, object);

    assertThatThrownBy(() ->  movable.getPosition())
        .isInstanceOf(UnableToGetPositionException.class);
    verify(ioc, times(1)).resolve(eq("Movable.Position.Get"), eq(object));
  }

  @Test
  void givenIoCWithoutPositionAndGenerator_whenGetPosition_thenPositionResolvedAndReturned() throws Exception {
    Map<String, Object> object = new HashMap<>();

    Movable movable = adapterGenerator.generate(Movable.class, ioc, object);
    Position returnedPosition = movable.getPosition();

    assertThat(returnedPosition).isNull();
    verify(ioc, times(1)).resolve(eq("Movable.Position.Get"), eq(object));
  }

  @Test
  void givenIoCWithVelocityAndGenerator_whenGetVelocity_thenVelocityResolvedAndReturned() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Movable.Velocity.Get"), eq(object))).thenReturn(velocity);
    Movable movable = adapterGenerator.generate(Movable.class, ioc, object);
    Velocity returnedVelocity = movable.getVelocity();

    assertThat(returnedVelocity).isEqualTo(velocity);
    verify(ioc, times(1)).resolve(eq("Movable.Velocity.Get"), eq(object));
  }

  @Test
  void givenIoCAndGenerator_whenGetVelocityAndResolveThrowsException_thenThrowsException() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Movable.Velocity.Get"), eq(object)))
        .thenThrow(UnableToGetVelocityException.class);

    Movable movable = adapterGenerator.generate(Movable.class, ioc, object);

    assertThatThrownBy(() ->  movable.getVelocity())
        .isInstanceOf(UnableToGetVelocityException.class);
    verify(ioc, times(1)).resolve(eq("Movable.Velocity.Get"), eq(object));
  }

  @Test
  void givenIoCWithoutVelocityAndGenerator_whenGetVelocity_thenPositionVelocityAndReturned() throws Exception {
    Map<String, Object> object = new HashMap<>();

    Movable movable = adapterGenerator.generate(Movable.class, ioc, object);
    Velocity returnedVelocity = movable.getVelocity();

    assertThat(returnedVelocity).isNull();
    verify(ioc, times(1)).resolve(eq("Movable.Velocity.Get"), eq(object));
  }

  @Test
  void givenIoCAAndGenerator_whenSucceedMove_thenMoved() throws Exception {
    Map<String, Object> object = new HashMap<>();

    Movable movable = adapterGenerator.generate(Movable.class, ioc, object);
    movable.move(velocity);

    verify(ioc, times(1)).resolve(eq("Movable.Move"), eq(object), eq(velocity));
  }

  @Test
  void givenIoCAndGenerator_whenMoveThrowsException_thenThrowsException() throws Exception {
    Map<String, Object> object = new HashMap<>();

    when(ioc.resolve(eq("Movable.Move"), eq(object), eq(velocity)))
        .thenThrow(UnableToMoveException.class);

    Movable movable = adapterGenerator.generate(Movable.class, ioc, object);

    assertThatThrownBy(() ->  movable.move(velocity))
        .isInstanceOf(UnableToMoveException.class);
    verify(ioc, times(1)).resolve(eq("Movable.Move"), eq(object), eq(velocity));
  }
}