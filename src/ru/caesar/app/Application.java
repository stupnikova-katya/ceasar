package ru.caesar.app;

import ru.caesar.decoder.Decoder;

/**
 * Основной класс приложения.
 */
public class Application {

    /**
     * Метод, запускающий расшифровку 3-х зашифрованных изображений с использованием статистических параметров.
     */
    public void start() {
//        Sender sender = new Sender();
//        sender.saveBinaryFile("src/resources/Swift1.png", "src/resources/Swift.bin");

//        Receiver receiver = new Receiver();
//        receiver.saveFileFromBinaryFile("src/resources/Page1.bin", "src/resources/Page1_grey.png");

//        Ceaser ceaser = new Ceaser();
//        ceaser.encode("src/resources/Swift.bin", "src/resources/SwiftEnc.bin", 45);
//        ceaser.decode("src/resources/SwiftEnc.bin", "src/resources/SwiftDec.bin",45);
//        Receiver receiver = new Receiver();
//        receiver.saveFileFromBinaryFile("src/resources/SwiftDec.bin", "src/resources/SwiftDec.png");

//        AnalysisUtil.writeImageStatisticsInFile();
//        GeneratorUtil.generate3BinFiles();

        Decoder decoder = new Decoder();
        decoder.decodeFiles();
    }
}
