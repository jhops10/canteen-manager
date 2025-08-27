package com.jhops10.canteen_manager.util;

import com.jhops10.canteen_manager.model.Product;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public class ProductFactory {

    public static Product createDefaultProduct(Long id) {
        return Instancio.of(Product.class)
                .set(field(Product::getId), id)
                .create();
    }
}
