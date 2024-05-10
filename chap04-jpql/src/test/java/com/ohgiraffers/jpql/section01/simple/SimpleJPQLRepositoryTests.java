package com.ohgiraffers.jpql.section01.simple;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SimpleJPQLRepositoryTests {
    @Autowired
    private SimpleJPQLRepository simpleJPQLRepository;

    @DisplayName("TypedQuery를 이용한 단일행, 단일컬럼 조회 테스트")
    @Test
    void testSelectSingleMenuByTypedQuery(){
        String menuName = simpleJPQLRepository.selectSingleMenuByTypedQuery();
        assertEquals("한우딸기국밥", menuName);
    }

    @DisplayName("Query를 이용한 단일행, 단일컬럼 조회 테스트") // 결과값의 타입을 지정하지 못하는 상황에는 Query 이용
    @Test
    void testSelectSingleMenuByQuery(){
        Object menuName = simpleJPQLRepository.selectSingleMenuByQuery();
        assertEquals("한우딸기국밥", menuName);
    }

    @DisplayName("TypedQuery를 이용한 단일행 조회 테스트")
    @Test
    void selectSingleRowByTypedQuery(){
        Menu menu = simpleJPQLRepository.selectSingleRowByTypedQuery();
        System.out.println("menu = " + menu);
        assertEquals("한우딸기국밥", menu.getMenuName());
    }

    @DisplayName("TypedQuery를 이용한 다중행 조회 테스트")
    @Test
    void testSelectMultiRowByTypedQuery(){
        List<Menu> menuList = simpleJPQLRepository.selectMultiRowByTypedQuery();
        System.out.println("menuList = " + menuList);
        assertNotNull(menuList);
    }

    @DisplayName("DISTINCT 연산자 사용 조회 테스트")
    @Test
    void testSelectUsingDistinct(){
        List<Integer> categoryList = simpleJPQLRepository.selectUsingDistinct();
        System.out.println("categoryList = " + categoryList);
        assertNotNull(categoryList);
    }

    @DisplayName("IN 연산자 사용 조회 테스트")
    @Test
    void testSelectUsingIn(){
        List<Menu> menuList = simpleJPQLRepository.testSelectUsingIn();
        System.out.println("menuList = " + menuList);
        assertNotNull(menuList);
    }

    @DisplayName("Like 연산자 사용 조회 테스트")
    @Test
    void testSelectUsingLike(){
        List<Menu> menuList = simpleJPQLRepository.testSelectUsingLike();
        System.out.println("menuList = " + menuList);
        assertNotNull(menuList);
    }
}
