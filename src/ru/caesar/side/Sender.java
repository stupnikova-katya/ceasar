package ru.caesar.side;

import ru.caesar.domain.ImageInBinaryVector;
import ru.caesar.util.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий процес передачи документа по факсу со сжатием.
 */
public class Sender {

    /**
     * Сохранить сжатый вектор изображения в бинарный файл.
     * @param inputFileName - наименование файла с изображением
     * @param outputFileName - наименование бинарного файла с вектором
     */
    public void saveBinaryFile(String inputFileName, String outputFileName) {
        BufferedImage image = readImageFile(inputFileName);
        ImageInBinaryVector binaryImageVector = getBinaryImageVector(image);
        FileUtils.saveImageInBinaryVector(binaryImageVector, outputFileName);
    }

    /**
     * Получить сжатый вектор изображения.
     * @param inputFileName - наименование файла с изображением
     * @return {@link ImageInBinaryVector}
     */
    public ImageInBinaryVector getBinaryImageVectorFromImage(String inputFileName) {
        BufferedImage image = readImageFile(inputFileName);
        return getBinaryImageVector(image);
    }

    /**
     * Считать изображение из файла
     * @param inputFileName - наименование файла с изображением
     * @return
     */
    private BufferedImage readImageFile(String inputFileName) {
        try {
            File file = new File(inputFileName);
            return ImageIO.read(file);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Получить сжатый вектор изображения.
     * @param image - изображение
     * @return {@link ImageInBinaryVector}
     */
    private ImageInBinaryVector getBinaryImageVector(BufferedImage image) {
        List<Integer> result = new ArrayList<>();
        int ind = 0;
        boolean lastWasWhite = true;
        boolean currentIsWhite;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                // Получаем цвет текущего пикселя
                Color color = new Color(image.getRGB(x, y));
                color.getRGB();
                // Применяем стандартный алгоритм для получения черно-белого изображения
                int grey = (int) (color.getRed() * 0.299 + color.getBlue() * 0.587 + color.getGreen() * 0.114);
                int resultColor = grey < 160 ? 0 : 255;
                if (ind == 0) {
                    // Начинаем всегда с числа белых символов
                    lastWasWhite = resultColor == 255;
                    result.add(resultColor == 255 ? 1 : 0);
                    ind++;
                    continue;
                }
                currentIsWhite = resultColor == 255;
                // Если текущий и предыдущий цвета совпадают, то наращиваем число битов по предыдущему индексу
                if (currentIsWhite == lastWasWhite) {
                    int count = result.get(ind-1);
                    result.set(ind - 1, count + 1);
                } else {
                    result.add(1);
                    ind++;
                }
                lastWasWhite = currentIsWhite;
            }
        }
        return new ImageInBinaryVector(result, image.getWidth(), image.getHeight());
    }
}
