package ru.caesar.domain;

import ru.caesar.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий шифрование Цезаря.
 */
public class Ceaser {

    /**
     * Зашифровать сжатый вектор изображения и сохранить в бин. файл.
     * @param inputFileName - наименование файла с вектором - источником
     * @param outputFileName - наименование результирующего файла
     * @param key - значение ключа
     */
    public void encode(String inputFileName, String outputFileName, Integer key) {
        ImageInBinaryVector binaryVector = FileUtils.readImageInBinaryVector(inputFileName);
        ImageInBinaryVector encodedVector = createEncodedVector(binaryVector, key);
        FileUtils.saveImageInBinaryVector(encodedVector, outputFileName);
    }

    /**
     * Расшифровать сжатый вектор изображения и сохранить в бин. файл.
     * @param inputFileName - наименование файла с вектором - источником
     * @param outputFileName - наименование результирующего файла
     * @param key - значение ключа
     */
    public void decode(String inputFileName, String outputFileName, Integer key) {
        ImageInBinaryVector binaryVector = FileUtils.readImageInBinaryVector(inputFileName);
        ImageInBinaryVector decodedVector = createDecodedVector(binaryVector, key);
        FileUtils.saveImageInBinaryVector(decodedVector, outputFileName);
    }

    /**
     * Сооздать зашифрованный сжатый вектор.
     * @param binaryVector - сжатый вектор
     * @param key - значение ключа
     * @return {@link ImageInBinaryVector}
     */
    private ImageInBinaryVector createEncodedVector(ImageInBinaryVector binaryVector, Integer key) {
        List<Integer> binaryImageVector = new ArrayList<>();
        for (Integer value : binaryVector.getBinaryImageVector()) {
            int num = value;
            num = (num + key) % Integer.MAX_VALUE;
            binaryImageVector.add(num);
        }
        return new ImageInBinaryVector(binaryImageVector, binaryVector.getWidth(), binaryVector.getHeight());
    }

    /**
     * Сооздать расшифрованный сжатый вектор.
     * @param binaryVector - сжатый вектор
     * @param key - значение ключа
     * @return {@link ImageInBinaryVector}
     */
    private ImageInBinaryVector createDecodedVector(ImageInBinaryVector binaryVector, Integer key) {
        List<Integer> binaryImageVector = new ArrayList<>();
        for (Integer value : binaryVector.getBinaryImageVector()) {
            int num = value;
            num -= key;
            if (num < 0) {
                num += Integer.MAX_VALUE;
            }
            binaryImageVector.add(num);
        }
        return new ImageInBinaryVector(binaryImageVector, binaryVector.getWidth(), binaryVector.getHeight());
    }

}
