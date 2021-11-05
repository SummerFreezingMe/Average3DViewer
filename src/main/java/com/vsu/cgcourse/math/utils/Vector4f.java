package com.vsu.cgcourse.math.utils;

public class Vector4f {

    public Vector4f(float x, float y, float z, float v) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.v = v;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setX(float x) {
        this.x = x;
    }

    float x;
    float y;

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    float z;
    float v;
    public boolean equals(Vector4f other) {
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps&& Math.abs(v - other.v) < eps;
    }
}
