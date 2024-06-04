package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.exceptions.ProductNotFoundException;
import com.js1802_team5.diamondShop.models.request_models.Product;
import com.js1802_team5.diamondShop.models.response_models.CartResponse;
import com.js1802_team5.diamondShop.models.response_models.CartItemResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
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
    public Response addToCart(int productID, ProductType productType, int customerID, Integer size) {
        Response response = new Response();
        try {
            if (productType == ProductType.DIAMOND_SHELL && size == null) {
                response.setSuccess(false);
                response.setMessage("Need to select ring size before adding to Cart");
                response.setStatusCode(400);
                response.setResult(null);
                return response;
            }

            CartResponse cartResponse = null;
            if (cartStorage.isEmpty()) {
                String newCartID = generateCartId();
                cartResponse = new CartResponse(newCartID, customerID, new ArrayList<>());
                cartStorage.put(newCartID, cartResponse);
            } else {
                boolean found = false;
                for (Map.Entry<String, CartResponse> entry : cartStorage.entrySet()) {
                    CartResponse storedCartResponse = entry.getValue();
                    if (storedCartResponse.getCustomerID() == customerID) {
                        cartResponse = storedCartResponse;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    String newCartID = generateCartId();
                    cartResponse = new CartResponse(newCartID, customerID, new ArrayList<>());
                    cartStorage.put(newCartID, cartResponse);
                }
            }

            Product product = findProductById(productID, productType);
            if (product.getQuantity() == 0) {
                response.setSuccess(false);
                response.setMessage("Product out of stock");
                response.setStatusCode(400);
                response.setResult(null);
                return response;
            }

            boolean itemAdded = false;
            if (productType == ProductType.DIAMOND_SHELL) {
                for (CartItemResponse item : cartResponse.getItems()) {
                    if (item.getProductId() == productID && item.getProductType() == productType) {
                        if (item.getSize() == (size)) {
                            item.setQuantity(item.getQuantity() + 1);
                            item.setAmount(item.getAmount() + item.getUnitPrice());
                            itemAdded = true;
                            break;
                        }
                    }
                }
                if (!itemAdded) {
                    CartItemResponse newItem = new CartItemResponse();
                    newItem.setProductId(productID);
                    newItem.setProductType(productType);
                    newItem.setQuantity(1);
                    newItem.setUnitPrice(product.getPrice());
                    newItem.setAmount(product.getPrice());
                    newItem.setSize(size);
                    cartResponse.getItems().add(newItem);
                }
            } else {
                Optional<CartItemResponse> existingItemOptional = cartResponse.getItems().stream()
                        .filter(item -> item.getProductId() == productID && item.getProductType() == productType)
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
            }

            cartStorage.put(cartResponse.getCartId(), cartResponse);
            response.setSuccess(true);
            response.setMessage("Item added to cart successfully!");
            response.setStatusCode(200);
            response.setResult(cartResponse);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            response.setResult(null);
        }
        return response;
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
    public Response updateCart(int customerID, ProductType productType, int productID, int quantity) {
        Response response = new Response();
        try {
            CartResponse cartResponse = getCartByCustomerID(customerID);
            if (cartResponse == null) {
                response.setSuccess(false);
                response.setMessage("Cart not found for customerID: " + customerID);
                response.setStatusCode(404);
                response.setResult(null);
                return response;
            }
            switch (productType) {
                case DIAMOND:
                    updateDiamondQuantity(cartResponse, productID, quantity);
                    break;
                case DIAMOND_SHELL:
                    updateDiamondShellQuantity(cartResponse, productID, quantity);
                    break;
                default:
                    response.setSuccess(false);
                    response.setMessage("Invalid product type: " + productType);
                    response.setStatusCode(400);
                    response.setResult(null);
                    return response;
            }
            response.setSuccess(true);
            response.setMessage("Cart updated successfully!");
            response.setStatusCode(200);
            response.setResult(cartResponse);
        } catch (IllegalArgumentException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
            response.setResult(null);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            response.setResult(null);
        }
        return response;
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
                throw new IllegalArgumentException("Invalid quantity: " + quantity + " (product quantity not exceed: " + product.getQuantity() + ")");
            }
        } else {
            throw new IllegalArgumentException("Product not found in cart: productID=" + productID + ", productType=" + productType);
        }
    }

    @Override
    public Response deleteCart(int customerID, int productID, ProductType productType) {
        Response response = new Response();
        try {
            CartResponse cartResponse = getCartByCustomerID(customerID);
            if (cartResponse == null) {
                response.setSuccess(false);
                response.setMessage("Cart not found for customerID: " + customerID);
                response.setStatusCode(404);
                response.setResult(null);
                return response;
            }
            boolean found = false;
            List<CartItemResponse> cartItems = cartResponse.getItems();
            Iterator<CartItemResponse> iterator = cartItems.iterator();
            while (iterator.hasNext()) {
                CartItemResponse cartItem = iterator.next();
                if (cartItem.getProductId() == productID && cartItem.getProductType() == productType) {
                    iterator.remove();
                    found = true;
                    break;
                }
            }
            if (!found) {
                response.setSuccess(false);
                response.setMessage("Product not found in cart: productID=" + productID + ", productType=" + productType);
                response.setStatusCode(404);
                response.setResult(null);
                return response;
            }
            if (cartItems.isEmpty()) {
                resetCart(customerID);
            }
            response.setSuccess(true);
            response.setMessage("Product removed from cart successfully");
            response.setStatusCode(200);
            response.setResult(cartResponse);
        } catch (IllegalArgumentException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
            response.setResult(null);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            response.setResult(null);
        }
        return response;
    }

    @Override
    public void resetCart(int customerID) {
        cartStorage.remove(String.valueOf(customerID));
    }
}
