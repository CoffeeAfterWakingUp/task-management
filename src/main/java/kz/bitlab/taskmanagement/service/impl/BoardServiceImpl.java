package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.entity.Card;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.enums.BoardVisibility;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import kz.bitlab.taskmanagement.repository.BoardRepository;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.service.CardService;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import kz.bitlab.taskmanagement.util.comparator.SortByCardOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    @Lazy
    private final WorkspaceService workspaceService;
    private final CardService cardService;

    @Override
    public Board create(Board board, Long workspaceId) {
        if (board == null) throw new BadRequestException("board is null");
        if (workspaceId == null) throw new BadRequestException("workspaceId is null");


        String title = board.getTitle();
        BoardVisibility boardVisibility = board.getVisibility();
        if (title == null || title.isEmpty()) throw new BadRequestException("title is empty");
        if (boardVisibility == null) throw new BadRequestException("board visibility is empty");


        Workspace workspace = workspaceService.getById(workspaceId);
        board.setWorkspace(workspace);
        board.setCreatedTime(LocalDateTime.now());
        return boardRepository.save(board);
    }

    @Override
    public Board getById(Long id) {
        if (id == null) throw new BadRequestException("Id is null");
        Optional<Board> boardOpt = boardRepository.findById(id);
        if (boardOpt.isEmpty()) throw new NotFoundException("Board is not found");
        Board board = boardOpt.get();
        board.getCards().sort(new SortByCardOrder());
        return boardOpt.orElseThrow(() -> new NotFoundException("Board is not found"));
    }

    @Override
    public void updateCardOrders(List<Integer> cardIds, Long boardId) {
        if (cardIds == null || cardIds.isEmpty()) throw new BadRequestException("cardIds is empty");
        if (boardId == null) throw new BadRequestException("boardId is empty");

        Board board = getById(boardId);
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < cardIds.size(); i++) {
            Integer cardId = cardIds.get(i);
            Card card = cardService.getById(cardId.longValue());
            card.setCardOrder(i + 1);
            cards.add(card);
        }
        cardService.updateAll(cards);
    }

    @Override
    public void delete(Board board) {
        boardRepository.delete(board);
    }
}
