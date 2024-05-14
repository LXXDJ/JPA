package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor    // final 필드를 의존성 주입받도록 함
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    // 1. findById : 특정 값 조회
    public MenuDTO findMenuByMenuCode(int menuCode){
        /* menuCode를 전달해서 findById를 수행했을 때, 조회된 행이 없을 경우
         * IllegalArgumentException를 만들어서 에러를 던져라(orElseThrow) => 예상된 오류를 발생시킴 */
        Menu foundMenu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(foundMenu, MenuDTO.class);   // 엔터티를 MenuDTO로 가공하는 작업
    }

    // 2. findAll : Sort 정렬
    public List<MenuDTO> findMenuList() {
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());
        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))  // 하나씩 가공
                .toList();                                          // 다시 리스트로 바꿈
    }

    // 3. findAll : Pageable 페이징 처리
    public Page<MenuDTO> findMenuList(Pageable pageable){
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,   // 몇번째 페이지 요청인지 : offset
                pageable.getPageSize(),                                             // 한페이지에 몇개씩 보여줄건지 : limit
                Sort.by("menuCode").descending()
        );
        Page<Menu> menuList = menuRepository.findAll(pageable);
        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }

    // 4. Query Method
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {
//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);
//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(
                menuPrice, Sort.by("menuPrice").descending()
        );
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }

    // 5. JPQL or NativeQuery
    public List<CategoryDTO> findAllCategory() {
        List<Category> categoryList = categoryRepository.findAllCategory();
        return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
    }
}
