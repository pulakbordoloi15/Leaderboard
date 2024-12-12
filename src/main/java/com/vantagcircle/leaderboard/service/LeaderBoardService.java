package com.vantagcircle.leaderboard.service;

import com.vantagcircle.leaderboard.entity.User;
import com.vantagcircle.leaderboard.repository.StepsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaderBoardService {

    @Autowired
    private StepsRepository stepsRepository;

    public List<Map<String, Object>> getIndividualLeaderboard() {
        List<User> steps = stepsRepository.findAllSteps();

        //If no data found
        if (steps.isEmpty()) {
            throw new IllegalStateException("No data found");
        }

        //A map (userScores) is created which is grouping users by their userId, and their scores
        Map<Long, Integer> userScores = steps.stream()
                .collect(Collectors.groupingBy(
                        User::getUserId,
                        Collectors.summingInt(s -> s.getStepsCount() / 125)));

        //The map(Leaderboard) is sorted in decreasing order in order sort the scores rank wise.
        List<Map<String, Object>> leaderboard = userScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue(Comparator.reverseOrder()))
                .map(entry -> {
                    Long userId = entry.getKey();
                    int totalScore = entry.getValue();

                    // Find username for the user ensuring that the first user with a matching userId is returned.
                    // It doesn't continue searching the rest of the list after the first match is found
                    String username = steps.stream()
                            .filter(s -> s.getUserId().equals(userId))
                            .map(User::getUsername)
                            .findFirst()
                            .orElse("Unknown");

                    // Create the leaderboard entry
                    Map<String, Object> leaderboardEntry = new LinkedHashMap<>();
                    leaderboardEntry.put("userId", userId);
                    leaderboardEntry.put("username", username);
                    leaderboardEntry.put("totalScore", totalScore);
                    return leaderboardEntry;
                })
                .collect(Collectors.toList());

        //Assigning rank
        for (int i = 0; i < leaderboard.size(); i++) {
            leaderboard.get(i).put("rank", i + 1);
        }

        return leaderboard;
    }


    public List<Map<String, Object>> getTeamLeaderboard() {
        List<User> steps = stepsRepository.findAllSteps();

        if (steps.isEmpty()) {
            throw new IllegalStateException("No steps data found");
        }

        //Similarly, counting the total score, team wise and grouping them
        Map<Long, Integer> teamScores = steps.stream()
                .collect(Collectors.groupingBy(
                        User::getTeamId,
                        Collectors.summingInt(s -> s.getStepsCount() / 125)));

        //Sorting them in descending order,to sort rank wise
        List<Map<String, Object>> leaderboard = teamScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue(Comparator.reverseOrder()))
                .map(entry -> {
                    Long teamId = entry.getKey();
                    int totalTeamScore = entry.getValue();


                    String teamName = steps.stream()
                            .filter(s -> s.getTeamId().equals(teamId))
                            .map(User::getTeamName)
                            .findFirst()
                            .orElse("Unknown");

                    // Create the leaderboard entry
                    Map<String, Object> leaderboardEntry = new LinkedHashMap<>();
                    leaderboardEntry.put("teamName", teamName);
                    leaderboardEntry.put("teamId", teamId);
                    leaderboardEntry.put("totalTeamScore", totalTeamScore);
                    return leaderboardEntry;
                })
                .collect(Collectors.toList());

        //Assigning rank
        for (int i = 0; i < leaderboard.size(); i++) {
            leaderboard.get(i).put("rank", i + 1);
        }

        return leaderboard;
    }
    public List<Map<String, Object>> getStatisticsPerUser() {
        List<User> steps = stepsRepository.findAllSteps();

        if (steps.isEmpty()) {
            throw new IllegalStateException("No steps data found");
        }


        List<Map<String, Object>> statistics = steps.stream()
                .collect(Collectors.groupingBy(User::getUserId))
                .entrySet().stream()
                .map(entry -> {
                    Long userId = entry.getKey();
                    List<Integer> stepCounts = entry.getValue().stream()
                            .map(User::getStepsCount)
                            .toList();

                    int minSteps = stepCounts.stream().min(Integer::compareTo).orElse(0);
                    int maxSteps = stepCounts.stream().max(Integer::compareTo).orElse(0);
                    int meanSteps = (int) stepCounts.stream().mapToInt(Integer::intValue).average().orElse(0);

                    Map<String, Object> statsEntry = new LinkedHashMap<>();
                    statsEntry.put("userId", userId);
                    statsEntry.put("username", entry.getValue().get(0).getUsername());
                    statsEntry.put("minSteps", minSteps);
                    statsEntry.put("meanSteps", meanSteps);
                    statsEntry.put("maxSteps", maxSteps);
                    return statsEntry;
                })
                .sorted(Comparator.comparing(entry -> entry.get("username").toString()))
                .toList();


        for (int i = 0; i < statistics.size(); i++) {
            statistics.get(i).put("rank", i + 1);
        }

        return statistics;
    }

}