package org.fbs.r34.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_logs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String langCode;

    private String query;

    private int currentLimit;

    private LocalDateTime createdAt;

}
