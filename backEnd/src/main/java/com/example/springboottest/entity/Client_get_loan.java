package com.example.springboottest.entity;



import com.example.springboottest.TriggersId.Client_get_loanTriggersId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;


@Data
@Entity
@IdClass(Client_get_loanTriggersId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Client_get_loan {
    @Id
    private String client_id;

    private String bank_name;
    @Id
    private String loan_id;
}
