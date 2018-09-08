package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.log.LogDto;
import com.vankata.cardealer.domain.entity.Log;

import java.util.List;

public interface LogService {

    boolean saveLog(Log log);

    List<LogDto> findAll();

    List<LogDto> findLogsByUsername(String username);

    void deleteAll();
}
