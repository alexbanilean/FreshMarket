package com.unibuc.fresh_market.services.ProductOrderService;

import com.unibuc.fresh_market.domain.Product;
import com.unibuc.fresh_market.domain.ProductOrder;
import com.unibuc.fresh_market.repositories.ProductOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderServiceImplementation implements ProductOrderService {
    private final ProductOrderRepository productOrderRepository;

    public ProductOrderServiceImplementation(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    public ProductOrder createProductOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    public List<ProductOrder> getAllProductOrders() {
        return productOrderRepository.findAll();
    }

    public Optional<ProductOrder> getProductOrderById(Integer id) {
        ProductOrder productOrder = productOrderRepository.findById(id).orElse(null);
        return Optional.ofNullable(productOrder);
    }

    public Optional<ProductOrder> updateProductOrder(Integer id, ProductOrder productOrder) {
        ProductOrder productOrderToUpdate = productOrderRepository.findById(id).orElse(null);
        if (productOrderToUpdate != null) {
            productOrderToUpdate.setQuantity(productOrder.getQuantity());
            productOrderToUpdate.setNotes(productOrder.getNotes());
            productOrderRepository.save(productOrderToUpdate);
            return Optional.of(productOrderToUpdate);
        }

        return Optional.empty();
    }

    public Optional<ProductOrder> deleteProductOrder(Integer id) {
        ProductOrder productOrderToDelete = productOrderRepository.findById(id).orElse(null);
        if (productOrderToDelete != null) {
            productOrderRepository.delete(productOrderToDelete);
            return Optional.of(productOrderToDelete);
        }

        return Optional.empty();
    }

    public List<ProductOrder> getProductOrdersByOrderId(Integer orderId) {
        return productOrderRepository.findProductOrdersByOrderId(orderId);
    }

    public double calculateTotalOrderValue(Integer orderId) {
        List<ProductOrder> productOrders = productOrderRepository.findProductOrdersByOrderId(orderId);

        if (productOrders == null || productOrders.isEmpty()) {
            throw new IllegalArgumentException("No products found for the given order ID: " + orderId);
        }

        double totalValue = productOrders.stream()
                .mapToDouble(productOrder -> {
                    Product product = productOrder.getProduct();
                    if (product == null) {
                        throw new IllegalStateException("Product not found for ProductOrder ID: " + productOrder.getId());
                    }
                    return product.getPrice() * productOrder.getQuantity();
                })
                .sum();

        return totalValue;
    }
}
