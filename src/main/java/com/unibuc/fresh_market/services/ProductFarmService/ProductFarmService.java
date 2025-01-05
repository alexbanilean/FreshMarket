package com.unibuc.fresh_market.services.ProductFarmService;

import com.unibuc.fresh_market.domain.ProductFarm;

import java.util.List;

public interface ProductFarmService {
    ProductFarm createProductFarm(ProductFarm productFarm);
    ProductFarm getProductFarmById(Integer id);
    List<ProductFarm> getAllProductFarms();
    ProductFarm updateProductFarm(Integer id, ProductFarm productFarm);
    void deleteProductFarm(Integer id);

    List<ProductFarm> getProductFarmsByFarmId(Integer farmId);
    List<ProductFarm> getProductFarmsByProductId(Integer productId);
    double calculateProductStockAcrossFarms(Integer productId);
}
