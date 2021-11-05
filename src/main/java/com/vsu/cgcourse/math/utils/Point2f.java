package com.vsu.cgcourse.math.utils;

public class Point2f {

    public Point2f(float x,float y) {
        this.x = x;
        this.y = y;
    }

    public float x;
    public float y;
    public final float distanceSquared(javax.vecmath.Point2f var1) {
        float var2 = this.x - var1.x;
        float var3 = this.y - var1.y;
        return var2 * var2 + var3 * var3;
    }

    public final float distance(javax.vecmath.Point2f var1) {
        float var2 = this.x - var1.x;
        float var3 = this.y - var1.y;
        return (float)Math.sqrt((double)(var2 * var2 + var3 * var3));
    }

    public final float distanceL1(javax.vecmath.Point2f var1) {
        return Math.abs(this.x - var1.x) + Math.abs(this.y - var1.y);
    }

    public final float distanceLinf(javax.vecmath.Point2f var1) {
        return Math.max(Math.abs(this.x - var1.x), Math.abs(this.y - var1.y));
    }
}
