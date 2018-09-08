package com.vankata.cardealer.service;

import com.vankata.cardealer.domain.dto.log.LogDto;
import com.vankata.cardealer.domain.entity.Log;
import com.vankata.cardealer.domain.mapper.ModelParser;
import com.vankata.cardealer.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private final LogsRepository logsRepository;

    private final ModelParser modelParser;

    @Autowired
    public LogServiceImpl(LogsRepository logsRepository, ModelParser modelParser) {
        this.logsRepository = logsRepository;
        this.modelParser = modelParser;
    }

    @Override
    public boolean saveLog(Log log) {
        return this.logsRepository.save(log).getId() != 0;
    }

    @Override
    public List<LogDto> findAll() {
        List<Log> logs = this.logsRepository.findAll();
        LogDto[] logDtos = this.modelParser.convert(logs, LogDto[].class);
        return Arrays.asList(logDtos);
    }

    @Override
    public List<LogDto> findLogsByUsername(String username) {
        List<Log> logs =  this.logsRepository.findAllByUsername(username);
        LogDto[] logDtos = this.modelParser.convert(logs, LogDto[].class);
        return Arrays.asList(logDtos);
    }

    @Override
    public void deleteAll() {
        this.logsRepository.deleteAll();
    }
}
