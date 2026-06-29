package com.app.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Точка отправления/прибытия
 * @author Ivan Kirillov
 */
@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Location implements Serializable {
    private long x;
    private Integer y;
    private Float z;

    /**
     * Конструктор класса
     */
    public Location(long x, Integer y, Float z) {
        if (y == null) throw new IllegalArgumentException("y null");
        if (z == null) throw new IllegalArgumentException("z null");

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setY(Integer y) {
        if (y == null) throw new IllegalArgumentException("y null");
        this.y = y;
    }

    public void setZ(Float z) {
        if (z == null) throw new IllegalArgumentException("z null");
        this.z = z;
    }

    @Override
    public String toString() {
        return "\n   X - " + x
                + ";\n   Y - " + y
                + ";\n   Z - " + z;
    }
}

