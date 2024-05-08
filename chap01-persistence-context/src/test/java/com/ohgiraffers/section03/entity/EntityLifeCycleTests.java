package com.ohgiraffers.section03.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class EntityLifeCycleTests {
    private EntityLifeCycle lifeCycle;

    @BeforeEach
    void setup(){
        this.lifeCycle = new EntityLifeCycle();
    }

    @DisplayName("비영속 테스트")
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testTransient(int menuCode) {
        // when
        Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);

        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
        );

        EntityManager entityManager = lifeCycle.getManagerInstance();

        // then
        assertNotEquals(foundMenu, newMenu);
        assertTrue(entityManager.contains(foundMenu));  // founcMenu는 영속성 컨텍스트에서 관리되는 영속 상태의 객체
        assertFalse(entityManager.contains(newMenu));    // newMenu는 영속성 컨텍스트에서 관리되지 않는 비영속 상태의 객체
    }

    @DisplayName("다른 엔터티 매니저가 관리하는 엔터티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testManagedOtherEntityManager(int menuCode){
        // when
        Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
        Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);

        // then
        assertNotEquals(menu1, menu2);
    }

    @DisplayName("같은 엔터티 매니저가 관리하는 엔터티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testManagedSameEntityManager(int menuCode){
        // given
        EntityManager entityManager = EntityManagerGenerator.getInstance();

        // when
        Menu menu1 = entityManager.find(Menu.class, menuCode);
        Menu menu2 = entityManager.find(Menu.class, menuCode);

        // then
        assertEquals(menu1, menu2);
    }

    @DisplayName("준영속화 detach 테스트")
    @ParameterizedTest
    @CsvSource({"11, 1000", "12, 1000"})
    void testDetachEntity(int menuCode, int menuPrice){
        // given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when
        entityTransaction.begin();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        // detach : 특정 엔터티만 준영속 상태 (영속성 컨텍스트가 관리하던 객체를 관리하지 않는 상태)로 만듦
        entityManager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);

        // flush : 영속성 컨텍스트의 상태를 DB로 내보낸다. commit 하지 않은 상태이므로 rollback 가능
        entityManager.flush();

        // then
        assertNotEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        entityTransaction.rollback();
    }

    @DisplayName("준영속화 detach 후 다시 영속화 테스트")
    @ParameterizedTest
    @CsvSource({"11, 1000", "12, 1000"})
    void testDetachAndMerge(int menuCode, int menuPrice){
        // given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when
        entityTransaction.begin();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        // detach : 특정 엔터티만 준영속 상태 (영속성 컨텍스트가 관리하던 객체를 관리하지 않는 상태)로 만듦
        entityManager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);

        /* 여기 추가 merge : 파라미터로 넘어온 준영속 엔터티 객체의 식별자 값으로 1차 캐시에서 엔터티 객체 조회
           (없으면 DB에서 조회하여 1차 캐시에 저장)
           조회한 영속 엔터티 객체에 준영속 상태의 엔터티 객체의 값을 병합한 뒤, 영속 엔터티 객체 반환
           혹은 조회할 수 없는 데이터라면 새로 생성해서 병합 */
        entityManager.merge(foundMenu);

        // flush : 영속성 컨텍스트의 상태를 DB로 내보낸다. commit 하지 않은 상태이므로 rollback 가능
        entityManager.flush();

        // then
        assertEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        entityTransaction.rollback();
    }

    @DisplayName("datach 후 merge한 데이터 update 테스트")
    @ParameterizedTest
    @CsvSource({"11, 하양민트초코죽", "12, 까만딸기탕후루"})
    void testMergeUpdate(int menuCode, String menuName){
        // given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        entityManager.detach(foundMenu);

        // when
        foundMenu.setMenuName(menuName);
        Menu refoundMenu = entityManager.find(Menu.class, menuCode);

        entityManager.merge(foundMenu); // refoundMenu로 조회해온 값이 foundMenu의 값과 병합되어 메뉴명이 변경됨

        // then
        assertEquals(menuName, refoundMenu.getMenuName());
    }

    @DisplayName("detach 후 merge한 데이터 save 테스트")
    @Test
    void testMergeSave(){
        // given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        Menu foundMenu = entityManager.find(Menu.class, 20);
        entityManager.detach(foundMenu);

        // when
        entityTransaction.begin();
        foundMenu.setMenuName("치약맛 초코 아이스크림");
        foundMenu.setMenuCode(999);     // 존재하지 않는 코드값으로 변경
        entityManager.merge(foundMenu);
        entityTransaction.commit();

        // then
        assertEquals("치약맛 초코 아이스크림", entityManager.find(Menu.class, 999).getMenuName());
    }

    @DisplayName("준영속화 clear 테스트")
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testClearPersistenceContext(int menuCode){
        // given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        // when
        // clear : 영속성 컨텍스트 초기화 => 영속성 컨텍스트 내 모든 엔터티를 준영속화 시킴
        entityManager.clear();

        // then
        Menu expectedMenu = entityManager.find(Menu.class, menuCode);
        assertNotEquals(foundMenu, expectedMenu);
    }

    @DisplayName("준영속화 close 테스트")
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testClosePersistenceContext(int menuCode){
        // given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        // when
        // close : 영속성 컨텍스트 종료
        entityManager.close();

        // then
//        entityManager.find(Menu.class, menuCode);     // Tests failed
        assertThrows(IllegalStateException.class, () -> entityManager.find(Menu.class, menuCode));  // 예상되는 에러를 적어주면 Tests passed
    }

    @DisplayName("영속성 엔터티 삭제 remove 테스트")
    @ParameterizedTest
    @ValueSource(ints = {2})
    void testRemoveEntity(int menuCode){
        // given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        // when
        entityTransaction.begin();
        // remove : 엔터티를 영속성 컨텍스트 및 DB에서 삭제 (단, 트랜잭션을 제어하지 않으면 DB에 영구반영 안됨)
        // 트랜잭션을 커밋하는 순간 영속성 컨텍스트에서 관리하는 엔터티 객체가 DB에 반영
        entityManager.remove(foundMenu);
        // flush : 영속성 컨텍스트의 변경 내용을 db에 동기화 하는 작업
        entityManager.flush();

        // then
        Menu refoundMenu = entityManager.find(Menu.class, menuCode);    // remove한 상태가 동기화 되었기 때문에, null 값임
        assertNull(refoundMenu);
        entityTransaction.rollback();
    }

}
