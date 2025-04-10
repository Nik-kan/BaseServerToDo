package com.example.BaseServerToDo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Entity
@Table(name= "tasks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "task_date")
    private Calendar date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_data", referencedColumnName = "id") // булщит, разобраться !!!!!
    private Long userId;

    @Column(name="task_status")
    private boolean taskStatus;

}
