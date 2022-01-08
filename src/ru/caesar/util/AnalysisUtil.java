package ru.caesar.util;

import ru.caesar.domain.ImageInBinaryVector;
import ru.caesar.side.Sender;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, реализующий логику для статистического анализа.
 */
public class AnalysisUtil {

    /**
     * Записать в файл статистические характеристики сжатых незашифрованных векторов для 10 исследуемых изображений.
     */
    public static void writeImageStatisticsInFile() {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= 10; i++) {
            Map<Integer, Integer> statistics = getImageStatistics("src/resources/Swift" + i + ".png");
            sb.append("Swift" + i + ".png");
            sb.append(System.lineSeparator());
            int count = 0;
            for(Map.Entry<Integer, Integer> entry : statistics.entrySet()) {
                if (count == 10) {
                    break;
                }
                sb.append(entry.getKey() + ": " + entry.getValue());
                sb.append(System.lineSeparator());
                count++;
            }
            sb.append(System.lineSeparator());
        }
        FileUtils.saveStatistics(sb.toString(), "Statistics.txt");
    }

    /**
     * Получить статистики сжатого незашифрованного вектора.
     * @param inputFileName - наименование файла с изображением
     * @return Map<Integer, Integer>
     */
    public static Map<Integer, Integer> getImageStatistics(String inputFileName) {
        Sender sender = new Sender();
        ImageInBinaryVector imageInBinaryVector = sender.getBinaryImageVectorFromImage(inputFileName);
        return getImageStatistics(imageInBinaryVector);
    }

    /**
     * Получить статистики сжатого незашифрованного вектора.
     * @param imageVector - сжатый вектор
     * @return Map<Integer, Integer>
     */
    public static Map<Integer, Integer> getImageStatistics(ImageInBinaryVector imageVector) {
        Map<Integer, Integer> statistics = getStatistics(imageVector.getBinaryImageVector());
        Map<Integer, Integer> result = sortByValueReversed(statistics);
        return result;
    }

    /**
     * Отсортировать статистику по убыванию.
     * @param map - словарь со статистич. данными
     * @param <K> - тип ключа
     * @param <V> - тип значения
     * @return
     */
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValueReversed(Map<K,V> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<K,V> comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Получить статистические характеристики из сжатого вектора.
     * @param vector - сжатый вектор
     * @return Map<Integer, Integer>
     */
    private static Map<Integer, Integer> getStatistics(List<Integer> vector) {
        Map<Integer, Integer> result = new HashMap<>();
        for (Integer value : vector) {
            if (result.containsKey(value)) {
                result.put(value, result.get(value) + 1);
            } else {
                result.put(value, 1);
            }
        }
        return result;
    }
}
