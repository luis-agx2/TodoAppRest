package com.lag.todoapp.rest.todoapprest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @ManyToOne(fetch =  FetchType.LAZY, cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
