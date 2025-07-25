package com.vianavitor.enterprisechat.model;

import com.vianavitor.enterprisechat.util.model.Manageable;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "group_chat")
public class GroupChat implements Manageable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_active")
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt() {
        Instant now = Instant.now();
        this.createdAt = ZonedDateTime.ofInstant(now, ZoneId.of("America/Sao_Paulo")).toLocalDate();
    }

    @Override
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt() {
        Instant now = Instant.now();
        this.updatedAt = ZonedDateTime.ofInstant(now, ZoneId.of("America/Sao_Paulo")).toLocalDate();
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
