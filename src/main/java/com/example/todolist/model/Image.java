package com.example.todolist.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String type;


    @Lob
    @Column(length = 50000)
    private byte[] imageData;

    @OneToOne(cascade = CascadeType.MERGE) // Adjust the cascade type as needed
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;

}
