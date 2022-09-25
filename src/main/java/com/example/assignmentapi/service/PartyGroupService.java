package com.example.assignmentapi.service;

import com.example.assignmentapi.model.PartyGroup;
import com.example.assignmentapi.repository.PartyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyGroupService {

    @Autowired
    private PartyGroupRepository partyGroupRepository;

    public PartyGroup create(PartyGroup partyGroup) {
        return partyGroupRepository.save(partyGroup);
    }

    public void deleteByPartyId(Long id, Long userId) {
        partyGroupRepository.deleteByPartyId(id, userId);
    }

    public Long countByPartyId(Long id, String userId) {
        return partyGroupRepository.countByPartyId(id, userId);
    }

    public Long countByPartyId(Long id) {
        return partyGroupRepository.countByPartyId(id);
    }
}
