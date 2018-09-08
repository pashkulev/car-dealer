package com.vankata.cardealer.domain.dto.supplier;

public class SupplierDtoWithPartsCount extends SupplierDto {

    private int partsCount;

    public int getPartsCount() {
        return this.partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
