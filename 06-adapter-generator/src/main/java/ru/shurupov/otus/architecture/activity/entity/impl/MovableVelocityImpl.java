package ru.shurupov.otus.architecture.activity.entity.impl;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.activity.entity.Velocity;

@RequiredArgsConstructor
public class MovableVelocityImpl implements Velocity {

    private final double deltaX;
    private final double deltaY;

    @Override
    public double[] getPositionDelta() {
        return new double[] {deltaX, deltaY};
    }
}
