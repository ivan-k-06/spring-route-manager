package com.app.repository;

import com.app.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findAllByOrderByIdAsc();

    long countByOwner(String owner);

    @Query("SELECT COALESCE(SUM(r.distance), 0) FROM Route r WHERE r.owner = ?1")
    Double sumDistanceByOwner(String owner);

    @Query("SELECT COALESCE(MAX(r.distance), 0) FROM Route r WHERE r.owner = ?1")
    Double maxDistanceByOwner(String owner);

    @Query("SELECT COALESCE(MIN(r.distance), 0) FROM Route r WHERE r.owner = ?1 AND r.distance > 0")
    Double minDistanceByOwner(String owner);

    @Query("SELECT COALESCE(AVG(r.distance), 0) FROM Route r WHERE r.owner = ?1")
    Double avgDistanceByOwner(String owner);
}