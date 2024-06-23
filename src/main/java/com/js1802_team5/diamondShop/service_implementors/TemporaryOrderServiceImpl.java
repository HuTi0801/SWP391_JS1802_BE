package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.Order;
import com.js1802_team5.diamondShop.services.TemporaryOrderService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TemporaryOrderServiceImpl implements TemporaryOrderService {
    private Map<String, Order> temporaryOrders = new ConcurrentHashMap<>();

    @Override
    public void saveTemporaryOrder(String key, Order temporaryOrder) {
        temporaryOrders.put(key, temporaryOrder);
    }

    @Override
    public Order getTemporaryOrder(String key) {
        return temporaryOrders.get(key);
    }

    @Override
    public void removeTemporaryOrder(String key) {
        temporaryOrders.remove(key);
    }
}
