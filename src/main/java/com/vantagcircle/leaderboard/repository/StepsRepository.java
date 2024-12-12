package com.vantagcircle.leaderboard.repository;

import com.vantagcircle.leaderboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StepsRepository extends JpaRepository<User, Long> {

    @Query("SELECT d FROM User d WHERE d.stepsCount > 0")
    List<User> findAllSteps();
}
