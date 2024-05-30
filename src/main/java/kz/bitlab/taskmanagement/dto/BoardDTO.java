package kz.bitlab.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long id;
    private String title;
    private String description;
    private int membersCount;
    private int cardsCount;
    private LocalDateTime createdTime;
    private String boardVisibility;
    private List<CardDTO> cards;
    private Long workspaceId;
    private String workspaceTitle;
}
