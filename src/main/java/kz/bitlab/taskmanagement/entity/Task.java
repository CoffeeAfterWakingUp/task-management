package kz.bitlab.taskmanagement.entity;


import jakarta.persistence.*;
import kz.bitlab.taskmanagement.enums.TaskPriority;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    private User worker;

    @ManyToOne(fetch = FetchType.EAGER)
    private Card card;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @Column(name = "deadline_time", nullable = false)
    private LocalDate deadlineTime;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", worker=" + worker +
                ", card=" + card +
                ", priority=" + priority +
                ", deadlineTime=" + deadlineTime +
                ", createdTime=" + createdTime +
                '}';
    }
}
