package com.ohgiraffers.jpql.section03.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Menu> singleEntityProjection(){
        String jpql = "SELECT m FROM Section03Menu m";
        return entityManager.createQuery(jpql, Menu.class).getResultList();
    }

    public List<MenuInfo> embeddedTypeProjection() {
        String jpql = "SELECT m.menuInfo FROM EmbeddedMenu m";
        List<MenuInfo> resultMenuInfo = entityManager.createQuery(jpql, MenuInfo.class).getResultList();
        return resultMenuInfo;
    }

    public List<Object[]> scalarTypeProjection(){
        // List<Object[]> : categoryCode와 categoryName를 동시에 받을 수 있는 타입이 정의되어 있지 않기 때문에 Object 배열로 받음
        String jpql = "SELECT c.categoryCode, c.categoryName FROM Section03Category c";
        return entityManager.createQuery(jpql).getResultList();
    }

    public List<CategoryInfo> newCommandProjection(){
        // Section03Category 엔터티에서 찾아온 값을 CategoryInfo에 매핑함 (CategoryInfo를 반환타입으로 사용)
        String jpql = "SELECT new com.ohgiraffers.jpql.section03.projection.CategoryInfo(c.categoryCode, c.categoryName)" +
                "FROM Section03Category c";
        return entityManager.createQuery(jpql, CategoryInfo.class).getResultList();
    }

}
