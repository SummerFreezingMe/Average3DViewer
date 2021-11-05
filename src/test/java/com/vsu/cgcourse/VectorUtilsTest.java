package com.vsu.cgcourse;

import com.vsu.cgcourse.math.utils.Vector2f;
import com.vsu.cgcourse.math.utils.Vector3f;
import com.vsu.cgcourse.math.utils.Vector4f;
import com.vsu.cgcourse.math.utils.VectorUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class VectorUtilsTest {


    @Test
    void testVectorLength2() {
        double expectedResult = 5.0;
        VectorUtils util = new VectorUtils();
        Vector2f test = new Vector2f(3, 4);
        double length = util.vectorLength(test);
        Assertions.assertEquals(length, expectedResult);
    }

    @Test
    void testVectorLength3() {
        double expectedResult = 7.0;
        VectorUtils util = new VectorUtils();
        Vector3f test = new Vector3f(3, 2, 6);
        double length = util.vectorLength(test);
        Assertions.assertEquals(length, expectedResult);
    }

    @Test
    void testVectorLength4() {
        double expectedResult = 5.0;
        VectorUtils util = new VectorUtils();
        Vector4f test = new Vector4f(1, 2, 2, 4);
        double length = util.vectorLength(test);
        Assertions.assertEquals(length, expectedResult);
    }

    @Test
    void vectorSum2() {
        VectorUtils util = new VectorUtils();
        Vector2f expectedResult = new Vector2f(1, 2);
        Vector2f summand1 = new Vector2f(0, -1);
        Vector2f summand2 = new Vector2f(1, 3);
        Vector2f result = util.vectorSum(summand1, summand2, 1);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
    }

    @Test
    void vectorSum3() {
        VectorUtils util = new VectorUtils();
        Vector3f expectedResult = new Vector3f(1, 2, 3);
        Vector3f summand1 = new Vector3f(2, 2, 8);
        Vector3f summand2 = new Vector3f(1, 0, 5);
        Vector3f result = util.vectorSum(summand1, summand2, -1);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());
    }

    @Test
    void vectorSum4() {
        VectorUtils util = new VectorUtils();
        Vector4f expectedResult = new Vector4f(1, 3, 3, 7);
        Vector4f summand1 = new Vector4f(0, 2, 2, 8);
        Vector4f summand2 = new Vector4f(1, 1, 1, -1);
        Vector4f result = util.vectorSum(summand1, summand2, 1);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());
        Assertions.assertEquals(result.getV(), expectedResult.getV());
    }

    @Test
    void testScalarDenomination2() {
        VectorUtils util = new VectorUtils();
        Vector2f expectedResult = new Vector2f(1, 2);
        Vector2f test = new Vector2f(3, 6);
        float denominator = 3f;
        Vector2f result = util.scalarDenomination(test, denominator);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());

    }

    @Test
    void testScalarDenomination3() {
        VectorUtils util = new VectorUtils();
        Vector3f expectedResult = new Vector3f(1, 2, 4);
        Vector3f test = new Vector3f(2, 4, 8);
        float denominator = 2f;
        Vector3f result = util.scalarDenomination(test, denominator);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());

    }

    @Test
    void testScalarDenomination4() {
        VectorUtils util = new VectorUtils();
        Vector4f expectedResult = new Vector4f(0, 1, 2, 4);
        Vector4f test = new Vector4f(0, 2, 4, 8);
        float denominator = 2f;
        Vector4f result = util.scalarDenomination(test, denominator);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());
        Assertions.assertEquals(result.getV(), expectedResult.getV());
    }

    @Test
    void testScalarMultiply2() {
        VectorUtils util = new VectorUtils();
        Vector2f expectedResult = new Vector2f(18, -18);
        Vector2f test = new Vector2f(6, -6);
        float multiplier = 3f;
        Vector2f result = util.scalarMultiply(test, multiplier);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());

    }

    @Test
    void testScalarMultiply3() {
        VectorUtils util = new VectorUtils();
        Vector3f expectedResult = new Vector3f(-7, 28, 14);
        Vector3f test = new Vector3f(-1, 4, 2);
        float multiplier = 7f;
        Vector3f result = util.scalarMultiply(test, multiplier);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());

    }

    @Test
    void testScalarMultiply4() {
        VectorUtils util = new VectorUtils();
        Vector4f expectedResult = new Vector4f(0, 2, 4, 8);
        Vector4f test = new Vector4f(0, 1, 2, 4);
        float multiplier = 2f;
        Vector4f result = util.scalarMultiply(test, multiplier);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());
        Assertions.assertEquals(result.getV(), expectedResult.getV());
    }

    @org.junit.jupiter.api.Test
    void testNormalisation2() {
        VectorUtils util = new VectorUtils();
        Vector2f result = new Vector2f(3, 4);
        Vector2f expectedResult = new Vector2f(3f / 5f, 4f / 5f);
        util.normalisation(result);
        Assertions.assertTrue(result.equals(expectedResult));


    }

    @org.junit.jupiter.api.Test
    void testNormalisation3() {
        VectorUtils util = new VectorUtils();
        Vector3f result = new Vector3f(2, 6, 3);
        Vector3f expectedResult = new Vector3f(2f / 7f, 6f / 7f, 3f / 7f);
        util.normalisation(result);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @org.junit.jupiter.api.Test
    void testNormalisation4() {
        VectorUtils util = new VectorUtils();
        Vector4f result = new Vector4f(1, 2, 2, 4);
        Vector4f expectedResult = new Vector4f(0.2f, 0.4f, 0.4f, 0.8f);
        util.normalisation(result);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @org.junit.jupiter.api.Test
    void vectorMultiplication() {
        VectorUtils util = new VectorUtils();
        Vector3f expectedResult = new Vector3f(-3, 6, -3);
        Vector3f a = new Vector3f(1, 2, 3);
        Vector3f b = new Vector3f(4, 5, 6);
        Vector3f result = util.vectorMultiplication(a, b);
        Assertions.assertEquals(result.getX(), expectedResult.getX());
        Assertions.assertEquals(result.getY(), expectedResult.getY());
        Assertions.assertEquals(result.getZ(), expectedResult.getZ());
    }
}