package com.ohgiraffers.associationmapping.section03.bidirection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BiDirectionService {
    private BiDirectionRepository biDirectionRepository;

    public BiDirectionService(BiDirectionRepository biDirectionRepository){
        this.biDirectionRepository = biDirectionRepository;
    }

    public Menu findMenu(int menuCode){
        return biDirectionRepository.findMenu(menuCode);
    }

    @Transactional
    public Category findCategory(int categoryCode){
        // return biDirectionRepository.findCategory(categoryCode);

        Category category = biDirectionRepository.findCategory(categoryCode);

        // 카테고리에서도 메뉴가 필요한 상황을 만들어줌 (join)
        System.out.println("category.getMenuList() = " + category.getMenuList());
        System.out.println("category.getMenuList().get(0).getCategory() = " + category.getMenuList().get(0).getCategory());

        return category;
    }
}
