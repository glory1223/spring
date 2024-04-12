package com.example.gloryBooks.controller;

import com.example.gloryBooks.dto.GloryBook;
import com.example.gloryBooks.service.GloryBookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GloryBookController {



    @Autowired
    GloryBookService gloryBookService;

    @RequestMapping(value = "/list", method={RequestMethod.GET, RequestMethod.POST}) // {RequestMethod.GET, RequestMethod.POST}
    public String list(HttpServletRequest request, Model model) {

        try {

            String searchKey = request.getParameter("searchKey"); //검색키워드
            String searchValue = request.getParameter("searchValue"); //검색어

            if(searchValue == null) { //검색어가 없다면

                searchKey = "book_name"; // 검색 키워드의 디폴트는 subject
                searchValue = ""; //검색어의 디폴트는 빈문자열이다

            } else { // 검색어가 있다면
                searchKey = "book_name";
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }

            Map map = new HashMap();
            map.put("searchKey", searchKey);
            map.put("searchValue", searchValue);

            List<GloryBook> bookLists = gloryBookService.getBookList(map);

            //검색어에 대한 쿼리스트링을 만든다.
            String param ="";
            String listUrl = "/list";
            String articleUrl = "/view?";


            //검색어가 있다면
          //  if(searchValue != null && !searchValue.equals("")) {
                param = "searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
           // }
         //   if(!param.equals("")) {
                listUrl += "?" + param; // listUrl의 값 /list?searchKey=subject&searchValue=네번째
                articleUrl += param;
          //  }

            model.addAttribute("bookLists", bookLists); // DB에서 가져온 전체 게시물 리스트
            model.addAttribute("searchKey", searchKey); // 검색어
            model.addAttribute("searchValue", searchValue); // 검색어
            model.addAttribute("listUrl", listUrl);
            model.addAttribute("articleUrl", articleUrl);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "list";
    }

    @GetMapping(value ="/view")
    public String view(HttpServletRequest request, Model model) {
        try {
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                String searchKey = request.getParameter("searchKey");
                String searchValue =request.getParameter("searchValue");

                if(searchValue != null) {
                    searchValue = URLDecoder.decode(searchValue, "UTF-8");
                }
                //조회수 늘리기 (보류)

                // 게시물 데이터 가져오기
                GloryBook gloryBook = gloryBookService.getReadBook(bookId);

                //가져온 게시물이 없다면
                if(gloryBook == null) {
                    return "redirect:/list";
                }

                String param = "";

                //검색어가 있다면
                if(searchValue != null & !searchValue.equals("")) {
                    param += "searchKey=" + searchKey;
                    param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
                }

                model.addAttribute("gloryBook", gloryBook);
                model.addAttribute("params", param);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        return "view";
        }
    }


