package com.dev.magazina.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
class WarehouseProductKey implements Serializable {

    @Column(name = "warehouse_id")
    int warehouseId;

    @Column(name = "product_id")
    int productId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}