package com.vsu.cgcourse.obj_reader;

import com.vsu.cgcourse.math.utils.Vector2f;
import com.vsu.cgcourse.math.utils.Vector3f;
import com.vsu.cgcourse.model.Mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Кстати, проверяйте за мной стиль кода. Перелезаю на него для наших практик и могу что-то не замечать.
// А уж если кто-то найдет серьезные ошибки в коде (а они могут быть, тем более сейчас 2:15 утра), будем награждать доп.баллами

public class ObjReader {

    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    public static Mesh read(String fileContent) {
        Mesh result = new Mesh();

        // вводим счетчик кол-ва вершин и нормалей в списках (test08)
        int countVertex = 0;
        int countTextureVertex = 0;
        int countNormal = 0;

        int lineInd = 0;
        Scanner scanner = new Scanner(fileContent);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(line.split("\\s+"))); //один и более пробелов допускаются
            if (wordsInLine.isEmpty())
                continue;

            final String token = wordsInLine.get(0);
            wordsInLine.remove(0);

            ++lineInd;
            switch (token) {
                // Обратите внимание!
                // Для структур типа вершин методы написаны так, чтобы ничего не знать о внешней среде.
                // Они принимают только то, что им нужно для работы, а возвращают только то, что могут создать.
                // Исключение - индекс строки. Он прокидывается, чтобы выводить сообщение об ошибке.
                // Могло быть иначе. Например, метод parseVertex мог вместо возвращения вершины принимать вектор вершин
                // модели или сам класс модели, работать с ним.
                // Но такой подход может привести к большему количеству ошибок в коде. Например, в нем что-то может
                // тайно сделаться с классом модели.
                // А еще это портит читаемость
                // И не стоит забывать про тесты. Чем проще вам задать данные для теста, проверить, что метод рабочий,
                // тем лучше.
                case OBJ_VERTEX_TOKEN -> {
                    result.vertices.add(parseVertex(wordsInLine, lineInd));
                    countVertex = result.vertices.size();
                }
                case OBJ_TEXTURE_TOKEN -> {
                    result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
                    countTextureVertex = result.textureVertices.size();
                }
                case OBJ_NORMAL_TOKEN -> {
                    result.normals.add(parseNormal(wordsInLine, lineInd));
                    countNormal = result.normals.size();
                }
                // А здесь описанное выше правило нарушается, и это плохо. Например, очевидно, что тестировать такой
                // метод сложнее.
                // Подумайте и перепишите его так, чтобы с ним было легче работать.
                case OBJ_FACE_TOKEN -> {
                    ArrayList<Integer> onePolygonVertexIndices = new ArrayList<Integer>();
                    ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<Integer>();
                    ArrayList<Integer> onePolygonNormalIndices = new ArrayList<Integer>();
                    parseFace(wordsInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices,
                            lineInd, countVertex, countTextureVertex, countNormal);
                    result.polygonVertexIndices.add(onePolygonVertexIndices);
                    result.polygonTextureVertexIndices.add(onePolygonTextureVertexIndices);
                    result.polygonNormalIndices.add(onePolygonNormalIndices);
                }
                default -> {
                }
            }
        }
        return result;
    }

    // Всем методам кроме основного я поставил модификатор доступа protected, чтобы обращаться к ним в тестах
    public static Vector3f parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() > 3) {
                throw new IndexOutOfBoundsException();
            }
            return new Vector3f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd); // Не удалось проанализировать значение с плавающей запятой.

        } catch (IndexOutOfBoundsException e) {
            if (wordsInLineWithoutToken.size() < 3) {
                throw new ObjReaderException("Too few vertex arguments.", lineInd); // слишком мало аргументов вершин
            } else {
                throw new ObjReaderException("Too many vertex arguments.", lineInd);
            }
        }

    }

    public static Vector2f parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() == 3) {
                wordsInLineWithoutToken.remove(2);

            } else if (wordsInLineWithoutToken.size() > 3) {
                throw new IndexOutOfBoundsException();
            }
            return new Vector2f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)));

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch (IndexOutOfBoundsException e) {
            if (wordsInLineWithoutToken.size() < 2) {
                throw new ObjReaderException("Too few texture vertex arguments.", lineInd); // слишком мало аргументов вершин
            } else {
                throw new ObjReaderException("Too many texture vertex arguments.", lineInd);
            }
        }
    }

    public static Vector3f parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() > 3) {
                throw new IndexOutOfBoundsException();
            }
            return new Vector3f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch (IndexOutOfBoundsException e) {
            if (wordsInLineWithoutToken.size() < 3) {
                throw new ObjReaderException("Too few normal arguments.", lineInd); // слишком мало аргументов вершин
            } else {
                throw new ObjReaderException("Too many normal arguments.", lineInd);
            }
        }
    }

    public static void parseFace(
            final ArrayList<String> wordsInLineWithoutToken,
            ArrayList<Integer> onePolygonVertexIndices,
            ArrayList<Integer> onePolygonTextureVertexIndices,
            ArrayList<Integer> onePolygonNormalIndices,
            int lineInd,
            int countVertex,
            int countTextureVertex,
            int countNormal) {


        for (String s : wordsInLineWithoutToken) {
            parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd,
                    countVertex, countTextureVertex, countNormal);
        }


        /*
        polygonVertexIndices.add(onePolygonVertexIndices);
        polygonTextureVertexIndices.add(onePolygonTextureVertexIndices);
        polygonNormalIndices.add(onePolygonNormalIndices);

         */
    }

    // Обратите внимание, что для чтения полигонов я выделил еще один вспомогательный метод.
    // Это бывает очень полезно и с точки зрения структурирования алгоритма в голове, и с точки зрения тестирования.
    // В радикальных случаях не бойтесь выносить в отдельные методы и тестировать код из одной-двух строчек.
    public static void parseFaceWord(
            String wordInLine,
            ArrayList<Integer> onePolygonVertexIndices,
            ArrayList<Integer> onePolygonTextureVertexIndices,
            ArrayList<Integer> onePolygonNormalIndices,
            int lineInd,
            int countVertex,
            int countTextureVertex,
            int countNormal) {
        try {
            String[] wordIndices = wordInLine.split("/");
            if (wordIndices.length == 0 || wordIndices[0].equals("")) {
                throw new IndexOutOfBoundsException();
            }

            if ((Integer.parseInt(wordIndices[0]) - 1) < -1 ||
                    (wordIndices.length > 1 && !wordIndices[1].equals("") && (Integer.parseInt(wordIndices[1]) - 1) < -1) ||
                    (wordIndices.length > 2 && (Integer.parseInt(wordIndices[2]) - 1) < -1)) {
                throw new IllegalArgumentException();
            }
            switch (wordIndices.length) {
                case 1 -> {

                    if (Integer.parseInt(wordIndices[0]) <= countVertex) {
                        onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    } else {
                        throw new ObjReaderException("Vertex does not exist.", lineInd);
                    }
                }
                case 2 -> {
                    if (Integer.parseInt(wordIndices[0]) <= countVertex) {
                        onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    } else {
                        throw new ObjReaderException("Vertex does not exist.", lineInd);
                    }
                    if (Integer.parseInt(wordIndices[1]) <= countTextureVertex) {
                        onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                    } else {
                        throw new ObjReaderException("VertexTexture does not exist.", lineInd);
                    }
                }
                case 3 -> {
                    if (Integer.parseInt(wordIndices[0]) <= countVertex) {
                        onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    } else {
                        throw new ObjReaderException("Vertex does not exist.", lineInd);
                    }
                    if (Integer.parseInt(wordIndices[2]) <= countNormal) {
                        onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);
                    } else {
                        throw new ObjReaderException("Normal does not exist.", lineInd);
                    }
                    if (!wordIndices[1].equals("")) {
                        if (Integer.parseInt(wordIndices[1]) <= countTextureVertex) {
                            onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                        } else {
                            throw new ObjReaderException("VertexTexture does not exist.", lineInd);
                        }
                    }
                }
                default -> {
                    throw new ObjReaderException("Invalid element size.", lineInd);
                }
            }

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse int value.", lineInd);
        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few arguments.", lineInd);
        } catch (IllegalArgumentException e) {
            throw new ObjReaderException("Invalid value.", lineInd);
        }
    }
}
