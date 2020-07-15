package com.example.accessingdatamysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.accessingdatamysql.entity.*;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface OwnCardRepository extends JpaRepository<OwnCard, Integer> {
    @Modifying
    @Transactional
    void deleteOwnCardByUserIdEqualsAndCardIdEquals(Integer userId, Integer cardId);

    @Query(value = "SELECT u from OwnCards u where u.userId = ?1", nativeQuery = true)
    List<OwnCard> findByUserId(Integer userId);

    // 这里还没有测试过可能会出现bug
    @Query(value = "SELECT u from OwnCards u where u.userId = ?1 and u.cardId = ?2", nativeQuery = true)
    OwnCard findByUserIdCardId(Integer userId, Integer cardId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE OwnCard u SET u = :newOwnCard WHERE u.ownCardId = :ownCardId")
    int updateOwnCardStatus(@Param("newOwnCard") OwnCard newOwnCard, @Param("ownCardId") Integer ownCardId);

}
