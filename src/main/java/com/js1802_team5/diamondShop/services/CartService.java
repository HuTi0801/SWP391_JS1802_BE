package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.models.request_models.Product;
import com.js1802_team5.diamondShop.models.response_models.Cart;

public interface CartService {
    String generateCartId();
    String addToCart(int productID, ProductType productType, int customerID);
    Cart getCart(String cartID, int customerID);
    Product findProductById(int productID, ProductType productType);
}
