package ru.caesar.decoder;

import javafx.util.Pair;
import ru.caesar.domain.Ceaser;
import ru.caesar.domain.ImageInBinaryVector;
import ru.caesar.side.Receiver;
import ru.caesar.util.AnalysisUtil;
import ru.caesar.util.FileUtils;

import java.util.*;

/**
 * Класс, реализующий расшифровку 3-х зашифрованных изображений с использованием статистических параметров.
 */
public class Decoder {

    /** Статистические параметры незашифрованных векторов для 10 исследуемых изображений */
    private static final List<Pair<Integer, Integer>> SWIFT1 = new ArrayList<>(10);
    private static final List<Pair<Integer, Integer>> SWIFT2 = new ArrayList<>(10);
    private static final List<Pair<Integer, Integer>> SWIFT3 = new ArrayList<>(10);
    private static final List<Pair<Integer, Integer>> SWIFT4 = new ArrayList<>(10);
    private static final List<Pair<Integer, Integer>> SWIFT5 = new ArrayList<>(10);
    private static final List<Pair<Integer, Integer>> SWIFT6 = new ArrayList<>(10);
    private static final List<Pair<Integer, Integer>> SWIFT7 = new ArrayList<>(10);
    private static final List<Pair<Integer, Integer>> SWIFT8 = new ArrayList<>(10);
    private static final List<Pair<Integer, Integer>> SWIFT9 = new ArrayList<>(10);
    private static final List<Pair<Integer, Integer>> SWIFT10 = new ArrayList<>(10);
    private static final List<List<Pair<Integer, Integer>>> SWIFT_STATS = new ArrayList<>(10);

