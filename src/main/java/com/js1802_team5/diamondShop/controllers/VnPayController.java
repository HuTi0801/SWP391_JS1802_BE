package com.js1802_team5.diamondShop.controllers;

import com.js1802_team5.diamondShop.models.response_models.VnPayResponse;
import com.js1802_team5.diamondShop.services.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/auth/payment")
@RequiredArgsConstructor
public class VnPayController {
    private final VnPayService vnPayService;
    @PostMapping("/create-payment")
    public VnPayResponse createPayment(HttpServletRequest request,
                                       @RequestParam("cusId") Integer cusId,
                                       @RequestParam("amount") long amount,
                                       @RequestParam("language") String language) throws UnsupportedEncodingException {
        return vnPayService.createPayment(request, cusId, amount, language);
    }
    @GetMapping("/return")
    public VnPayResponse testVnPayReturn(
            @RequestParam Map<String, String> params,
            HttpServletRequest request) throws UnsupportedEncodingException {
        // Đưa các tham số nhận được vào request
        for (Map.Entry<String, String> entry : params.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }

        // Gọi hàm vnPayReturn để xử lý
        return vnPayService.vnPayReturn(request);
    }

}
