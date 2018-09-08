package com.vankata.cardealer.domain.dto.supplier;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SupplierDto {

    private long id;

    @NotBlank(message = "Supplier Name is required!")
    @Size(min = 2, message = "Name should contain at least 2 characters!")
    private String name;

    private boolean isImporter;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsImporter() {
        return this.isImporter;
    }

    public void setIsImporter(boolean importer) {
        this.isImporter = importer;
    }
}
