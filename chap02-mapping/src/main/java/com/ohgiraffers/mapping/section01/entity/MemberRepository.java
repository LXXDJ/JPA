package com.ohgiraffers.mapping.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Member member){
        entityManager.persist(member);
    }

    public String findNameById(String memberId){
        // FROM 뒤에 테이블명이 아니라, entity명이 온다. (alias 별칭 필수) => entity 기준으로 생각
        String jpql = "SELECT m.memberName FROM entityMember m WHERE m.memberId = '" + memberId + "'";

        // entityManager.createQuery(전달할 구문, 반환받고자 하는 타입)
        // getSingleResult() : 하나의 결과만 전달받겠다.
        return entityManager.createQuery(jpql, String.class).getSingleResult();
    }
}
