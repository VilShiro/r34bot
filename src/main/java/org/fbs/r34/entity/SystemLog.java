package org.fbs.r34.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "system_logs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private String signature;

    @Column(length = 8192)
    private String message;

    private Criticality criticality = Criticality.INFO;
}
