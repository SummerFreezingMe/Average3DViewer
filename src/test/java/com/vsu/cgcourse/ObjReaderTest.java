package com.vsu.cgcourse;
import com.vsu.cgcourse.math.utils.Vector3f;
import com.vsu.cgcourse.obj_reader.ObjReader;
import com.vsu.cgcourse.obj_reader.ObjReaderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;

public class ObjReaderTest {

    @Test
    public void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("2", "f"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseTextureVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("2"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseTextureVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("2", "0", "5"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormal01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("7.1", "2.3", "4.0", "6"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseNormal02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("7.1", "2.3"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormal03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("7.1", "2.3", "s"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }


    @Test
    public void testParseVertex05() {

        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFace06() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "4508/4508 4509/4509 4510/4510 5312/5312";
        ArrayList<String> words = new ArrayList<>(Arrays.asList(wordInLine.split(" ")));

        try {
            ObjReader.parseFace(words, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 5312, 5312, 5312);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }

    }

    @Test
    public void testParseFace06_02() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "4508/4508 4509/4509 4510/-4510 5312/5312";
        ArrayList<String> words = new ArrayList<>(Arrays.asList(wordInLine.split(" ")));

        try {
            ObjReader.parseFace(words, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 5312, 5312, 5312);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }

    }

    // отрицательные индексы
    @Test
    public void testParseFaceWord07() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "4510/-12/4510";
        String[] words = wordInLine.split(" ");
        try {
            for(String s : words) {
                ObjReader.parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 4510, 4510, 4510);
            }
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }

    }

    @Test
    public void testParseFaceWord07_02() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "-4510/10/4510"; //
        String[] words = wordInLine.split(" ");
        try {
            for(String s : words) {
                ObjReader.parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 4510, 4510, 4510);
            }
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }

    }

    @Test
    public void testParseFaceWord08() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "886/42/12";
        String[] words = wordInLine.split(" ");
        try {
            for(String s : words) {
                ObjReader.parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices,
                        onePolygonNormalIndices, 10, 100, 100, 100);
            }
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Vertex does not exist.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseFaceWord09() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "42/886/12";
        String[] words = wordInLine.split(" ");
        try {
            for(String s : words) {
                ObjReader.parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);
            }
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. VertexTexture does not exist.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord10() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "42/100/120";
        String[] words = wordInLine.split(" ");
        try {
            for(String s : words) {
                ObjReader.parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices,
                        onePolygonNormalIndices, 10, 100, 100, 100);
            }
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Normal does not exist.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }


    @Test
    public void testParseFaceWord11() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "12/ab/8";

        try {
            ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord12() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "12/88/1.4";

        try {
            ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord13() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "12//8";

        try {
            ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);
            ArrayList<Integer> expectedOnePolygonVertexIndices = new ArrayList<>(Arrays.asList(11));
            ArrayList<Integer> expectedOnePolygonNormalIndices = new ArrayList<>(Arrays.asList(7));

            Assertions.assertEquals(expectedOnePolygonVertexIndices, onePolygonVertexIndices);
            Assertions.assertEquals(expectedOnePolygonNormalIndices, onePolygonNormalIndices);
        } catch (ObjReaderException exception) {
            //String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            //Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord13_02() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "12/3/";

        try {
            ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);
            ArrayList<Integer> expectedOnePolygonVertexIndices = new ArrayList<>(Arrays.asList(11));
            ArrayList<Integer> expectedOnePolygonTextureVertexIndices = new ArrayList<>(Arrays.asList(2));

            Assertions.assertEquals(expectedOnePolygonVertexIndices, onePolygonVertexIndices);
            Assertions.assertEquals(expectedOnePolygonTextureVertexIndices, onePolygonTextureVertexIndices);
        } catch (ObjReaderException exception) {
            //String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            //Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord13_03() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "/12/3";

        try {
            ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);

        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }


    @Test
    public void testParseFaceWord14() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = ""; // в методе считывается как wordInLine.split("/")[0].equals("")

        try {
            ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord15() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "/"; // в методе считывается как wordInLine.split("/").length == 0

        try {
            ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord16() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "//";

        try {
            ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord17() {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        String wordInLine = "/-3";

        try {
            ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10, 100, 100, 100);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
}
