package com.ohgiraffers.task.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PagingButton {
    private int currentPage;
    private int startPage;
    private int endPage;
}
