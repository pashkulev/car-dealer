package com.vankata.cardealer.domain.dto.part;

import com.vankata.cardealer.domain.dto.supplier.SupplierDto;

public class ViewPartDto extends PartDto {

    private SupplierDto supplier;

    public SupplierDto getSupplier() {
        return this.supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }
}
