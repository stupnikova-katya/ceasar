package ru.caesar.util;

import ru.caesar.domain.Ceaser;
import ru.caesar.side.Sender;

import java.io.File;
import java.util.*;

/**
 * Класс, реализующий шифрование трех случайных изображений.
 */
public class GeneratorUtil {

    /**
     * Зашифровать три случайных изображения (создать три бинарных файла со сжатыми зашифрованными векторами).
     */
    public static void generate3BinFiles() {
        Random rand = new Random();
        Sender sender = new Sender();
        Ceaser ceaser = new Ceaser();
        Set<Integer> indx = new HashSet<>(3);
        do {
            int ind = 1 + rand.nextInt(10);
            indx.add(ind);
        } while(indx.size() != 3);
        int count = 0;
        for (Integer ind : indx) {
            int key = 100 + rand.nextInt(10000);
            String outputFileName = "src/resources/generated/" + count + "File.bin";
            String encodedFileName = "src/resources/generated/" + count + "FileEnc.bin";
            sender.saveBinaryFile("src/resources/Swift" + ind + ".png", outputFileName);
            ceaser.encode(outputFileName, encodedFileName, key);
            File file = new File(outputFileName);
            file.delete();
            count++;
        }
    }
}
