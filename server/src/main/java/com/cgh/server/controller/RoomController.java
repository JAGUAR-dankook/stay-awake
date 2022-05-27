package com.cgh.server.controller;

import com.cgh.server.domain.Member;
import com.cgh.server.domain.Record;
import com.cgh.server.domain.Subject;
import com.cgh.server.service.MemberService;
import com.cgh.server.service.RecordService;
import com.cgh.server.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    MemberService memberService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    RecordService recordService;

    Member member = new Member();

    @GetMapping("")
    public String selectSubject(Model model, @AuthenticationPrincipal User user) {
        Member member = memberService.findByUsername(user.getUsername()).get();
        model.addAttribute("member", member);

        return "select_room";
    }

    @GetMapping("/create")
    public String createSubject(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject_create";
    }

    @PostMapping("/create")
    public String create(Subject subject, @AuthenticationPrincipal User user) {
        Member member;
        if (memberService.findByUsername(user.getUsername()).isPresent()) {
            member = memberService.findByUsername(user.getUsername()).get();
            member.addSubject(subject);
            memberService.save(member);
        }
        return "redirect:/room";
    }

    @GetMapping("/{id}")
    public String enterRoom(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
        int count = 0;

        if (memberService.findByUsername(user.getUsername()).isPresent()) {
            member = memberService.findByUsername(user.getUsername()).get();
        }
        if (recordService.findRecordByStartDateAndMember(LocalDate.now(), member).isPresent()) {
            count = recordService.findRecordByStartDateAndMember(LocalDate.now(), member).get().getSeconds();
        }
        model.addAttribute("roomId", id);
        model.addAttribute("count", count);
        model.addAttribute("username", member.getUsername());

        return "room";
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String record(HttpServletRequest request, @AuthenticationPrincipal User user) {

        if (request.getParameter("second").equals("")) {
            return null;
        }

        Member member = new Member();
        Record record = new Record();

        if (memberService.findByUsername(user.getUsername()).isPresent()) {
            member = memberService.findByUsername(user.getUsername()).get();
        }
        if (recordService.findRecordByStartDateAndMember(LocalDate.now(), member).isPresent()) {
            record = recordService.findRecordByStartDateAndMember(LocalDate.now(), member).get();
        }

        record.setStartDate(LocalDate.now());
        record.setMember(member);
        record.setSeconds(Integer.parseInt(request.getParameter("second")));
        recordService.save(record);

        return "room";
    }
}
