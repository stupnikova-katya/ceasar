package ru.caesar.util;

import ru.caesar.domain.ImageInBinaryVector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Класс, реализующий логику работы с файлами.
 */
public class FileUtils {

    /**
     * Сохранить сжатый вектор в бинарный файл.
     * @param image - сжатый вектор изображения
     * @param outputFileName - наименование бинарного файла
     */
    public static void saveImageInBinaryVector(ImageInBinaryVector image, String outputFileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFileName));
            out.writeObject(image);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Прочитать сжатый вектор из бинарного файла.
     * @param inputFileName - наименование бинарного файла.
     * @return {@link ImageInBinaryVector}
     */
    public static ImageInBinaryVector readImageInBinaryVector(String inputFileName) {
        ImageInBinaryVector imageInBinaryVector = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(inputFileName));
            imageInBinaryVector = (ImageInBinaryVector) in.readObject();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return imageInBinaryVector;
    }

    /**
     * Сохранить изображение.
     * @param fileName - наименование файла с изображением
     * @param image - изображение
     */
    public static void saveImage(String fileName, BufferedImage image) {
        try {
            File output = new File(fileName);
            ImageIO.write(image, "png", output);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Сохранить файл со статистическими характеристиками.
     * @param statistics - статистические характеристики
     * @param fileName - наименование файла
     */
    public static void saveStatistics(String statistics, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/resources/" + fileName))) {
            bw.write(statistics);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
