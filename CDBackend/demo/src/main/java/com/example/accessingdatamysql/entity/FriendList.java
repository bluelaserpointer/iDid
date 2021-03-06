package com.example.accessingdatamysql.entity;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "friend_list", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "friendListId")

public class FriendList {
    // 好友名单Id（应该跟用户的userId保持一致）
    @Id
    @Column(name = "friend_list_id", nullable = false)
    private Integer friendListId;
    // 好友的userIds
    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> friendIds;

    public FriendList() {}
    // friendListId应该要等于userId（一对一关系，（不用自动生成的方法了））
    public FriendList(Integer userId) {
        this.friendListId = userId;
    }

    public Integer getFriendListId() {
        return friendListId;
    }

    // 应该不会更改friendListid
    public void setFriendListId(Integer FriendListId) {
        this.friendListId = FriendListId;
    }

    public List<Integer> getFriendIds() {
        return this.friendIds;
    }

    public void setFriendIds(List<Integer> friendIds) {
        this.friendIds = friendIds;
    }

}
