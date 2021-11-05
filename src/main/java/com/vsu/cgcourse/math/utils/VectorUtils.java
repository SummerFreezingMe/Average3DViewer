package com.vsu.cgcourse.math.utils;

public class VectorUtils {


    public float vectorLength(final Vector2f v) {
        return (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY());
    }

    public float vectorLength(final Vector3f v) {
        return (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());
    }

    public float vectorLength(final Vector4f v) {
        return (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ() + v.getV() * v.getV());
    }

    /**
     * @param sign - sign функция, принимает значение "1" при сложении и -1 при вычитании
     * @param v1   первый вектор
     * @param v2   второй (вычитаемый) вектор
     **/
    public Vector2f vectorSum(final Vector2f v1, final Vector2f v2, int sign) throws ArithmeticException {
        if (sign != 1 && sign != -1) {
            throw new ArithmeticException("Ошибка! неверное значение sign-функции");
        }
        return new Vector2f(v1.getX() + v2.getX() * sign, v1.getY() + v2.getY() * sign);
    }

    public Vector3f vectorSum(final Vector3f v1, final Vector3f v2, int sign) throws ArithmeticException {
        if (sign != 1 && sign != -1) {
            throw new ArithmeticException("Ошибка! неверное значение sign-функции");
        }
        return new Vector3f(v1.getX() + v2.getX() * sign, v1.getY() + v2.getY() * sign, v1.getZ() + v2.getZ() * sign);
    }

    public Vector4f vectorSum(final Vector4f v1, final Vector4f v2, int sign) throws ArithmeticException {
        if (sign != 1 && sign != -1) {
            throw new ArithmeticException("Ошибка! неверное значение sign-функции");
        }
        return new Vector4f(v1.getX() + v2.getX() * sign, v1.getY() + v2.getY() * sign,
                v1.getZ() + v2.getZ() * sign, v1.getV() + v2.getV() * sign);
    }


    public Vector2f scalarDenomination(final Vector2f v, float n) throws ArithmeticException {
        if (n == 0) {
            throw new ArithmeticException("Ошибка! На ноль делить нельзя");
        }

        return new Vector2f(v.getX() / n, v.getY() / n);
    }

    public Vector3f scalarDenomination(final Vector3f v, float n) throws ArithmeticException {
        if (n == 0) {
            throw new ArithmeticException("Ошибка! На ноль делить нельзя");
        }

        return new Vector3f(v.getX() / n, v.getY() / n, v.getZ() / n);
    }

    public Vector4f scalarDenomination(final Vector4f v, float n) throws ArithmeticException {
        if (n == 0) {
            throw new ArithmeticException("Ошибка! На ноль делить нельзя");
        }

        return new Vector4f(v.getX() / n, v.getY() / n, v.getZ() / n, v.getV() / n);
    }


    public Vector2f scalarMultiply(final Vector2f v, float n) {
        return new Vector2f(v.getX() * n, v.getY() * n);
    }

    public Vector3f scalarMultiply(final Vector3f v, float n) {
        return new Vector3f(v.getX() * n, v.getY() * n, v.getZ() * n);
    }

    public Vector4f scalarMultiply(final Vector4f v, float n) {
        return new Vector4f(v.getX() * n, v.getY() * n, v.getZ() * n, v.getV() * n);
    }


    public void normalisation(Vector2f v) {
        double localLength = vectorLength(v);
        v.setX((float) (v.getX() / localLength));
        v.setY((float) (v.getY() / localLength));
    }

    public void normalisation(Vector3f v) {
        double localLength = vectorLength(v);
        v.setX((float) (v.getX() / localLength));
        v.setY((float) (v.getY() / localLength));
        v.setZ((float) (v.getZ() / localLength));
    }

    public void normalisation(Vector4f v) {
        double localLength = vectorLength(v);
        v.setX((float) (v.getX() / localLength));
        v.setY((float) (v.getY() / localLength));
        v.setZ((float) (v.getZ() / localLength));
        v.setV((float) (v.getV() / localLength));
    }


    public Vector3f vectorMultiplication(final Vector3f a, final Vector3f b) {
        return new Vector3f(a.getY() * b.getZ() - a.getZ() * b.getY(),
                -1 * (a.getX() * b.getZ() - a.getZ() * b.getX()),
                a.getX() * b.getY() - a.getY() * b.getX());
    }

    public Float vectorDot(final Vector3f a, final Vector3f b) {
        return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
    }


}
