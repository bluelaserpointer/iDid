package com.example.accessingdatamysql.daoimpl;

import com.example.accessingdatamysql.dao.ActivityDao;
import com.example.accessingdatamysql.repository.*;
import com.example.accessingdatamysql.entity.*;

// import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// import java.sql.Timestamp;
// import java.io.Console;
import java.util.*;

@Repository
public class ActivityDaoImpl implements ActivityDao {
    @Autowired
    private ActivityRepository ActivityRepository;
    @Autowired
    private ActivityDetailsRepository ActivityDetailsRepository;

    @Override
    public Activity getOneActivity(Integer ActivityId) {
        Activity Activity = ActivityRepository.getOne(ActivityId);
        Optional<ActivityDetails> ActivityDetails = ActivityDetailsRepository
                .findActivityDetailsByActivityIdEquals(ActivityId);
        ActivityDetails.ifPresent(Activity::setActivityDetails);
        return Activity;
    }

    public Activity addNewActivity(Activity newActivity) {

        Activity Activity = new Activity(newActivity.getType(), newActivity.getActivityName(), newActivity.getStart());
        // System.out.println("new Activity has an Id of : " + n.getActivityId());
        ActivityRepository.save(Activity);
        ActivityDetails ActivityDetails = new ActivityDetails(Activity.getActivityId(),
                newActivity.getActivityDetails().getActivityImg(),
                newActivity.getActivityDetails().getActivityDescription());
        ActivityDetailsRepository.save(ActivityDetails);
        return Activity;
    }

    public Activity updateActivity(Activity updateActivity) {

        Activity Activity = ActivityRepository.getOne(updateActivity.getActivityId());

        // System.out.println("old Activity has an Id of : " + n.getActivityId());
        Activity.setActivity(updateActivity.getType(), updateActivity.getActivityName(), updateActivity.getStart());

        ActivityRepository.updateActivityStatus(Activity, updateActivity.getActivityId());

        Optional<ActivityDetails> optActivityDetails = ActivityDetailsRepository
                .findActivityDetailsByActivityIdEquals(updateActivity.getActivityId());
        ActivityDetails activityDetails = new ActivityDetails(updateActivity.getActivityId(), "", "");

        if (optActivityDetails.isPresent()) {
            System.out.println("Activity Exists");
            activityDetails = optActivityDetails.get();
        } else {
            System.out.println("Activity doesn't exist");
        }

        activityDetails.setActivityDescription(updateActivity.getActivityDetails().getActivityDescription());
        activityDetails.setActivityImg(updateActivity.getActivityDetails().getActivityImg());
        ActivityDetailsRepository.save(activityDetails);
        Activity.setActivityDetails(activityDetails);
        return Activity;

    }

    public List<Activity> getAllActivities() {
        List<Activity> Activities = ActivityRepository.findAll();
        for (int i = 0; i < Activities.size(); i++) {
            Activity Activity = Activities.get(i);
            Optional<ActivityDetails> ActivityDetails = ActivityDetailsRepository
                    .findActivityDetailsByActivityIdEquals(Activity.getActivityId());
            ActivityDetails.ifPresent(Activity::setActivityDetails);
            Activities.set(i, Activity);
        }
        return Activities;
    }

    public String deleteActivities(List<Integer> ActivityIds) {
        for (int i = 0; i < ActivityIds.size(); i++) {
            ActivityRepository.deleteById(ActivityIds.get(i));
            ActivityDetailsRepository.deleteActivityDetailsByActivityIdEquals(ActivityIds.get(i));
        }
        return "Deleted Activities by id";
    }

    public String deleteAll() {
        ActivityRepository.deleteAll();
        ActivityDetailsRepository.deleteAll();
        return "Deleted All Activities";
    }

    public List<Activity> deleteActivity(Integer activityId) {
        ActivityRepository.deleteById(activityId);
        ActivityDetailsRepository.deleteActivityDetailsByActivityIdEquals(activityId);
        return getAllActivities();
    }

}
