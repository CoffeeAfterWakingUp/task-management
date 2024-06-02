package kz.bitlab.taskmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "register_time", nullable = false)
    private LocalDateTime registerTime;

    @OneToMany(mappedBy = "user")
    private Set<WorkspaceMember> workspaces = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Task> authorTasks = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Task> workerTasks = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_favorite_boards",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Board> favoriteBoards = new HashSet<>();

    public void addFavoriteBoard(Board board) {
        if (favoriteBoards == null) favoriteBoards = new HashSet<>();
        favoriteBoards.add(board);
        Set<User> favoritedUsers = board.getFavoritedUsers();
        if (favoritedUsers == null) favoritedUsers = new HashSet<>();
        favoritedUsers.add(this);
    }

    public void removeFavoriteBoard(Board board) {
        favoriteBoards.remove(board);
        board.getFavoritedUsers().remove(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registerTime=" + registerTime +
                '}';
    }

    public User() {
        this.registerTime = LocalDateTime.now();
    }
}
