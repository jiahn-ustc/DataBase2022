package com.example.springboottest.TriggersId;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Manage_departmentTriggersId implements Serializable {
    String bank_name;
    String department_id;
}
