package com.ohgiraffers.jpql.section07.subquery;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SubqueryRepositoryTests {
    @Autowired
    private SubqueryRepository subqueryRepository;

    @DisplayName("서브쿼리 조회 테스트")
    @Test
    public void testSelectWithSubquery(){
        String categoryName = "한식";
        List<Menu> menuList = subqueryRepository.selectWithSubQuery(categoryName);
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
