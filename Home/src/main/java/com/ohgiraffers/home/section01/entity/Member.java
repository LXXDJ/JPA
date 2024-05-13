package com.ohgiraffers.home.section01.entity;

import jakarta.persistence.*;

@Entity(name = "entityMember")
@Table(name = "tbl_member")
@Access(AccessType.FIELD)
public class Member {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;


}
