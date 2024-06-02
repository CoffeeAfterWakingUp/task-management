package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.entity.Card;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import kz.bitlab.taskmanagement.repository.CardRepository;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalInt;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final BoardService boardService;

    @Override
    public Card create(Card card, Long boardId) {
        if (card == null) throw new BadRequestException("Card is null");
        if (boardId == null) throw new BadRequestException("Board id is null");
        String title = card.getTitle();
        if (title == null || title.isEmpty()) throw new BadRequestException("Title is empty!");
        Board board = boardService.getById(boardId);
        card.setCardOrder(getCardOrder(board));
        card.setBoard(board);
        return cardRepository.save(card);
    }

    @Override
    public Card getById(Long id) {
        if (id == null) throw new BadRequestException("Id is null");
        return cardRepository.findById(id).orElseThrow(() -> new NotFoundException("card is not found"));
    }

    @Override
    public void updateAll(List<Card> cards) {
        if (cards == null) throw new BadRequestException("cards is null");
        cardRepository.saveAll(cards);
    }

    private int getCardOrder(Board board) {
        List<Card> cards = board.getCards();
        if (cards == null || cards.isEmpty()) return 1;
        OptionalInt max = cards.stream()
                .map(Card::getCardOrder)
                .mapToInt(Integer::intValue)
                .max();
        return max.getAsInt() + 1;
    }
}
