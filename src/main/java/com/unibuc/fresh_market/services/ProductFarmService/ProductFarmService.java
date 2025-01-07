package com.unibuc.fresh_market.services.ProductFarmService;

import com.unibuc.fresh_market.domain.ProductFarm;

import java.util.List;
import java.util.Optional;

public interface ProductFarmService {
    ProductFarm createProductFarm(ProductFarm productFarm);
    List<ProductFarm> getAllProductFarms();
    Optional<ProductFarm> getProductFarmById(Integer id);
    Optional<ProductFarm> updateProductFarm(Integer id, ProductFarm productFarm);
    Optional<ProductFarm> deleteProductFarm(Integer id);

    List<ProductFarm> getProductFarmsByFarmId(Integer farmId);
    List<ProductFarm> getProductFarmsByProductId(Integer productId);
    Integer calculateProductStockAcrossFarms(Integer productId);
}
