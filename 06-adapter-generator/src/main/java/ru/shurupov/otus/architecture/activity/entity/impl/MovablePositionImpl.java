package ru.shurupov.otus.architecture.activity.entity.impl;

import lombok.RequiredArgsConstructor;
import ru.shurupov.otus.architecture.activity.entity.Position;

@RequiredArgsConstructor
public class MovablePositionImpl implements Position {

    private final double x;
    private final double y;

    @Override
    public double[] getCoords() {
        return new double[] {x, y};
    }
}
