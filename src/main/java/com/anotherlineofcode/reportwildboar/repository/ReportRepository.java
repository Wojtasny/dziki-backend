package com.anotherlineofcode.reportwildboar.repository;

import com.anotherlineofcode.reportwildboar.model.Report;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "reports", path = "reports")
public interface ReportRepository extends PagingAndSortingRepository<Report, Long> {

    //List<Report> findByLastName(@Param("name") String name);

}
