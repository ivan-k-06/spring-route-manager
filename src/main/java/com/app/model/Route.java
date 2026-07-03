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
            @AttributeOverride(name = "latitude", column = @Column(name = "coord_lat")),
            @AttributeOverride(name = "longitude", column = @Column(name = "coord_lon"))
    })
    private Coordinates coordinates;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate = ZonedDateTime.now();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "from_name")),
            @AttributeOverride(name = "latitude", column = @Column(name = "from_lat")),
            @AttributeOverride(name = "longitude", column = @Column(name = "from_lon"))
    })
    private Location from;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "to_name")),
            @AttributeOverride(name = "latitude", column = @Column(name = "to_lat")),
            @AttributeOverride(name = "longitude", column = @Column(name = "to_lon"))
    })
    private Location to;

    @Column(nullable = false)
    private Float distance;

    @Column(name = "owner_login", nullable = false)
    private String owner;
}