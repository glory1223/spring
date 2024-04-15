package com.example.gloryBooks.controller;

import com.example.gloryBooks.dto.GloryBook;
import com.example.gloryBooks.dto.GloryUser;
import com.example.gloryBooks.service.GloryBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static groovyjarjarantlr4.v4.gui.GraphicsSupport.saveImage;

@Controller
public class GloryBookController {



    @Autowired
    GloryBookService gloryBookService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    // {RequestMethod.GET, RequestMethod.POST}
    public String list(HttpServletRequest request, Model model) {

        try {

            String searchKey = request.getParameter("searchKey"); //검색키워드
            String searchValue = request.getParameter("searchValue"); //검색어

            if (searchValue == null) { //검색어가 없다면

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
            String param = "";
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

    @GetMapping(value = "/view")
    public String view(HttpServletRequest request, Model model, GloryUser gloryUser) {
        String param = "";
        try {
            param = "userId" + gloryUser.getUserId();
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            if (searchValue != null) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }
            //조회수 늘리기 (보류)

            // 게시물 데이터 가져오기
            GloryBook gloryBook = gloryBookService.getReadBook(bookId);

            //가져온 게시물이 없다면
            if (gloryBook == null) {
                return "redirect:/list";
            }


            //검색어가 있다면
            if (searchValue != null & !searchValue.equals("")) {
                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
            }

            model.addAttribute("gloryBook", gloryBook);
            model.addAttribute("params", param);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "view";
    }


    @GetMapping("/write")
    public String showWriteForm() {
        return "write"; // write.html로 이동
    }

    @PostMapping("/insert")
    public String insertBook(GloryBook gloryBook, HttpSession session) {
        Object userId = (int)session.getAttribute("user_id");

            try {

                if (userId == null) {
                    return "redirect:/login";
                } else {
                    gloryBookService.insertBook(gloryBook);
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
            return "redirect:/list";
        }
    @DeleteMapping(value = "/delete/{bookId}")
    public @ResponseBody ResponseEntity deletePost(@PathVariable("bookId") int bookId, HttpSession session) {
        try {
            gloryBookService.deleteBook(bookId);

            Object userId = session.getAttribute("user_id");

            if(userId == null) {
                return new ResponseEntity<String>("삭제실패. 관리자에게 문의하세요.", HttpStatus.UNAUTHORIZED);
            } else {
                gloryBookService.deleteBook(bookId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("삭제실패. 관리자에게 문의하세요.", HttpStatus.BAD_REQUEST);
        }

        //ResponseEntity<첫번째 매개변수의 타입>(result결과, response상태코드)
        //HttpStatus.OK일떄는 ajax의 success함수로 결과가 출력된다.
        return new ResponseEntity<Integer>(bookId, HttpStatus.OK);
    }

    @PostMapping(value = "/rewrite")
    public String update(GloryBook gloryBook, HttpSession session, HttpServletRequest request) {
        String param = "";
        try {
            String pageNum = request.getParameter("pageNum");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");
            param = "bookId=" + gloryBook.getBookId();

            if(searchValue != null && !searchValue.equals("")) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
                //검색어가 있다면
                param += "&searchKey=" + searchKey;
                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
            }

            Object memberId = session.getAttribute("member_id");

            if(memberId == null) {
                return "redirect:/login"; // 세션 만료시 로그인 페이지로 이동
            } else {
                gloryBookService.updateBook(gloryBook); //포스트 update 서비스 호출
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/view?" + param;
    }
    }





