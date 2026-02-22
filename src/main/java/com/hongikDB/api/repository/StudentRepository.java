package com.hongikDB.api.repository;

import com.hongikDB.api.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByName(String name); //총인원수( 동명이인 포함해서 알 수 있음)
    long countByDegree(String degree);
    Optional<Student> findByEmail(String email); //unique로 취급 가능
}