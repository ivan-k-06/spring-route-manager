package com.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "coordinates_x")),
            @AttributeOverride(name = "y", column = @Column(name = "coordinates_y"))
    })
    private Coordinates coordinates;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate = ZonedDateTime.now();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "from_x")),
            @AttributeOverride(name = "y", column = @Column(name = "from_y")),
            @AttributeOverride(name = "z", column = @Column(name = "from_z"))
    })
    private Location from;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "to_x")),
            @AttributeOverride(name = "y", column = @Column(name = "to_y")),
            @AttributeOverride(name = "z", column = @Column(name = "to_z"))
    })
    private Location to;

    @Column(nullable = false)
    private Float distance;

    @Column(name = "owner_login", nullable = false)
    private String owner;
}