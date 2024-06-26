package com.ohgiraffers.jpql.section03.projection;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_menu")
public class EmbeddedMenu {
    @Id
    private int menuCode;
    @Embedded //@Embeddable 타입을 사용하는 곳에 적용
    private MenuInfo menuInfo;
    private int categoryCode;
    private String orderableStatus;
    public EmbeddedMenu () {}
    public EmbeddedMenu(
            int menuCode, MenuInfo menuInfo, int categoryCode, String orderableSt
    ) {
        super();
        this.menuCode = menuCode;
        this.menuInfo = menuInfo;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }
    public int getMenuCode() {
        return menuCode;
    }
    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }
    public MenuInfo getMenuInfo() {
        return menuInfo;
    }
    public void setMenuInfo(MenuInfo menuInfo) {
        this.menuInfo = menuInfo;
    }
    public int getCategoryCode() {
        return categoryCode;
    }
    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
    public String getOrderableStatus() {
        return orderableStatus;
    }
    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }
    @Override
    public String toString() {
        return "EmbeddedMenu [menuCode=" + menuCode + ", menuInfo=" + menuInfo
                + ", categoryCode=" + categoryCode + ", orderableStatus="
                + orderableStatus + "]";
    }
}
