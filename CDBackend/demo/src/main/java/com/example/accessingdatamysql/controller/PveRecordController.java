package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

import com.example.accessingdatamysql.service.PveRecordService;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/pveRecord") // This means URL's start with /demo (after Application path)
public class PveRecordController {
    @Autowired // This means to get the bean called OwnItemRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private PveRecordService pveRecordService;

    @RequestMapping(value = "/addPveRecord")
    public PveRecord addPveRecord(@RequestParam("userId") Integer userId, @RequestParam("chapterId") Integer chapterId,
                                  @RequestParam("phaseId") Integer phaseId, @RequestParam("result") Integer result,
                                  @RequestParam("posRecord") String posRecord) throws JsonProcessingException {
        return pveRecordService.addPveRecord(userId, chapterId, phaseId, result, posRecord);
    }

    @RequestMapping(value = "/updatePveRecord")
    public PveRecord addPveRecord(@RequestParam("pveRecordId") Integer pveRecordId, @RequestParam("userId") Integer userId,
                                  @RequestParam("chapterId") Integer chapterId, @RequestParam("phaseId") Integer phaseId,
                                  @RequestParam("result") Integer result, @RequestParam("recordTime")Timestamp recordTime,
                                  @RequestParam("posRecord") String posRecord) throws JsonProcessingException {
        return pveRecordService.updatePveRecord(pveRecordId, userId, chapterId, phaseId, result, recordTime, posRecord);
    }

    @RequestMapping(value = "/getAllPveRecords")
    public List<PveRecord> getAllPveRecords() {
        return pveRecordService.getAllPveRecords();
    }

    @RequestMapping(value = "/getPveRecordStatistics")
    public Map<String, Integer> getPveRecordStatistics() {
        return pveRecordService.getPveRecordStatistics();
    }

    @RequestMapping(value = "/getAllPveRecordsByUser")
    public List<PveRecord> getAllPveRecordsByUser(@RequestParam("userId") Integer userId) {
        return pveRecordService.getAllPveRecordsByUser(userId);
    }

    @RequestMapping(value = "/deleteAllPveRecordsByUser")
    public boolean deleteAllPveRecordsByUser(@RequestParam("userId") Integer userId) {
        return pveRecordService.deleteAllPveRecordsByUser(userId);
    }

    @RequestMapping(value = "/deletePveRecords")
    public boolean deletePveRecords(@RequestParam("pveRecordIds") List<Integer> pveRecordIds) {
        return pveRecordService.deletePveRecords(pveRecordIds);
    }

}