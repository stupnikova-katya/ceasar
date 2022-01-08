package ru.caesar.side;

import ru.caesar.domain.ImageInBinaryVector;
import ru.caesar.util.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий процесс приема сжатого содержимого документа по факсу.
 */
public class Receiver {

    /**
     * Сохранить изображение из сжатого вектора, прочитанного из бинарного файла.
     * @param inputFileName - наименование файла со сжаным вектором
     * @param outputFileName - наименование файла с изображением
     */
    public void saveFileFromBinaryFile(String inputFileName, String outputFileName) {
        ImageInBinaryVector binaryImageVector = FileUtils.readImageInBinaryVector(inputFileName);
        BufferedImage image = getImageFromVector(binaryImageVector);
        FileUtils.saveImage(outputFileName, image);
    }

    /**
     * Получить изображение из сжатого вектора
     * @param binaryImageVector - сжатый вектор
     * @return {@link BufferedImage}
     */
    private BufferedImage getImageFromVector(ImageInBinaryVector binaryImageVector) {
        List<Integer> colorVector = new ArrayList<>();
        boolean lastWasWhite = false;
        for (Integer num : binaryImageVector.getBinaryImageVector()) {
            if (lastWasWhite) {
                addColors(num, colorVector, 0);
                lastWasWhite = false;
            } else {
                addColors(num, colorVector, 255);
                lastWasWhite = true;
            }
        }
        int[][] colorsIn2D = convertTo2D(colorVector, binaryImageVector.getWidth(), binaryImageVector.getHeight());
        BufferedImage result = new BufferedImage(binaryImageVector.getWidth(), binaryImageVector.getHeight(), 6);
        for (int x = 0; x < binaryImageVector.getWidth(); x++) {
            for (int y = 0; y < binaryImageVector.getHeight(); y++) {
                int col = colorsIn2D[x][y];
                Color color = new Color(col, col, col);
                result.setRGB(x, y, color.getRGB());
            }
        }
        return result;
    }

    /**
     * Добавить несколько идущих подряд пикселей одного цвета.
     * @param num - число подряд идущих пикселей
     * @param colors - общий массив пикселей
     * @param color - цвет: белый - 255\черный - 0
     */
    private void addColors(Integer num, List<Integer> colors, Integer color) {
        for (int i = 0; i < num; i++) {
            colors.add(color);
        }
    }

    /**
     * Перевести вектор пикселей в двумерную матрицу пикселей
     * @param vector - вектор пикселей
     * @param width - ширина изображения
     * @param height - высота изображения
     * @return int[][]
     */
    private int[][] convertTo2D(List<Integer> vector, Integer width, Integer height) {
        int[][] result = new int[width][height];
        int i = 0;
        int j = 0;
        for (Integer value : vector) {
            result[i][j] = value;
            j++;
            if (j > height - 1) {
                i++;
                j = 0;
            }
        }
        return result;
    }
}
