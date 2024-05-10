package com.ohgiraffers.associationmapping.section03.bidirection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BiDirectionServiceTests {
    @Autowired
    private BiDirectionService biDirectionService;

    @DisplayName("양방향 연관관계 매핑 조회 테스트(연관관계의 주인)")
    @Test
    void biDirectionFindTest1(){
        // given
        int menuCode = 10;

        // when
        // 진짜 연관관계 : 처음 조회시 부터 조인한 결과를 인출해 온다. (즉시 로딩)
        Menu foundMenu = biDirectionService.findMenu(menuCode);

        // then
        assertEquals(menuCode, foundMenu.getMenuCode());
    }

    @DisplayName("양방향 연관관계 매핑 조회 테스트(연관관계의 주인 아님)")
    @Test
    void biDirectionFindTest2(){
        // given
        int categoryCode = 10;

        // when
        // 가짜 연관관계 : 해당 엔터티를 조회하고 필요시 연관관계 엔터티를 조회하는 쿼리 실행 (지연로딩)
        Category foundCategory = biDirectionService.findCategory(categoryCode);

        // then
        assertEquals(categoryCode, foundCategory.getCategoryCode());
    }
}
