package com.cgh.server.repository;

import com.cgh.server.domain.Member;
import com.cgh.server.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findRecordsByMember(Member member);

    Optional<Record> findRecordByStartDateAndMember(LocalDate startDate, Member member);

    List<Record> findRecordsByStartDateBetweenAndMember(LocalDate start, LocalDate end, Member member);

}
