package com.vianavitor.enterprisechat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

enum PriorityLevel {
    NORMAL("normal"), IMPORTANT("important"), VERY_IMPORTANT("very-important");

    private final String value;

    PriorityLevel(String value) {
        this.value = value;
    }

    boolean compare(String value) {
        return this.value.equals(value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}

enum State {
    TODO("to-do"), IN_PROGRESS("in-progress"), DONE("done");

    private final String value;

    State(String value) {
        this.value = value;
    }

    boolean compare(String value) {
        return this.value.equals(value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PriorityLevel priority;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "expire_at")
    private LocalDate expireAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPriority() {
        return priority.toString();
    }

    public void setPriority(String priority) {
        switch (priority) {
            case "normal":
                this.priority = PriorityLevel.NORMAL;
                break;
            case "important":
                this.priority = PriorityLevel.IMPORTANT;
                break;
            case "very-important":
                this.priority = PriorityLevel.VERY_IMPORTANT;
                break;
            default:
                throw new IllegalArgumentException(priority+" is a invalid priority type");
        }
    }

    public String getState() {
        return state.toString();
    }

    public void setState(String state) {
        switch (state) {
            case "to-do":
                this.state = State.TODO;
                break;
            case "in-progress":
                this.state = State.IN_PROGRESS;
                break;
            case "done":
                this.state = State.DONE;
                break;
            default:
                throw new IllegalArgumentException(state+" is a invalid task state type");
        }
    }

    public LocalDate getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDate expireAt) {
        this.expireAt = expireAt;
    }

}
