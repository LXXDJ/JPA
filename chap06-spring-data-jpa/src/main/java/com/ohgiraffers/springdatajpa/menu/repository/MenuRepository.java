package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* JpaRepository<엔터티, Id타입> */
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    // 파라미터로 전달받은 가격을 초과하는 메뉴 목록 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

    // 정렬(메뉴가격)
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

    // + 정렬 기준 추가(메뉴가격 내림차순)
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);
}
