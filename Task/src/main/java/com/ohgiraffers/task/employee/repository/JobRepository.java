package com.ohgiraffers.task.employee.repository;

import com.ohgiraffers.task.employee.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, String> {
    @Query(value = "SELECT * FROM job ORDER BY job_code", nativeQuery = true)
    List<Job> findAllJob();
}
