package com.example.accessingdatamysql.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.dao.*;
import com.example.accessingdatamysql.entity.*;
import com.example.accessingdatamysql.service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardDao CardDao;

    @Override
    public Card getOneCard(Integer CardId) {
        return CardDao.getOneCard(CardId);
    }

    public Card addNewCard(Card newCard) {
        return CardDao.addNewCard(newCard);
    }

    public Card updateCard(Card updateCard) {
        return CardDao.updateCard(updateCard);
    }

    public List<Card> getAllCards() {
        return CardDao.getAllCards();
    }

    public String deleteCards(List<Integer> CardIds) {
        return CardDao.deleteCards(CardIds);
    }

    public String deleteAll() {
        return CardDao.deleteAll();
    }

    public List<Card> deleteCard(Integer cardId) {
        return CardDao.deleteCard(cardId);
    }

    @Override
    public List<Card> getByRarityAndType(String rarity, Integer type) {
        return CardDao.getByRarityAndType(rarity, type);
    }

    @Override
    public JSONObject ListPage(ListRequest listRequest) {
        return CardDao.ListPage(listRequest.getPageToken(), listRequest.getPageSize());
    }
}
