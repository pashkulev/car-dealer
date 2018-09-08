package com.vankata.cardealer.domain.dto.part;

public class FormPartDto extends PartDto {

    private long supplierId;

    public FormPartDto() {
        super();
    }

    public long getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }
}
