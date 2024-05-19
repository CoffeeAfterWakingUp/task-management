package kz.bitlab.taskmanagement.entity;

import jakarta.persistence.*;
import kz.bitlab.taskmanagement.enums.WorkspaceVisibility;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private Workspace workspace;



    @Enumerated(value = EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private WorkspaceVisibility visibility;




}
