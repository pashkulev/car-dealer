package com.vankata.cardealer.domain.dto.part;

public class PartDto extends SimplePartDto {

    private long id;

    private long quantity;

    public PartDto() {
        this.setQuantity(1);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
