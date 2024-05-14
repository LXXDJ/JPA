package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.common.Pagenation;
import com.ohgiraffers.springdatajpa.common.PagingButton;
import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j      // logger 객체를 선언하기 위해 사용
@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/{menuCode}")
    public String findMenuByMenuCode(@PathVariable int menuCode, Model model){
        MenuDTO resultMenu = menuService.findMenuByMenuCode(menuCode);
        model.addAttribute("menu", resultMenu);

        return "/menu/detail";
    }

    @GetMapping("/list")
    public String findMenuList(Model model, @PageableDefault Pageable pageable){
        // 페이징 처리 전
//        List<MenuDTO> menuList = menuService.findMenuList();
//        model.addAttribute("menuList", menuList);

        // 페이징 처리 후
        log.info("pageable: {}", pageable);

        Page<MenuDTO> menuList = menuService.findMenuList(pageable);
        log.info("{}", menuList.getContent());          // [MenuDTO(menuCode=999, menuName=치약맛 초코 아이스크림, menuPrice=22000, categoryCode=5, orderableStatus=N), MenuDTO(menuCode=321, menuName=스파게티 돈가스, menuPrice=30000, categoryCode=321, orderableStatus=Y), ...
        log.info("{}", menuList.getTotalPages());       // 5
        log.info("{}", menuList.getTotalElements());    // 24
        log.info("{}", menuList.getSize());             // 5
        log.info("{}", menuList.getNumberOfElements()); // 5
        log.info("{}", menuList.isFirst());             // true
        log.info("{}", menuList.isLast());              // false
        log.info("{}", menuList.getSort());             // menuCode: DESC
        log.info("{}", menuList.getNumber());           // 1

        PagingButton paging = Pagenation.getPagingButtonInfo(menuList);
        model.addAttribute("menuList", menuList);
        model.addAttribute("paging", paging);

        return "/menu/list";
    }

    @GetMapping("/querymethod")
    public void querymethodPage(){}

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model){
        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);
        model.addAttribute("menuList", menuList);
        model.addAttribute("menuPrice", menuPrice);

        return "/menu/searchResult";
    }

    @GetMapping("/regist")
    public void registPage(){}

    /* 스프링 프레임워크에서 비동기 통신(API 통신 구현)을 위해 사용
    *  @RequestBody : 클라이언트 -> 서버 요청. json 기반의 HTTP Body를 자바 객체로 변환
    *  @ResponseBody : 서버 -> 클라이언트 응답. 자바 객체를 json 기반의 HTTP Body로 변환 */
    @GetMapping("/category")
    @ResponseBody
    public List<CategoryDTO> findCategoryList(){
        return menuService.findAllCategory();
    }
}
