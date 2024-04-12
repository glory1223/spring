package com.example.gloryBooks.controller;

import com.example.gloryBooks.dto.CartItem;
import com.example.gloryBooks.dto.GloryCart;
import com.example.gloryBooks.service.CartItemService;
import com.example.gloryBooks.service.GloryCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class GloryCartController {


    @Autowired
    GloryCartService gloryCartService;

    @Autowired
    CartItemService cartItemService;


    @GetMapping(value = "/cart")
    public String viewCart(Model model, HttpSession session) {
        //세션에서 id 가져오기
        Object userId = (int)session.getAttribute("user_id");

        if(userId == null) {
            return "redirect:/login";
        } else {

            try {

              List<GloryCart> cartLists = gloryCartService.cartList((int) userId);

                // 모델에 장바구니 목록 추가
                model.addAttribute("cartLists", cartLists);
                model.addAttribute("userId", userId);
            } catch (Exception e) {

            }
        }
        return "cart/cart";
    }

    @PostMapping(value= "/addToCart/{bookId}")
    public String addToCart(@PathVariable("bookId") int bookId, @RequestParam("quantity") int quantity) {
        try {
            CartItem cartItem = new CartItem();
            cartItem.setBookId(bookId);
            cartItem.setQuantity(quantity);
            cartItemService.addToCart(cartItem);
        } catch (Exception e) {
            // 실패 시 처리 로직
            throw new RuntimeException(e);
        }
        return "redirect:/list";
    }

}





