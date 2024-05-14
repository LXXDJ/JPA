package com.ohgiraffers.springdatajpa.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본생성자를 PROTECTED 접근제한 설정 (기본생성자도 public하게 생성 할 경우 안정성 보장X)
/* @AllArgsConstructor : 이것도 지양 */
@Getter
/* @Setter : 객체를 언제든지 변경할 수 있으면 객체의 안정성이 보장되지 않기 때문에 setter 메소드 생성은 지양한다.
 * 값 변경이 필요한 경우, 해당 기능을 위한 메소드를 별도로 생성 */
/* @ToString : 서로 연관관계가 있을 때 둘다 ToString이 있으면, 서로 참조하면서 stackOverFlow 발생하기 때문에 연관관계 매핑 필드는 제거하고 사용 */
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;
    private String menuName;
    private int menuPrice;
    private int categoryCode;
    private String orderableStatus;
}
