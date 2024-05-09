package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_like")
public class Like { // id 속성 필수 (id 속성이 없어서 빨간밑줄이 표시되는 것)

    @EmbeddedId     // 이렇게 설정해주고 나면 Like 클래스에 빨간밑줄이 사라짐
    private LikeCompositeKey likeInfo;

    protected Like(){}

    public Like(LikeCompositeKey likeInfo) {
        this.likeInfo = likeInfo;
    }

    @Override
    public String toString() {
        return "Like{" +
                "likeInfo=" + likeInfo +
                '}';
    }
}
