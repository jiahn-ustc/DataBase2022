package com.example.springboottest.TriggersId;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Employee_clientTriggersId implements Serializable {
    String client_id;
    String employee_id;
}
