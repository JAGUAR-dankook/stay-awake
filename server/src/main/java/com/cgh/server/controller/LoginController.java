package com.cgh.server.controller;

import com.cgh.server.domain.Member;
import com.cgh.server.domain.Role;
import com.cgh.server.domain.dto.LoginRequest;
import com.cgh.server.domain.dto.RegistryRequest;
import com.cgh.server.service.LoginService;
import com.cgh.server.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("")
    public String login(Model model) {
        model.addAttribute("member", new LoginRequest());
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("member", new RegistryRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute RegistryRequest registryRequest) {
        Member member = new Member(registryRequest.getUsername(), passwordEncoder.encode(registryRequest.getPassword()));
        memberService.save(member);
        return "redirect:/login";
    }

    @ModelAttribute("roles")
    public Map<String, Role> roles() {
        Map<String, Role> map = new LinkedHashMap<>();
        map.put("관리자", Role.ROLE_ADMIN);
        map.put("매니저", Role.ROLE_MANAGER);
        map.put("일반 사용자", Role.ROLE_MEMBER);
        return map;
    }
}
