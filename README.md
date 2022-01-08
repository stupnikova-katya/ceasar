# Ceasar (Факс Цезаря)

#1 Процесс передачи документа по факсу со сжатием смоделирован в классе ru.caesar.side.Sender и реализует следующий набор операций:
· открыть изображение (скриншот страницы книги или документа в формате А4)ru.caesar.side.Sender#readImageFile;

· преобразовать его в бинарное изображение ru.caesar.side.Sender#getBinaryImageVectorFromImage;

· представить результат в виде вектора ru.caesar.side.Sender#getBinaryImageVectorFromImage;

· обойти данный вектор и сформировать на его основе вектор в формате: 
[число белых пикселей, число черных пикселей, число белых пикселей, число черных пикселей...] ru.caesar.side.Sender.getBinaryImageVectorFromImage;

· сохранить содержимое вектора в бинарный файл ru.caesar.util.FileUtils.saveImageInBinaryVector.

#2 Процесс приема сжатого содержимого документа по факсу смоделирован в классе ru.caesar.side.Receiver и реализует следующий набор операций:
· прочитать бинарный файл в вектор ru.caesar.util.FileUtils#readImageInBinaryVector;

· Обратить операцию сжатия из пункта 1 ru.caesar.side.Receiver#getImageFromVector;

· представить полученный вектор в виде матрицы-бинарного изображения ru.caesar.side.Receiver#convertTo2D;

· сохранить полученное изображение в файл ru.caesar.util.FileUtils#saveImage.

# 3 Процесс шифрования кодом Цезаря (ru.caesar.domain.Ceaser#encode) смоделирован в классе ru.caesar.domain.Ceaser и реализует следующий набор операций
· прочитать бинарный файл в вектор V ru.caesar.util.FileUtils#readImageInBinaryVector;

· Для каждого значения Vi прибавлять к нему значение ключа k по модулю максимального числа для вашего типа данных max_val: ( Vi + k ) mod max_val ru.caesar.domain.Ceaser#createDecodedVector;

· сохранить полученный вектор в бинарный файл ru.caesar.util.FileUtils#saveImageInBinaryVector.

# 4 Процесс дешифрования кодом Цезаря (ru.caesar.domain.Ceaser#decode) смоделирован в классе ru.caesar.domain.Ceaser

# 5 Произведен частотный анализ содержимого сжатого незашифрованного вектора (ru.caesar.util.AnalysisUtil#writeImageStatisticsInFile).
· Взяты 10 файлов-картинок с текстом с близкими свойствами (похожий размер шрифта и одинаковый формат страницы): src/resources/Swift1.png - src/resources/Swift10.png.

· Изображения были сжаты в соответствии с пунктом 1.

· Для их сжатого представления было посчитано, сколько раз встречается каждое значение.

· Таким образом была найдена первая десятка наиболее встречающихся значений в сжатых незашифрованных векторах.
Все полученные статистики сохранены в файл src/resources/Statistics.txt и продублированы в ru.caesar.decoder.Decoder#SWIFT_STATS

# 6 Произведен анализ 3-х случайных зашифрованных файлов, с использованием известных статистических параметров распределения значений в незашифрованных (ru.caesar.decoder.Decoder#decodeFiles).
· Посчитаны для зашифрованного файла, сколько раз встречается там каждое значение.

· После перебора первые несколько наиболее встречающихся значений в сжатых незашифрованных векторах 
они были проверены на соответствие наиболее частому значению из зашифрованного.

· Ключ вычислен так, если бы это значение в зашифрованном файле соответствовало текущему из проверяемых.

· Попытаться расшифровать файл этим ключом. Если не выбрасывается исключение при попытке конвертировать 
вектор в изображение, то данный кандидат является успешным, и изображение сохраняется в виде файла.