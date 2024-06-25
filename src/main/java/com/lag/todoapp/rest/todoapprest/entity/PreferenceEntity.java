package com.lag.todoapp.rest.todoapprest.entity;

import com.lag.todoapp.rest.todoapprest.enums.DashboardEnum;
import com.lag.todoapp.rest.todoapprest.enums.ThemeEnum;
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
@Table(name = "preferences")
public class PreferenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dashboard_view")
    @Enumerated(EnumType.STRING)
    private DashboardEnum dashBoardView;

    @Column(name = "theme")
    @Enumerated(EnumType.STRING)
    private ThemeEnum theme;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
