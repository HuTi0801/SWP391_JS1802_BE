package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.configurations.VnPayConfig;
import com.js1802_team5.diamondShop.models.response_models.VnPayResponse;
import com.js1802_team5.diamondShop.services.VnPayService;
import com.js1802_team5.diamondShop.utils.VnpayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VnPayServiceImpl implements VnPayService {
    private final VnPayConfig vnPayConfig;

    @Override
    public VnPayResponse createPayment(HttpServletRequest request, Integer cusId, long amount, String language) throws UnsupportedEncodingException {
        String vnp_Version = vnPayConfig.getVnp_Version();
        String vnp_Command = vnPayConfig.getVnp_Command();
        String orderType = vnPayConfig.getOrderType();
        amount = amount * 100;  // Số tiền tính bằng VND, nhân với 100 để chuyển sang đồng

        String vnp_TxnRef = VnpayUtil.getRandomNumber(8);
        String vnp_IpAddr = VnpayUtil.getIpAddress(request);
        String vnp_TmnCode = vnPayConfig.getVnp_TmnCode();

        // IPN URL được lấy từ cấu hình
        String vnp_IpnUrl = vnPayConfig.getIpnUrl();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Order is successfully:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        if (language != null && !language.isEmpty()) {
            vnp_Params.put("vnp_Locale", language);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Đưa IPN URL vào danh sách tham số
        vnp_Params.put("vnp_IpnUrl", vnp_IpnUrl);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return VnPayResponse.builder()
                .status(200)
                .message("Redirecting to payment gateway")
                .url(paymentUrl)
                .build();
    }

    @Override
    public VnPayResponse vnPayReturn(HttpServletRequest request) throws UnsupportedEncodingException {
        String queryString = request.getQueryString();
        Map<String, String> allParams = new HashMap<>();
        if (queryString != null) {
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.toString()); // decode the parameter value
                    allParams.put(key, value);
                }
            }
        }
        String vnp_SecureHash = allParams.get("vnp_SecureHash");
        if (vnp_SecureHash == null) {
            return VnPayResponse.builder()
                    .status(400)
                    .message("Transaction is not successfully. There is no authentication code.")
                    .url(request.getRequestURL().toString())
                    .paymentDetails(allParams)
                    .build();
        }

        // Tạo danh sách các tên trường để tính toán mã băm
        List<String> fieldNames = new ArrayList<>(allParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            if (!fieldName.equals("vnp_SecureHash")) { // exclude vnp_SecureHash itself
                String fieldValue = allParams.get(fieldName);
                if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                        hashData.append('&');
                    }
                }
            }
        }

        String calculatedHash = VnpayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());

        if (!vnp_SecureHash.equals(calculatedHash)) {
            return VnPayResponse.builder()
                    .status(400)
                    .message("Transaction is not successfully. Error verifying signature.")
                    .url(request.getRequestURL().toString())
                    .paymentDetails(allParams)
                    .build();
        }

        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
        if ("00".equals(vnp_ResponseCode)) {
            return VnPayResponse.builder()
                    .status(200)
                    .message("Transaction is successfully!")
                    .url(request.getRequestURL().toString())
                    .paymentDetails(allParams)
                    .build();
        } else {
            return VnPayResponse.builder()
                    .status(400)
                    .message("Transaction is not successfully. Status: " + vnp_ResponseCode)
                    .url(request.getRequestURL().toString())
                    .paymentDetails(allParams)
                    .build();
        }
    }
}
