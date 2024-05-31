package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.exceptions.IllegalArgumentException;
import com.js1802_team5.diamondShop.exceptions.OutOfStockException;
import com.js1802_team5.diamondShop.exceptions.ProductNotFoundException;
import com.js1802_team5.diamondShop.models.request_models.Product;
import com.js1802_team5.diamondShop.models.response_models.CartResponse;
import com.js1802_team5.diamondShop.models.response_models.CartItemResponse;
import com.js1802_team5.diamondShop.repositories.DiamondRepo;
import com.js1802_team5.diamondShop.repositories.DiamondShellRepo;
import com.js1802_team5.diamondShop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final DiamondShellRepo diamondShellRepo;
    private final DiamondRepo diamondRepo;
    private final Map<String, CartResponse> cartStorage = new HashMap<>();
    private static int cartCounter = 1;
    @Override
    public String generateCartId() {
        String cartId = String.format("CA%06d", cartCounter);
        cartCounter++;
        return cartId;
    }

    @Override
    public String addToCart(int productID, ProductType productType, int customerID) {
        CartResponse cartResponse = null;
        if (cartStorage.isEmpty()) {
            String newCartID = generateCartId();
            cartResponse = new CartResponse(newCartID, customerID, new ArrayList<>());
            cartStorage.put(newCartID, cartResponse);
        } else {
            for (Map.Entry<String, CartResponse> entry : cartStorage.entrySet()) {
                CartResponse storedCartResponse = entry.getValue();
                if (storedCartResponse.getCustomerID().equals(customerID)) {
                    cartResponse = storedCartResponse;
                    break;
                } else {
                    String newCartID = generateCartId();
                    cartResponse = new CartResponse(newCartID, customerID, new ArrayList<>());
                    cartResponse.setCustomerID(customerID);
                    cartStorage.put(newCartID, cartResponse);
                }
            }
        }

        Product product = findProductById(productID, productType);

        if (product.getQuantity() == 0) {
            throw new OutOfStockException("Product out of stock");
        }

        Optional<CartItemResponse> existingItemOptional = cartResponse.getItems().stream()
                .filter(item -> item.getProductId() == (productID) && item.getProductType() == productType)
                .findFirst();

        if (existingItemOptional.isPresent()) {
            CartItemResponse existingItem = existingItemOptional.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            existingItem.setAmount(existingItem.getAmount() + existingItem.getUnitPrice());
        } else {
            CartItemResponse newItem = new CartItemResponse();
            newItem.setProductId(productID);
            newItem.setProductType(productType);
            newItem.setQuantity(1);
            newItem.setUnitPrice(product.getPrice());
            newItem.setAmount(product.getPrice());
            cartResponse.getItems().add(newItem);
        }
        cartStorage.put(cartResponse.getCartId(), cartResponse);
        return cartResponse.getCartId();
    }

    public CartResponse getCartByCustomerID(int customerID) {
        Optional<CartResponse> optionalCart = cartStorage.values().stream()
                .filter(cartResponse -> cartResponse.getCustomerID() == customerID)
                .findFirst();
        return optionalCart.orElse(null);
    }

    @Override
    public Product findProductById(int productID, ProductType productType) {
        if (productType == ProductType.DIAMOND) {
            return diamondRepo.findById(productID).orElseThrow(() -> new ProductNotFoundException("Diamond not found"));
        } else if (productType == ProductType.DIAMOND_SHELL) {
            return diamondShellRepo.findById(productID).orElseThrow(() -> new ProductNotFoundException("Diamond Shell not found"));
        } else {
            throw new IllegalArgumentException("Invalid product type");
        }
    }

    @Override
    public CartResponse updateCart(int customerID, ProductType productType, int productID, int quantity) {
        CartResponse cartResponse = getCartByCustomerID(customerID);
        if (cartResponse == null) {
            throw new IllegalArgumentException("Cart not found for customerID: " + customerID);
        }
        switch (productType) {
            case DIAMOND:
                updateDiamondQuantity(cartResponse, productID, quantity);
                break;
            case DIAMOND_SHELL:
                updateDiamondShellQuantity(cartResponse, productID, quantity);
                break;
            default:
                throw new IllegalArgumentException("Invalid product type: " + productType);
        }
        //Return an updated cart
        return cartResponse;
    }

    @Override
    public void updateDiamondQuantity(CartResponse cartResponse, int productID, int quantity) {
        updateProductQuantity(cartResponse, productID, quantity, ProductType.DIAMOND);
    }

    @Override
    public void updateDiamondShellQuantity(CartResponse cartResponse, int productID, int quantity) {
        updateProductQuantity(cartResponse, productID, quantity, ProductType.DIAMOND_SHELL);
    }

    @Override
    public void updateProductQuantity(CartResponse cartResponse, int productID, int quantity, ProductType productType) {
        Product product = findProductById(productID, productType);
        Optional<CartItemResponse> optionalProduct = cartResponse.getItems().stream()
                .filter(item -> item.getProductId() == productID && item.getProductType() == productType)
                .findFirst();
        if (optionalProduct.isPresent()) {
            CartItemResponse cartItemResponse = optionalProduct.get();
            //Check valid quantity
            if (quantity > 0 && quantity <= product.getQuantity()) {
                cartItemResponse.setQuantity(quantity);
                cartItemResponse.setAmount(cartItemResponse.getUnitPrice() * quantity);
            } else {
                throw new IllegalArgumentException("Invalid quantity: " + quantity);
            }
        } else {
            throw new IllegalArgumentException("Product not found in cart: productID=" + productID + ", productType=" + productType);
        }
    }

    @Override
    public void deleteCart(int customerID, int productID, ProductType productType) {
        CartResponse cartResponse = getCartByCustomerID(customerID);
        if (cartResponse == null) {
            throw new IllegalArgumentException("Cart not found for customerID: " + customerID);
        }
        boolean found = false;
        List<CartItemResponse> cartItems = cartResponse.getItems();
        Iterator<CartItemResponse> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItemResponse cartItem = iterator.next();
            if (cartItem.getProductId() == productID && cartItem.getProductType() == productType) {
                iterator.remove();
                found = true;
                break; // Exit the loop after deleting the item
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Product not found in cart: productID=" + productID + ", productType=" + productType);
        }
        // If cart is empty after deleting an item, reset the cart to null
        if (cartItems.isEmpty()) {
            resetCart(customerID);
        }
    }

    @Override
    public void resetCart(int customerID) {
        cartStorage.remove(String.valueOf(customerID));
    }
}
