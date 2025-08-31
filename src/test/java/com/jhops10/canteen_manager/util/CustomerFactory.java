package com.jhops10.canteen_manager.util;

import com.jhops10.canteen_manager.model.Customer;
import org.instancio.Instancio;

import java.util.ArrayList;

import static org.instancio.Select.field;

public class CustomerFactory {

    public static Customer createDefaultCustomer(Long id) {
        return Instancio.of(Customer.class)
                .set(field(Customer::getId), id)
                .set(field(Customer::getOrders), new ArrayList<> ())
                .create();
    }
}
