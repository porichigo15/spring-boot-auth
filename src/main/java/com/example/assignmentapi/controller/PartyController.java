package com.example.assignmentapi.controller;

import com.example.assignmentapi.model.Party;
import com.example.assignmentapi.model.PartyGroup;
import com.example.assignmentapi.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @GetMapping("/find-parties")
    public ResponseEntity<List<Party>> findParties() {
        List<Party> result = partyService.findParties();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Party> get(@PathVariable("id") Long id) {
        Party result = partyService.get(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<Party> create(@RequestBody Party party) {
        Party result = partyService.create(party);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Party> update(@PathVariable("id") Long id, @RequestBody Party party) {
        Party result = partyService.update(id, party);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        partyService.deleteById(id);
    }

    @PostMapping("/join")
    public ResponseEntity<PartyGroup> join(@RequestBody Party party) {
        PartyGroup result = partyService.join(party);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/un-join")
    public void unJoin(@RequestBody Party party) {
        partyService.unJoin(party);
    }
}
