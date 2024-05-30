package kz.bitlab.taskmanagement.util.comparator;

import kz.bitlab.taskmanagement.entity.Card;

import java.util.Comparator;

public class SortByCardOrder implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return Integer.compare(o1.getCardOrder(), o2.getCardOrder());
    }
}
