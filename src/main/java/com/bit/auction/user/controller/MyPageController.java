package com.bit.auction.user.controller;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.entity.Point;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.BiddingService;
import com.bit.auction.goods.service.PointService;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class MyPageController {

    @Autowired
    private final UserService userService;
    private final PointService pointService;
    private final BiddingService biddingService;
    private final AuctionService auctionService;

    @GetMapping("/mypage-view")
    public ModelAndView getMyPage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ModelAndView mav = new ModelAndView();

        UserDTO loginUser = customUserDetails.getUser().toDTO();

        if (loginUser == null) {
            mav.setViewName("user/login/login");
        } else {
            mav.setViewName("user/mypage/mypage.html");
        }
        return mav;

    }

@GetMapping("/point")
public ModelAndView getPointPage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ModelAndView mav = new ModelAndView();

        UserDTO loginUser = customUserDetails.getUser().toDTO();

        String userId = customUserDetails.getUsername();

        PointDTO pointDTO = pointService.getPoint(userId);

        mav.addObject("getPoint", pointDTO);

        if (loginUser == null) {
            mav.setViewName("user/login/login");
        } else {
            mav.setViewName("user/mypage/mypoint.html");
        }
        return mav;
}


    @GetMapping("/biddingList")
    public ModelAndView getMyBidding(/*@RequestParam("auctionId") Long auctionId,*/
                                     @PageableDefault(page = 0, size = 10) Pageable pageable,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModelAndView mav = new ModelAndView();

        List<AuctionDTO> biddingList = auctionService.getByuserName(customUserDetails.getUser().getUserName());
        mav.addObject("BiddingList", biddingList);


        /*String userId = customUserDetails.getUsername();
        BiddingDTO biddingDTO = biddingService.getbid(auctionId , userId);
        mav.addObject("biddingList", auctionService.getMyBiddingList(pageable, userId));
        mav.addObject("getBid" , biddingDTO);*/
        mav.setViewName("user/mypage/getMyBiddingList.html");

        return mav;
    }

}
