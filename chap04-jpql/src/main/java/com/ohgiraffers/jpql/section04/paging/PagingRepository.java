package com.ohgiraffers.jpql.section04.paging;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PagingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Menu> usingPagingAPI(int offset, int limit){
        String jpql = "SELECT m FROM Section04Menu m ORDER BY m.menuCode DESC";
        List<Menu> pagingMenuList = entityManager.createQuery(jpql, Menu.class)
                                                .setFirstResult(offset) // 조회를 시작할 위치
                                                .setMaxResults(limit)   // 조회할 최대 행의 개수
                                                .getResultList();
        return pagingMenuList;
    }
}
