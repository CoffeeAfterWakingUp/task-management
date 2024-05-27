package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.entity.Board;

public interface BoardService {

    Board create(Board board, Long workspaceId);
}
