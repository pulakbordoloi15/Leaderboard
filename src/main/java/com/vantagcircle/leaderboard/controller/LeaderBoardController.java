package com.vantagcircle.leaderboard.controller;

import com.vantagcircle.leaderboard.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leaderboards")
public class LeaderBoardController {

    @Autowired
    private LeaderBoardService leaderBoardService;

    //To fetch individual score and exceptions if data not found
    @GetMapping("/individualscore")
    public ResponseEntity<List<Map<String, Object>>> getIndividualLeaderboard() {
        try {
            return ResponseEntity.ok(leaderBoardService.getIndividualLeaderboard());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(List.of(Map.of("error", e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of(Map.of("error", "An error occurred while processing the request")));
        }
    }

    //To fetch team score
    @GetMapping("/teamscore")
    public List<Map<String, Object>> getTeamLeaderboard() {

        return leaderBoardService.getTeamLeaderboard();
    }
    //To fetch User statistics
    @GetMapping("/userstats")
    public List<Map<String, Object>> getStepStatisticsPerUser() {
        return leaderBoardService.getStatisticsPerUser();
    }
}
