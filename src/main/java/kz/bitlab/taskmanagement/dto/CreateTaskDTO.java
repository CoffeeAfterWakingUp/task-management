package kz.bitlab.taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDTO {

    private String taskTitle;
    private String taskDescription;
    private String worker;
    private String author;
    private String priority;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadlineTime;
    private Long cardId;
}
