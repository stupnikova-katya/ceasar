package ru.caesar.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс - представление сжатого вектора.
 */
public class ImageInBinaryVector implements Serializable {

    private static final long serialVersionUID = 2L;

    /** Ширина исходного изображения (в пикселях) */
    private Integer width;

    /** Высота исходного изображения (в пикселях) */
    private Integer height;

    /**
     * Сжатый вектор вида [число белых пикселей, число черных пикселей, число белых пикселей, число черных пикселей...].
     * Белые пиксели всегда идут в начале.
     */
    private List<Integer> binaryImageVector = new ArrayList<>();

    public ImageInBinaryVector() {}

    public ImageInBinaryVector(List<Integer> binaryImageVector, Integer width, Integer height) {
        this.binaryImageVector = binaryImageVector;
        this.width = width;
        this.height = height;
    }

    public List<Integer> getBinaryImageVector() {
        return binaryImageVector;
    }

    public void setBinaryImageVector(List<Integer> binaryImageVector) {
        this.binaryImageVector = binaryImageVector;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "ImageInBinaryVector{" +
                "binaryImageVector=" + binaryImageVector +
                '}';
    }
}
