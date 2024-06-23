package com.js1802_team5.diamondShop.services;

import com.js1802_team5.diamondShop.models.entity_models.Order;

public interface TemporaryOrderService {
    public void saveTemporaryOrder(String key, Order temporaryOrder);

    public Order getTemporaryOrder(String key);

    public void removeTemporaryOrder(String key);
}
