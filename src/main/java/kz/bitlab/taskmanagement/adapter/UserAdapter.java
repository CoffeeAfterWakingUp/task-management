package kz.bitlab.taskmanagement.adapter;

import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.BoardDTO;
import kz.bitlab.taskmanagement.dto.FavoriteBoardDTO;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.mapper.BoardMapper;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.util.SessionAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserAdapter {

    private final UserService userService;
    private final BoardMapper boardMapper;

    public ApiResponse<Boolean> addFavoritedBoard(String username, FavoriteBoardDTO favoriteBoardDTO, HttpSession session) {
        if (favoriteBoardDTO == null) throw new BadRequestException("Favorite board is null");
        userService.addFavoriteBoard(username, favoriteBoardDTO.getBoardId());
        Optional<User> userOpt = userService.getByUsername(username);
        Set<BoardDTO> favoritedBoards = boardMapper.toDTOs(userOpt.get().getFavoriteBoards());
        session.setAttribute(SessionAttribute.FAVORITED_BOARDS, favoritedBoards);
        return ApiResponse.<Boolean>builder().body(Boolean.TRUE).status(HttpStatus.CREATED.value()).build();
    }

    public ApiResponse<Boolean> removeFavoritedBoard(String username, FavoriteBoardDTO favoriteBoardDTO, HttpSession session) {
        if (favoriteBoardDTO == null) throw new BadRequestException("Favorite board is null");
        userService.removeFavoritedBoard(username, favoriteBoardDTO.getBoardId());
        Optional<User> userOpt = userService.getByUsername(username);
        Set<BoardDTO> favoritedBoards = boardMapper.toDTOs(userOpt.get().getFavoriteBoards());
        session.setAttribute(SessionAttribute.FAVORITED_BOARDS, favoritedBoards);
        return ApiResponse.<Boolean>builder().body(Boolean.TRUE).status(HttpStatus.CREATED.value()).build();
    }
}
