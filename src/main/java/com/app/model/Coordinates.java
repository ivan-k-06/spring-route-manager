package com.app.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Класс представляющий координаты
 * @author Ivan Kirillov
 */
@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Coordinates implements Serializable {
    private double x;
    private int y; //Максимальное значение поля: 71

    /**
     * Конструктор класса
     */
    public Coordinates(double x, int y) {
        if (y > 71) throw new IllegalArgumentException("Y > 71");

        this.x = x;
        this.y = y;
    }

    public void setY(int y) {
        if (y > 71) throw new IllegalArgumentException("Y > 71");
        this.y = y;
    }

    @Override
    public String toString() {
        return "\n   X - " + x
                + ";\n   Y - " + y;
    }
}