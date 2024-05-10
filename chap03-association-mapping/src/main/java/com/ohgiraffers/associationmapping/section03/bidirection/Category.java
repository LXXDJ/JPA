package com.ohgiraffers.associationmapping.section03.bidirection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

/* fk(외래키)가 없고 List<Menu>를 그냥 추가해줬으므로 가짜 연관관계
 * 지연 로딩 발생, join 없음 */

@Entity(name = "bidirection_category")
@Table(name = "tbl_category")
public class Category {
    @Id
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;
    @OneToMany(mappedBy = "category")   // 진짜 연관관계의 필드명
    private List<Menu> menuList;

    protected Category(){}

    public Category(int categoryCode, String categoryName, Integer refCategoryCode, List<Menu> menuList) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
        this.menuList = menuList;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
//                ", menuList=" + menuList +    // 메뉴쪽에서도 toString으로 카테고리를 부르고, 카테고리도 toString으로 메뉴를 불러서 stackoverflow 에러 발생
                '}';
    }
}
