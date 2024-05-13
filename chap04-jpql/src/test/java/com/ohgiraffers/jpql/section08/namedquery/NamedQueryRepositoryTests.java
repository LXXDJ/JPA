package com.ohgiraffers.jpql.section08.namedquery;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NamedQueryRepositoryTests {
    @Autowired
    private NamedQueryRepository namedQueryRepository;

    @DisplayName("동적쿼리 조회 테스트")
    @Test
    public void testSelectByDynamicQuery(){
//        String searchName = null; // 전체조회
//        int searchCode = 0;

        String searchName = "마늘";
        int searchCode = 4;

        List<Menu> menuList = namedQueryRepository.selectByDynamicQuery(searchName, searchCode);
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @DisplayName("네임드쿼리 조회 테스트")
    @Test
    public void testSelectNamedQuery(){
        List<Menu> menuList = namedQueryRepository.selectByNamedQuery();
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @DisplayName("네임드쿼리 XML 파일을 이용한 조회 테스트")
    @Test
    public void testSelectNamedQueryWithXML(){
        int menuCode = 3;
        Menu foundMenu = namedQueryRepository.selectByNamedQueryWithXML(menuCode);
        assertNotNull(foundMenu);
        System.out.println(foundMenu);
    }
}
