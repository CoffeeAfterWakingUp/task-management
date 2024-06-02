package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.entity.Card;

import java.util.List;

public interface CardService {

    Card create(Card card, Long boardId);

    Card getById(Long id);

    void updateAll(List<Card> cards);
}
