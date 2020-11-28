package com.anotherlineofcode.reportwildboar.repository;

import com.anotherlineofcode.reportwildboar.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

//@RepositoryRestResource(collectionResourceRel = "prediction", path = "prediction")
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

}
