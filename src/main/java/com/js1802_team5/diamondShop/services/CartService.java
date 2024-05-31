package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.models.request_models.Product;
import com.js1802_team5.diamondShop.models.response_models.CartResponse;

public interface CartService {
    String generateCartId();
    String addToCart(int productID, ProductType productType, int customerID);
    CartResponse getCartByCustomerID(int customerID);
    Product findProductById(int productID, ProductType productType);
    CartResponse updateCart(int customerID, ProductType productType, int productID, int quantity);
    void updateDiamondQuantity(CartResponse cartResponse, int productID, int quantity);
    void updateDiamondShellQuantity(CartResponse cartResponse, int productID, int quantity);
    void updateProductQuantity(CartResponse cartResponse, int productID, int quantity, ProductType productType);
    void deleteCart(int customerID, int productID, ProductType productType);
    void resetCart(int customerID);
}
