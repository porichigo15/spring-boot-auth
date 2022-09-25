package com.example.assignmentapi.service;

import com.example.assignmentapi.model.Party;
import com.example.assignmentapi.model.PartyGroup;
import com.example.assignmentapi.model.User;
import com.example.assignmentapi.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyGroupService partyGroupService;

    @Autowired
    private UserService userService;

    public List<Party> findParties() {
        User user = userService.getUserDetails();
        List<Party> parties = partyRepository.findAll();
        parties.forEach(item -> {
            Long member = partyGroupService.countByPartyId(item.getId());
            Long join = partyGroupService.countByPartyId(item.getId(), user.getUserId());
            item.setMember(member);
            item.setIsJoin(join > 0);
            item.setDisabled(item.getTotal().equals(member));
        });
        return parties;
    }

    public Party create(Party data) {
        data.setCreatedDate(LocalDateTime.now());
        return partyRepository.save(data);
    }

    public Party update(Long id, Party data) {
        data.setId(id);
        return partyRepository.save(data);
    }

    public void deleteById(Long id) {
        partyRepository.deleteById(id);
    }

    public Party get(Long id) {
        return partyRepository.findById(id).orElse(null);
    }

    public PartyGroup join(Party party) {
        PartyGroup partyGroup = new PartyGroup();

        // Set party object
        partyGroup.setParty(party);

        // Set user object
        User user = userService.getUserDetails();
        partyGroup.setUser(user);

        return partyGroupService.create(partyGroup);
    }

    public void unJoin(Party party) {
        User user = userService.getUserDetails();
        partyGroupService.deleteByPartyId(party.getId(), user.getId());
    }
}