    static {
        SWIFT1.add(new Pair<>(2, 14845));
        SWIFT1.add(new Pair<>(3, 9802));
        SWIFT1.add(new Pair<>(4, 5549));
        SWIFT1.add(new Pair<>(5, 3625));
        SWIFT1.add(new Pair<>(1, 2981));
        SWIFT1.add(new Pair<>(6, 2399));
        SWIFT1.add(new Pair<>(32, 2312));
        SWIFT1.add(new Pair<>(7, 1779));
        SWIFT1.add(new Pair<>(11, 1746));
        SWIFT1.add(new Pair<>(12, 1639));

        SWIFT2.add(new Pair<>(2, 16557));
        SWIFT2.add(new Pair<>(3, 8761));
        SWIFT2.add(new Pair<>(4, 5540));
        SWIFT2.add(new Pair<>(1, 3851));
        SWIFT2.add(new Pair<>(5, 3667));
        SWIFT2.add(new Pair<>(6, 2886));
        SWIFT2.add(new Pair<>(32, 2614));
        SWIFT2.add(new Pair<>(12, 2393));
        SWIFT2.add(new Pair<>(16, 2082));
        SWIFT2.add(new Pair<>(11, 2043));

        SWIFT3.add(new Pair<>(2, 14845));
        SWIFT3.add(new Pair<>(3, 9802));
        SWIFT3.add(new Pair<>(4, 5549));
        SWIFT3.add(new Pair<>(5, 3625));
        SWIFT3.add(new Pair<>(1, 2981));
        SWIFT3.add(new Pair<>(6, 2399));
        SWIFT3.add(new Pair<>(32, 2312));
        SWIFT3.add(new Pair<>(7, 1779));
        SWIFT3.add(new Pair<>(11, 1746));
        SWIFT3.add(new Pair<>(12, 1639));

        SWIFT4.add(new Pair<>(2, 14743));
        SWIFT4.add(new Pair<>(3, 10552));
        SWIFT4.add(new Pair<>(4, 5620));
        SWIFT4.add(new Pair<>(5, 3464));
        SWIFT4.add(new Pair<>(1, 2661));
        SWIFT4.add(new Pair<>(6, 2192));
        SWIFT4.add(new Pair<>(32, 1822));
        SWIFT4.add(new Pair<>(7, 1676));
        SWIFT4.add(new Pair<>(11, 1565));
        SWIFT4.add(new Pair<>(12, 1479));

        SWIFT5.add(new Pair<>(2, 19321));
        SWIFT5.add(new Pair<>(3, 12214));
        SWIFT5.add(new Pair<>(4, 7393));
        SWIFT5.add(new Pair<>(5, 4579));
        SWIFT5.add(new Pair<>(1, 3770));
        SWIFT5.add(new Pair<>(32, 3178));
        SWIFT5.add(new Pair<>(6, 3073));
        SWIFT5.add(new Pair<>(7, 2292));
        SWIFT5.add(new Pair<>(33, 1981));
        SWIFT5.add(new Pair<>(11, 1960));

        SWIFT6.add(new Pair<>(2, 11441));
        SWIFT6.add(new Pair<>(3, 9120));
        SWIFT6.add(new Pair<>(4, 4168));
        SWIFT6.add(new Pair<>(5, 2634));
        SWIFT6.add(new Pair<>(1, 1745));
        SWIFT6.add(new Pair<>(6, 1534));
        SWIFT6.add(new Pair<>(7, 1363));
        SWIFT6.add(new Pair<>(10, 1291));
        SWIFT6.add(new Pair<>(11, 1273));
        SWIFT6.add(new Pair<>(9, 1159));

        SWIFT7.add(new Pair<>(2, 13360));
        SWIFT7.add(new Pair<>(3, 8792));
        SWIFT7.add(new Pair<>(4, 4495));
        SWIFT7.add(new Pair<>(5, 2890));
        SWIFT7.add(new Pair<>(1, 2571));
        SWIFT7.add(new Pair<>(6, 1993));
        SWIFT7.add(new Pair<>(32, 1691));
        SWIFT7.add(new Pair<>(11, 1653));
        SWIFT7.add(new Pair<>(12, 1614));
        SWIFT7.add(new Pair<>(7, 1582));

        SWIFT8.add(new Pair<>(2, 12590));
        SWIFT8.add(new Pair<>(3, 9275));
        SWIFT8.add(new Pair<>(4, 4267));
        SWIFT8.add(new Pair<>(5, 2722));
        SWIFT8.add(new Pair<>(1, 2266));
        SWIFT8.add(new Pair<>(6, 1703));
        SWIFT8.add(new Pair<>(7, 1530));
        SWIFT8.add(new Pair<>(11, 1380));
        SWIFT8.add(new Pair<>(10, 1272));
        SWIFT8.add(new Pair<>(9, 1227));

        SWIFT9.add(new Pair<>(2, 8425));
        SWIFT9.add(new Pair<>(3, 6604));
        SWIFT9.add(new Pair<>(4, 3035));
        SWIFT9.add(new Pair<>(5, 2126));
        SWIFT9.add(new Pair<>(1, 1437));
        SWIFT9.add(new Pair<>(6, 1133));
        SWIFT9.add(new Pair<>(7, 1023));
        SWIFT9.add(new Pair<>(11, 836));
        SWIFT9.add(new Pair<>(9, 707));
        SWIFT9.add(new Pair<>(10, 705));

        SWIFT10.add(new Pair<>(2, 19368));
        SWIFT10.add(new Pair<>(3, 11578));
        SWIFT10.add(new Pair<>(4, 6837));
        SWIFT10.add(new Pair<>(5, 4581));
        SWIFT10.add(new Pair<>(1, 3955));
        SWIFT10.add(new Pair<>(6, 3173));
        SWIFT10.add(new Pair<>(32, 2543));
        SWIFT10.add(new Pair<>(7, 2282));
        SWIFT10.add(new Pair<>(12, 2180));
        SWIFT10.add(new Pair<>(11, 2160));

        SWIFT_STATS.add(SWIFT1);
        SWIFT_STATS.add(SWIFT2);
        SWIFT_STATS.add(SWIFT3);
        SWIFT_STATS.add(SWIFT4);
        SWIFT_STATS.add(SWIFT5);
        SWIFT_STATS.add(SWIFT6);
        SWIFT_STATS.add(SWIFT7);
        SWIFT_STATS.add(SWIFT8);
        SWIFT_STATS.add(SWIFT9);
        SWIFT_STATS.add(SWIFT10);
    }

