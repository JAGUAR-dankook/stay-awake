package com.cgh.server.service;

import com.cgh.server.domain.Team;
import com.cgh.server.domain.Member;
import com.cgh.server.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAllByMember(Member member) {
        return teamRepository.findAllByMember(member);
    }

    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }


    public void save(Team team) {
        teamRepository.save(team);
    }
}
