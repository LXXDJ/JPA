package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // findAll 메소드로 조회 가능하지만, JPQL 설정 테스트를 위해 작성
//    @Query("SELECT c FROM Category c ORDER BY c.categoryCode")
    @Query(value = "SELECT * FROM tbl_category ORDER BY category_code", nativeQuery = true)
    List<Category> findAllCategory();
}
