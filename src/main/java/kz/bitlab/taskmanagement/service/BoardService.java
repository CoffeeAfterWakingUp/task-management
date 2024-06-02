package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.entity.Board;

import java.util.List;

public interface BoardService {

    Board create(Board board, Long workspaceId);

    Board getById(Long id);

    void updateCardOrders(List<Integer> cardIds, Long boardId);

    void delete(Board board);
}
