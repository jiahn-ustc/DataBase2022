package com.example.springboottest.TriggersId;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Client_get_loanTriggersId implements Serializable {
    String client_id;
    String loan_id;
}
