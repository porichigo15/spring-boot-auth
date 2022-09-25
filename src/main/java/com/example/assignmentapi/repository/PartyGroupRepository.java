package com.example.assignmentapi.repository;

import com.example.assignmentapi.model.PartyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PartyGroupRepository extends JpaRepository<PartyGroup, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM PartyGroup p WHERE p.party.id = :id AND p.user.id = :userId")
    void deleteByPartyId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT COUNT(g.id) FROM PartyGroup g WHERE g.party.id = :id AND g.user.userId = :userId")
    Long countByPartyId(@Param("id") Long id, @Param("userId") String userId);

    @Query("SELECT COUNT(g.id) FROM PartyGroup g WHERE g.party.id = :id")
    Long countByPartyId(@Param("id") Long id);
}
