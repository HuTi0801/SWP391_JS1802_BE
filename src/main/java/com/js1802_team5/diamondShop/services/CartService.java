package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.models.request_models.Product;
import com.js1802_team5.diamondShop.models.response_models.CartResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;

public interface CartService {
    String generateCartId();
    Response addToCart(int productID, ProductType productType, int customerID, Integer size);
    CartResponse getCartByCustomerID(int customerID);
    Product findProductById(int productID, ProductType productType);
    Response updateCart(int customerID, ProductType productType, int productID, int quantity);
    void updateDiamondQuantity(CartResponse cartResponse, int productID, int quantity);
    void updateDiamondShellQuantity(CartResponse cartResponse, int productID, int quantity);
    void updateProductQuantity(CartResponse cartResponse, int productID, int quantity, ProductType productType);
    Response deleteCart(int customerID, int productID, ProductType productType);
    void resetCart(int customerID);
    Response applyPromotion(String cartId, String promotionCode);
}
