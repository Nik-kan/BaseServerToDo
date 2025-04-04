package com.example.BaseServerToDo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskDto {

    private Long id;

    private String title;

    private Calendar date;

    private Long userId;

    private boolean taskStatus;

}
