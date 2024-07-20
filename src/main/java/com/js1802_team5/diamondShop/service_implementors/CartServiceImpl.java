package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.enums.ProductType;
import com.js1802_team5.diamondShop.exceptions.ProductNotFoundException;
import com.js1802_team5.diamondShop.models.entity_models.Customer;
import com.js1802_team5.diamondShop.models.entity_models.Diamond;
import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.entity_models.Promotion;
import com.js1802_team5.diamondShop.models.request_models.Product;
import com.js1802_team5.diamondShop.models.response_models.CartResponse;
import com.js1802_team5.diamondShop.models.response_models.CartItemResponse;
import com.js1802_team5.diamondShop.models.response_models.Response;
import com.js1802_team5.diamondShop.repositories.*;
import com.js1802_team5.diamondShop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final DiamondShellRepo diamondShellRepo;
    private final DiamondRepo diamondRepo;
    private final PromotionRepo promotionRepo;
    private final PromotionDiamondRepo promotionDiamondRepo;
    private final PromotionDiamondShellRepo promotionDiamondShellRepo;
    private final CustomerRepo customerRepo;
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

            int totalQuantityInCart = cartResponse.getItems().stream()
                    .filter(item -> item.getProductId() == productID && item.getProductType() == productType)
                    .mapToInt(CartItemResponse::getQuantity)
                    .sum();

            if (totalQuantityInCart >= product.getQuantity()) {
                response.setSuccess(false);
                response.setMessage("Requested quantity exceeds available stock");
                response.setStatusCode(400);
                response.setResult(null);
                return response;
            }

            boolean itemAdded = false;
            if (productType == ProductType.DIAMOND_SHELL) {
                for (CartItemResponse item : cartResponse.getItems()) {
                    if (item.getProductId() == productID && item.getProductType() == productType) {
                        if (item.getSize() == size) {
                            if (item.getQuantity() + 1 > product.getQuantity()) {
                                response.setSuccess(false);
                                response.setMessage("Requested quantity exceeds available stock");
                                response.setStatusCode(400);
                                response.setResult(null);
                                return response;
                            }
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
                    newItem.setProductName(product.getName());
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
                    if (existingItem.getQuantity() + 1 > product.getQuantity()) {
                        response.setSuccess(false);
                        response.setMessage("Requested quantity exceeds available stock");
                        response.setStatusCode(400);
                        response.setResult(null);
                        return response;
                    }
                    existingItem.setQuantity(existingItem.getQuantity() + 1);
                    existingItem.setAmount(existingItem.getAmount() + existingItem.getUnitPrice());
                } else {
                    CartItemResponse newItem = new CartItemResponse();
                    newItem.setProductId(productID);
                    newItem.setProductName(product.getName());
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

    @Override
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
    public Response updateCart(int customerID, ProductType productType, int productID, int quantity, Integer size) {
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
                    updateDiamondShellQuantity(cartResponse, productID, quantity, size);
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

    private void updateDiamondQuantity(CartResponse cartResponse, int productID, int quantity) {
        updateProductQuantity(cartResponse, productID, quantity, ProductType.DIAMOND, null);
    }

    private void updateDiamondShellQuantity(CartResponse cartResponse, int productID, int quantity, Integer size) {
        updateProductQuantity(cartResponse, productID, quantity, ProductType.DIAMOND_SHELL, size);
    }

    private void updateProductQuantity(CartResponse cartResponse, int productID, int quantity, ProductType productType, Integer size) {
        Product product = findProductById(productID, productType);
        Optional<CartItemResponse> optionalProduct;
        if (productType == ProductType.DIAMOND_SHELL && size != null) {
            optionalProduct = cartResponse.getItems().stream()
                    .filter(item -> item.getProductId() == productID && item.getProductType() == productType && item.getSize() == size)
                    .findFirst();
        } else {
            optionalProduct = cartResponse.getItems().stream()
                    .filter(item -> item.getProductId() == productID && item.getProductType() == productType)
                    .findFirst();
        }

        if (optionalProduct.isPresent()) {
            CartItemResponse cartItemResponse = optionalProduct.get();
            // Check valid quantity
            if (quantity > 0 && quantity <= product.getQuantity()) {
                cartItemResponse.setQuantity(quantity);
                cartItemResponse.setAmount(cartItemResponse.getUnitPrice() * quantity);
            } else {
                throw new IllegalArgumentException("Invalid quantity: " + quantity + " (product quantity not exceed: " + product.getQuantity() + ")");
            }
        } else {
            throw new IllegalArgumentException("Product not found in cart: productID=" + productID + ", productType=" + productType + (size != null ? ", size=" + size : ""));
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

    @Override
    public Response applyPromotion(String cartId, String promotionCode, Integer customerID) {
        Response response = new Response();

        // Check if cart exists
        CartResponse cart = cartStorage.get(cartId);
        if (cart == null) {
            response.setMessage("Cart ID not found.");
            response.setSuccess(false);
            response.setStatusCode(404);
            return response;
        }

        // Check if customer exists
        Optional<Customer> customerOptional = customerRepo.findById(customerID);
        if (customerOptional.isEmpty()) {
            response.setMessage("Customer ID not found.");
            response.setSuccess(false);
            response.setStatusCode(404);
            return response;
        }
        Customer customer = customerOptional.get();

        // Check if promotion code exists
        Optional<Promotion> promotionOptional = promotionRepo.findByPromotionCode(promotionCode);
        if (promotionOptional.isEmpty()) {
            response.setMessage("Promotion code not found.");
            response.setSuccess(false);
            response.setStatusCode(404);
            return response;
        }
        Promotion promotion = promotionOptional.get();

        // Check if promotion is valid (dates)
        Date now = new Date();
        if (now.before(promotion.getStartDate()) || now.after(promotion.getEndDate())) {
            response.setMessage("Promotion code is not valid at this time.");
            response.setSuccess(false);
            response.setStatusCode(400);
            return response;
        }

        // Check if customer is eligible for the promotion
        String customerMemberLevel = customer.getMemberLevel();
        List<String> eligibleMemberLevels = Arrays.asList(promotion.getMemberLevelPromotion().split(","));
        if (!eligibleMemberLevels.contains(customerMemberLevel)) {
            response.setMessage("Customer is not suitable for this promotion.");
            response.setSuccess(false);
            response.setStatusCode(400);
            return response;
        }

        // Get applicable product IDs from promotion
        List<Integer> applicableDiamondIds = promotionDiamondRepo.findByPromotionId(promotion.getId())
                .stream().map(promotionDiamond -> promotionDiamond.getDiamond().getId())
                .toList();

        List<Integer> applicableDiamondShellIds = promotionDiamondShellRepo.findByPromotionId(promotion.getId())
                .stream().map(promotionDiamondShell -> promotionDiamondShell.getDiamondShell().getId())
                .toList();

        double discountPercent = promotion.getDiscountPercent();

        // Apply discount to applicable items in the cart
        for (CartItemResponse item : cart.getItems()) {
            if (item.getProductType() == ProductType.DIAMOND && applicableDiamondIds.contains(item.getProductId())) {
                double newAmount = item.getUnitPrice() * item.getQuantity() * (1 - discountPercent / 100);
                item.setAmount(newAmount);
            } else if (item.getProductType() == ProductType.DIAMOND_SHELL && applicableDiamondShellIds.contains(item.getProductId())) {
                double newAmount = item.getUnitPrice() * item.getQuantity() * (1 - discountPercent / 100);
                item.setAmount(newAmount);
            }
        }

        // Update the total price of the cart by summing up the amount of each item
        double totalPrice = cart.getItems().stream().mapToDouble(CartItemResponse::getAmount).sum();
        cart.setTotalPrice(totalPrice);

        cartStorage.put(cartId, cart);

        response.setResult(cart);
        response.setMessage("Promotion applied successfully.");
        response.setSuccess(true);
        response.setStatusCode(200);
        return response;
    }

    @Override
    public Response removePromotion(String cartId, Integer customerId) {
        Response response = new Response();

        try {
            // Kiểm tra giỏ hàng tồn tại
            CartResponse cart = cartStorage.get(cartId);
            if (cart == null) {
                response.setMessage("Cart ID not found.");
                response.setSuccess(false);
                response.setStatusCode(404);
                return response;
            }

            // Kiểm tra khách hàng tồn tại
            Optional<Customer> customerOptional = customerRepo.findById(customerId);
            if (customerOptional.isEmpty()) {
                response.setMessage("Customer ID not found.");
                response.setSuccess(false);
                response.setStatusCode(404);
                return response;
            }

            // Kiểm tra nếu khách hàng đã áp dụng khuyến mãi trước đó
            boolean promotionApplied = false;
            for (CartItemResponse item : cart.getItems()) {
                double originalAmount = item.getUnitPrice() * item.getQuantity();
                if (item.getAmount() < originalAmount) {
                    promotionApplied = true;
                    break;
                }
            }

            if (!promotionApplied) {
                response.setMessage("No promotion applied to the cart.");
                response.setSuccess(false);
                response.setStatusCode(400);
                return response;
            }

            // Loại bỏ giảm giá cho các sản phẩm trong giỏ hàng
            for (CartItemResponse item : cart.getItems()) {
                double originalAmount = item.getUnitPrice() * item.getQuantity();
                item.setAmount(originalAmount);
            }

            // Cập nhật tổng giá trị của giỏ hàng
            double totalPrice = cart.getItems().stream().mapToDouble(CartItemResponse::getAmount).sum();
            cart.setTotalPrice(totalPrice);

            cartStorage.put(cartId, cart);

            response.setResult(cart);
            response.setMessage("Promotion removed successfully.");
            response.setSuccess(true);
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error removing promotion.");
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public Response refreshCart(int customerID) {
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

            boolean updated = false;
            List<String> updatedProducts = new ArrayList<>();

            // Refresh each item in the cart
            Iterator<CartItemResponse> iterator = cartResponse.getItems().iterator();
            while (iterator.hasNext()) {
                CartItemResponse item = iterator.next();
                Product product = findProductById(item.getProductId(), item.getProductType());

                if (product == null || !isProductAvailable(product) || product.getQuantity() == 0) {
                    iterator.remove();
                    updated = true;
                    if (product == null || !isProductAvailable(product)) {
                        updatedProducts.add(item.getProductName() + " (Removed: Product not available)");
                    } else if (product.getQuantity() == 0) {
                        updatedProducts.add(item.getProductName() + " (Removed: Out of stock)");
                    }
                    continue;
                }

                boolean itemUpdated = false;

                if (!item.getProductName().equals(product.getName())) {
                    item.setProductName(product.getName());
                    itemUpdated = true;
                }

                if (item.getUnitPrice() != product.getPrice()) {
                    item.setUnitPrice(product.getPrice());
                    itemUpdated = true;
                }

                if (item.getQuantity() > product.getQuantity()) {
                    item.setQuantity(product.getQuantity());
                    item.setAmount(product.getQuantity() * item.getUnitPrice());
                    itemUpdated = true;
                } else {
                    item.setAmount(item.getQuantity() * item.getUnitPrice());
                }

                if (itemUpdated) {
                    updated = true;
                    updatedProducts.add(item.getProductName());
                }
            }

            cartStorage.put(cartResponse.getCartId(), cartResponse);

            if (updated) {
                response.setMessage("UPDATED! " + String.join(", ", updatedProducts));
            } else {
                response.setMessage("NOT UPDATED!");
            }

            response.setSuccess(true);
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

    private boolean isProductAvailable(Product product) {
        if (product instanceof Diamond) {
            return ((Diamond) product).isStatusDiamond();
        } else if (product instanceof DiamondShell) {
            return ((DiamondShell) product).isStatusDiamondShell();
        }
        return false;
    }
}
