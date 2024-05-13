package com.ohgiraffers.nativequery.section01.simple;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NativeQueryRepositoryTests {
    @Autowired
    private NativeQueryRepository nativeQueryRepository;

    @DisplayName("결과 타입을 정의한 Native Query 테스트")
    @Test
    void testNativeQueryByResultType(){
        int menuCode = 10;
        Menu foundMenu = nativeQueryRepository.nativeQueryByResultType(menuCode);
        assertNotNull(foundMenu);
        System.out.println(foundMenu);
    }

    @DisplayName("결과 타입을 지정할 수 없는 Native Query 테스트")
    @Test
    void testNativeQueryByNoResultType(){
        List<Object[]> menuList = nativeQueryRepository.nativeQueryByNoResultType();
        assertNotNull(menuList);
        menuList.forEach(row -> {
            for(Object column : row) System.out.print(column + " ");
            System.out.println();
        });
    }

    @DisplayName("자동 결과 매핑을 사용한 Native Query 조회 테스트")
    @Test
    public void testNativeQueryByAutoMapping() {
        List<Object[]> categoryList = nativeQueryRepository.nativeQueryByAutoMapping();
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            for(Object col : row) System.out.print(col + "/");
            System.out.println();
        });
    }

    @DisplayName("수동 결과 매핑을 사용한 Native Query 조회 테스트")
    @Test
    public void testNativeQueryByManualMapping() {
        List<Object[]> categoryList = nativeQueryRepository.nativeQueryByManualMapping();
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            for(Object col : row) System.out.print(col + "/");
            System.out.println();
        });
    }
}