    /**
     * Расшифровать 3 зашифрованных изображения.
     */
    public void decodeFiles() {
        List<ImageInBinaryVector> imageVectors = new ArrayList<>(3);
        Ceaser ceaser = new Ceaser();
        Receiver receiver = new Receiver();
        for (int i = 0; i < 3; i++) {
            imageVectors.add(FileUtils.readImageInBinaryVector("src/resources/generated/" + i + "FileEnc.bin"));
        }
        int vectorNum = 0;
        for (ImageInBinaryVector vector : imageVectors) {
            long startTime = System.currentTimeMillis();
            Map<Integer, Integer> statistics = AnalysisUtil.getImageStatistics(vector);
            int count = 0;
            Set<Integer> swiftNums = new HashSet<>();
            for (Map.Entry<Integer, Integer> statValue : statistics.entrySet()) {
                if (count == 10) {
                    break;
                }
                for (int i = 0; i < 10; i++) {
                    List<Pair<Integer, Integer>> swiftStat = SWIFT_STATS.get(i);
                    if (swiftStat.get(count).getValue().equals(statValue.getValue())) {
                        swiftNums.add(i + 1);
                    }
                }
                count++;
            }
            if (!swiftNums.isEmpty()) {
                int keyNum = 1;
                for (Integer swiftNum : swiftNums) {
                    try {
                        int numOfFile = swiftNum;
                        int firstInEncoded = statistics.entrySet().iterator().next().getKey();
                        int firstInDecoded = SWIFT_STATS.get(numOfFile - 1).get(0).getKey();
                        int key =  firstInEncoded - firstInDecoded;
                        ceaser.decode("src/resources/generated/" + vectorNum + "FileEnc.bin",
                                "src/resources/generated/" + vectorNum + "FileDec.bin", key);
                        receiver.saveFileFromBinaryFile("src/resources/generated/" + vectorNum + "FileDec.bin",
                                "src/resources/generated/" + vectorNum + "FileDec.png");
                        long endTime = System.currentTimeMillis();
                        double seconds = (double) (endTime - startTime) / 1000;
                        printDecodingStatistics("Swift" +numOfFile + ".png", firstInEncoded, firstInDecoded,
                                key, keyNum, seconds);
                    } catch(Exception e) {
                        System.err.println(e.getLocalizedMessage());
                    }
                    keyNum++;
                }
            }
            vectorNum++;
        }
    }

    /**
     * Вывести сведения о дешифровке файла.
     * @param fileName - наименование файла - источника
     * @param firstInEncoded - наиб. частое значение в зашифр. виде
     * @param firstInDecoded - наиб. частое значение в расшифр. виде
     * @param correctKey - правильный ключ
     * @param keyNum - номер подошедшего ключа
     * @param seconds - время затраченное на расшифровку (в секундах)
     */
    private void printDecodingStatistics(String fileName, Integer firstInEncoded, Integer firstInDecoded,
                                         Integer correctKey, Integer keyNum, Double seconds) {
        System.out.println("Название файла: " + fileName);
        System.out.println("Наиболее частое значение в зашифрованном виде: " + firstInEncoded);
        System.out.println("Наиболее частое значение в расшифрованном виде: " + firstInDecoded);
        System.out.println("Правильный вариант ключа: " + correctKey);
        System.out.println("Номер подошедшего значения из таблицы с  частотным анализом незашифрованных сжатых файлов: " + keyNum);
        System.out.println("Время, затраченное на перебор ключей и расшифровку: " + seconds + " c.");
        System.out.println("=================================");
    }
}
