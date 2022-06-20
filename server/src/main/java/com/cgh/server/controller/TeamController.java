package com.cgh.server.controller;

import com.cgh.server.domain.Member;
import com.cgh.server.domain.Team;
import com.cgh.server.dto.TeamDto;
import com.cgh.server.service.MemberService;
import com.cgh.server.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService teamService;
    @Autowired
    MemberService memberService;

    @GetMapping("")
    public String team(Model model, @AuthenticationPrincipal User user) {
        if (memberService.findByUsername(user.getUsername()).isPresent()) {
            Member member = memberService.findByUsername(user.getUsername()).get();
            if (!teamService.findAllByMember(member).isEmpty()) {
                model.addAttribute("team", teamService.findAllByMember(member));
            }
        }
        return "team";
    }

    @GetMapping("/{id}")
    public String loadTeam(@PathVariable("id") Long id, Model model) {
        Team team = teamService.findById(id).get();
        model.addAttribute("team", team);
        model.addAttribute("members", memberService.findMemberByTeam(team));
        return "team_info";
    }

    @GetMapping("/create")
    public String createTeam(Model model) {
        model.addAttribute("team", new Team());
        return "team_create";
    }

    @PostMapping("/create")
    public String create(Team team, @AuthenticationPrincipal User user) {
        Member member;
        if (memberService.findByUsername(user.getUsername()).isPresent()) {
            member = memberService.findByUsername(user.getUsername()).get();
            team.addMember(member);
            teamService.save(team);
            member.addTeam(team);
            memberService.save(member);
        }
        return "redirect:/team";
    }

    @GetMapping("/join")
    public String joinTeam(Model model) {
        TeamDto teamDto = new TeamDto();
        model.addAttribute("team", teamDto);
        return "team_join";
    }

    @PostMapping("/join")
    public String join(TeamDto teamDto, @AuthenticationPrincipal User user) {
        Member member;
        Team team;

        if (teamService.findById(teamDto.getId()).isPresent() && memberService.findByUsername(user.getUsername()).isPresent()) {
            team = teamService.findById(teamDto.getId()).get();
            member = memberService.findByUsername(user.getUsername()).get();
            team.addMember(member);
            teamService.save(team);
            member.addTeam(team);
            memberService.save(member);
        }

        return "redirect:/team";
    }

}
