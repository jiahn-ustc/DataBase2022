package com.example.springboottest.entity;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Client {
    @Id
    private String client_id;
    private String name;
    private String tel;
    private String address;

    private String contact_name;

    private String contact_tel;
    private String contact_email;
    private String contact;
}
