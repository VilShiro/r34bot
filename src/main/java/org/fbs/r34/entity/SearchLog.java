package org.fbs.r34.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "search_logs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String langCode;

    @Column(nullable = false)
    private String query;

    @Column(nullable = false)
    private int currentLimit;

}
