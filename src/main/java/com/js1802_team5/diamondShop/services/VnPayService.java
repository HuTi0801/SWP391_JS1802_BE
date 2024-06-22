package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.response_models.VnPayResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface VnPayService {
    VnPayResponse createPayment(HttpServletRequest request, Integer cusId, long amount, String bankCode, String language) throws UnsupportedEncodingException;
    VnPayResponse vnPayReturn(HttpServletRequest request) throws UnsupportedEncodingException;
}
