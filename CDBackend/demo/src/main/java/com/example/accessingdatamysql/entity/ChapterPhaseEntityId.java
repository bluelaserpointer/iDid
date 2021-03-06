package com.example.accessingdatamysql.entity;
import java.io.Serializable;

public class ChapterPhaseEntityId implements Serializable {
    private Integer chapterId;
    private Integer phaseId;

    public ChapterPhaseEntityId(){};

    public ChapterPhaseEntityId(Integer chapterId, Integer phaseId) {
        this.chapterId = chapterId;
        this.phaseId = phaseId;
    }

    public Integer getChapterId() {
        return chapterId;
    }
    public Integer getPhaseId() {
        return phaseId;
    }
}
