package com.ohgiraffers.jpql.section05.groupfunction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GroupFunctionRepositoryTests {
    
    @Autowired
    private GroupFunctionRepository groupFunctionRepository;
    
    @DisplayName("특정 카테고리에 등록된 메뉴 개수 조회")
    @Test
    void testCountMenuOfCategory(){
        int categoryCode = 44;
        long countOfMenu = groupFunctionRepository.countMenuOfCategory(categoryCode);
        
        assertTrue(countOfMenu >= 0);
        System.out.println("countOfMenu = " + countOfMenu);
    }

    @DisplayName("COUNT 외의 다른 그룹함수 조회결과가 없는 경우")
    @Test
    void testOtherWithNoResult(){
//        int categoryCode = 4;
//        long sumOfMenu = groupFunctionRepository.otherWithNoResult(categoryCode);
//        assertTrue(sumOfMenu >= 0);
//        System.out.println("sumOfMenu = " + sumOfMenu);

        int categoryCode = 44;
        assertDoesNotThrow( () -> {
            Long sumOfMenu = groupFunctionRepository.otherWithNoResult(categoryCode);
            System.out.println("sumOfMenu = " + sumOfMenu);
        });
    }

    @DisplayName("HAVING절 조회 테스트")
    @Test
    public void testSelectByGroupByHaving(){
        // int minPrice = 50000; // int 타입도 잘 작동한다.
        long minPrice = 50000L;
        List<Object[]> sumPriceOfCategoryList = groupFunctionRepository.selectByGroupByHaving(minPrice);
        assertNotNull(sumPriceOfCategoryList);
        sumPriceOfCategoryList.forEach( row -> {
            for (Object column : row) System.out.print(column + " ");
            System.out.println();
        });
    }
}
