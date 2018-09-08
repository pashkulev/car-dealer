package com.vankata.cardealer.domain.dto.log;

import com.vankata.cardealer.domain.enums.Operation;

import java.time.LocalDateTime;

public class LogDto {

    private String userUsername;

    private Operation operation;

    private String modifiedTable;

    private LocalDateTime time;

    public String getUserUsername() {
        return this.userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getModifiedTable() {
        return this.modifiedTable;
    }

    public void setModifiedTable(String modifiedTable) {
        this.modifiedTable = modifiedTable;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
