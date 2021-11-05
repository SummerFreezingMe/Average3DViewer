package com.vsu.cgcourse.math.utils;

public class Vector2f {
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    float x;

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    float y;
    public boolean equals(Vector2f other) {
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps ;
    }
}


