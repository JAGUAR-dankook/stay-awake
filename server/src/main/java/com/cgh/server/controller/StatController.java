package com.cgh.server.controller;

import com.cgh.server.domain.Member;
import com.cgh.server.domain.Record;
import com.cgh.server.service.MemberService;
import com.cgh.server.service.RecordService;
import com.cgh.server.util.TimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/stat")
public class StatController {
    final String NO_RECORD = "00:00:00";

    @Autowired
    RecordService recordService;
    @Autowired
    MemberService memberService;

    @GetMapping("")
    public String stat(Model model, @AuthenticationPrincipal User user) {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.KOREA).dayOfWeek();
        Member member = memberService.findByUsername(user.getUsername()).get();
        String dayRecord = NO_RECORD;
        String weekRecord = NO_RECORD;

        if (recordService.findRecordByStartDateAndMember(LocalDate.now(), member).isPresent()) {
            dayRecord = TimeFormatter.format(recordService.findRecordByStartDateAndMember(LocalDate.now(), member).get().getSeconds());
        }
        if (!recordService.findRecordsByStartDateBetweenAndMember(now.with(fieldISO, 1), now, member).isEmpty()) {
            weekRecord = TimeFormatter.format(sumRecords(recordService.findRecordsByStartDateBetweenAndMember(now.with(fieldISO, 1), now, member)));
        }

        model.addAttribute("dayrecord", dayRecord);
        model.addAttribute("weekrecord", weekRecord);

        return "status";
    }

    private int sumRecords(List<Record> records) {
        int result = 0;
        for (Record r : records) {
            result += r.getSeconds();
        }
        return result;
    }

}
