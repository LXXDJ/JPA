package com.ohgiraffers.section02.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityManagerCRUDTests {

    private EntityManagerCRUD crud;

    @BeforeEach
    void initManager(){
        this.crud = new EntityManagerCRUD();
    }

//    @Test
//    @DisplayName("메뉴 코드로 메뉴 조회 테스트")
//    void testFindMethodByMenuCode(){
//        // given
//        int menuCode = 2;
//
//        // when
//        Menu foundMenu = crud.findMenuByMenuCode(menuCode);
//
//        // then
//        assertEquals("우럭스무디", foundMenu.getMenuName());
//    }

    @ParameterizedTest // 하나의 테스트 메소드로 여러개의 파라미터에 대한 테스트 가능
    @CsvSource({"2,2", "3,3"})
    @DisplayName("메뉴 코드로 메뉴 조회 테스트")
    void testFindMethodByMenuCode(int menuCode, int expected){
        // given

        // when
        Menu foundMenu = crud.findMenuByMenuCode(menuCode);

        // then
        assertEquals(expected, foundMenu.getMenuCode());

        System.out.println("foundMenu = " + foundMenu);
    }

    private static Stream<Arguments> newMenu(){
        return Stream.of(
                Arguments.of(
                        "신메뉴",
                        35000,
                        4,
                        "Y"
                )
//                ,Arguments.of(    // 여러개 일때
//                        "신메뉴1",
//                        35000,
//                        4,
//                        "Y"
//                )
        );
    }

    @DisplayName("새로운 메뉴 추가 테스트")
    @ParameterizedTest
    @MethodSource("newMenu")    // 위에 있는 newMenu 메소드를 전달받아서 testRegist의 Source로 쓰겠다.
    void testRegist(String menuName, int menuPrice, int categoryCode, String orderableStatus){
        // when
        Menu newMenu = new Menu(menuName, menuPrice, categoryCode, orderableStatus);
        Long count = crud.saveAndReturnAllCount(newMenu);

        // then
        assertEquals(31, count);
    }

    @DisplayName("메뉴 이름 수정 테스트")
    @ParameterizedTest
    @CsvSource("31, 변경된 이름")
    void testModifyMenuName(int menuCode, String menuName){
        // when
        Menu modifiedMenu = crud.modifyMenuName(menuCode, menuName);

        // then
        assertEquals(menuName, modifiedMenu.getMenuName());
    }

    @DisplayName("메뉴 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = {23, 30, 31})
    void testRemoveMenu(int menuCode){
        // when
        Long count = crud.removeAndReturnAllCount(menuCode);

        // then
        assertEquals(22, count);
    }
}
