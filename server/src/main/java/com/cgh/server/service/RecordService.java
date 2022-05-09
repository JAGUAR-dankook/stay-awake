package com.cgh.server.service;

import com.cgh.server.domain.Member;
import com.cgh.server.domain.Record;
import com.cgh.server.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {

    RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Record> findRecordsByMember(Member member) {
        return recordRepository.findRecordsByMember(member);
    }

    public void save(Record record) {
        recordRepository.save(record);
    }

    public Optional<Record> findRecordByStartDateAndMember(LocalDate startDate, Member member) {
        return recordRepository.findRecordByStartDateAndMember(startDate, member);
    }
}
