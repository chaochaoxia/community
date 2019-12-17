package com.lic.ayr.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    private List<QuestionToKenDTO> questions; //这个是存放每次查询某一页的数据放在list中
    private boolean showPrevious;//是否有上一页
    private boolean showFirstPage;//是否有第一页
    private boolean showNext;//是否有下一页
    private boolean showEndPage;//是否有最后一页
    private Integer page;//当前页
    private List<Integer> pages=new ArrayList<>();//列表数
    private Integer totalPage;//有多少页

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if (totalCount%size==0){//如果刚好整除就取totalPage
            totalPage=totalCount / size;
        }else {
            totalPage=totalCount / size + 1;//有余数就+1
        }
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        this.page=page;
        pages.add(page);//先把当前页加到list中
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page + i <=totalPage){
                pages.add(page + i);
            }
        }

//        是否有上一页
        if (page ==1){
            showPrevious =false;
        }else {
            showPrevious=true;
        }
//        是否有下一页
        if (page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
//      是否展示第一页
        if (pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
//        是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }
    }

}
