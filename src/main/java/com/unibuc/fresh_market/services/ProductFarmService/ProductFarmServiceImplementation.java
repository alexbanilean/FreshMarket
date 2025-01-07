package com.unibuc.fresh_market.services.ProductFarmService;

import com.unibuc.fresh_market.domain.ProductFarm;
import com.unibuc.fresh_market.repositories.ProductFarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductFarmServiceImplementation implements ProductFarmService {
    private final ProductFarmRepository productFarmRepository;

    public ProductFarmServiceImplementation(ProductFarmRepository productFarmRepository) {
        this.productFarmRepository = productFarmRepository;
    }

    public ProductFarm createProductFarm(ProductFarm productFarm) {
        return productFarmRepository.save(productFarm);
    }

    public List<ProductFarm> getAllProductFarms() {
        return productFarmRepository.findAll();
    }

    public Optional<ProductFarm> getProductFarmById(Integer id) {
        ProductFarm productFarm = productFarmRepository.findById(id).orElse(null);
        return Optional.ofNullable(productFarm);
    }

    public Optional<ProductFarm> updateProductFarm(Integer id, ProductFarm productFarm) {
        ProductFarm productFarmToUpdate = productFarmRepository.findById(id).orElse(null);
        if (productFarmToUpdate != null) {
            productFarmToUpdate.setQuantity(productFarm.getQuantity());
            productFarmToUpdate.setNotes(productFarm.getNotes());
            productFarmRepository.save(productFarmToUpdate);
            return Optional.of(productFarmToUpdate);
        }

        return Optional.empty();
    }

    public Optional<ProductFarm> deleteProductFarm(Integer id) {
        ProductFarm productFarmToDelete = productFarmRepository.findById(id).orElse(null);
        if (productFarmToDelete != null) {
            productFarmRepository.delete(productFarmToDelete);
            return Optional.of(productFarmToDelete);
        }

        return Optional.empty();
    }

    public List<ProductFarm> getProductFarmsByFarmId(Integer farmId) {
        return productFarmRepository.findAllByFarmId(farmId);
    }

    public List<ProductFarm> getProductFarmsByProductId(Integer productId) {
        return productFarmRepository.findAllByProductId(productId);
    }

    public Integer calculateProductStockAcrossFarms(Integer productId) {
        List<ProductFarm> productFarms = getProductFarmsByProductId(productId);
        return productFarms.stream().mapToInt(ProductFarm::getQuantity).sum();
    }
}
