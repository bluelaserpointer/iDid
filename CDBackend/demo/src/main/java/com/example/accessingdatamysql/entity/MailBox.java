package com.example.accessingdatamysql.entity;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "MailBox", schema = "cardgame")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "mailBoxId")

public class MailBox {
    @Id
    @Column(name = "mailBoxId", nullable = false)
    private Integer mailBoxId;

    @ManyToMany
    @JoinColumn(name = "mails", unique = false)
    private List<Mail> mails;

    // mailBoxid应该要等于userId（一对一关系，（不用自动生成的方法了））
    public MailBox(Integer mailBoxId) {
        this.mailBoxId = mailBoxId;
    }

    public Integer getMailBoxId() {
        return mailBoxId;

    }

    // 应该不会更改mailboxid
    public void setMailBoxId(Integer mailBoxId) {
        this.mailBoxId = mailBoxId;
    }

    public List<Mail> getMails() {
        return this.mails;
    }

    public void setMails(List<Mail> mails) {
        this.mails = mails;
    }

}