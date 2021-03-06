package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.Classes.PaginationJpaRepository;
import org.springframework.data.jpa.repository.*;

import com.example.accessingdatamysql.entity.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.transaction.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends PaginationJpaRepository<User, Integer> {
    User findUserByUserNameEquals(String userName);

    // @Query("SELECT t.title FROM Todo t where t.id = :id")
    // String findTitleById(@Param("id") Long id);
    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE User u SET u.access = :newaccess WHERE u.userId =
    // :userId")
    // int updateAccessStatus(@Param("newaccess") Boolean newaccess,
    // @Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u = :newUser WHERE u.userId = :UserId")
    void updateUserStatus(@Param("newUser") User newUser, @Param("UserId") Integer UserId);

    @Transactional
    @Query(value = "SELECT * from user LIMIT ?1,?2", nativeQuery = true)
    List<User> ListPage(Integer start, Integer end);

    // List<User> findByFirstName(@Param("firstname") String firstname);
}
