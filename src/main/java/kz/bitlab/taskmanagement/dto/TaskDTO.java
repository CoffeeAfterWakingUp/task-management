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
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private String priority;
    @JsonFormat(pattern = "dd MMM yyyy")
    private LocalDate deadlineTime;
    @JsonFormat(pattern = "dd MMM yyyy")
    private LocalDate createdTime;
    private String author;
    private String worker;

}
