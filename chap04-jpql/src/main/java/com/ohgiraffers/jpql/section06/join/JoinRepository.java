package com.ohgiraffers.jpql.section06.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JoinRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Menu> selectByInnerJoin(){
        String jpql = "SELECT m FROM Section06Menu m JOIN m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        return menuList;
    }

    public List<Object[]> selectByOuterJoin(){
        String jpql = "SELECT m.menuName, c.categoryName FROM Section06Menu m RIGHT JOIN m.category c " +
                "ORDER BY m.category.categoryCode"; // 연관관계 매핑을 통해서 category에 접근하는 방식
        List<Object[]> menuList = entityManager.createQuery(jpql).getResultList();
        return menuList;
    }

    public List<Object[]> selectByCollectionJoin(){
        String jpql = "SELECT m.menuName, c.categoryName FROM Section06Category c LEFT JOIN c.menuList m";
        List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();
        return categoryList;
    }

    public List<Object[]> selectByThetaJoin() {
        // 세타조인 : 모든 행들을 한번씩 조인시킴
        String jpql = "SELECT m.menuName, c.categoryName FROM Section06Category c, Section06Menu m";
        List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();
        return categoryList;
    }

    public List<Menu> selectByFetchJoin(){
        // FETCH : 즉시로딩을 수행해서 값을 가져오겠다는 의미 (기본적으로 지연로딩 수행)
        String jpql = "SELECT m FROM Section06Menu m JOIN FETCH m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        return menuList;
    }
}
