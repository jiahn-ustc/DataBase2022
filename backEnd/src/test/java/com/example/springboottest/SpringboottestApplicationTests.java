package com.example.springboottest;

import com.example.springboottest.entity.Client;
import com.example.springboottest.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.print.Book;
import java.util.Optional;

@SpringBootTest
class SpringboottestApplicationTests {
    @Autowired
    private ClientRepository clientRepository;
    @Test
    void contextLoads() {
        System.out.println(clientRepository.findAll());
    }
    @Test
    void save(){
        Client client = new Client();
        client.setClient_id("1145141");
        client.setName("test");
        client.setTel("110");
        client.setAddress("ustc");
        Optional<Client> O = clientRepository.findById("1145141");
        if(O.isPresent())
        {
            System.out.println("客户中已有此身份证号,不允许创建此客户");
        }
        else {
            clientRepository.save(client);
            System.out.println(client);
        }


    }
    @Test
    void findById(){
        Optional<Client> O = clientRepository.findById("11451");

        if(O.isPresent())
        {
            System.out.println("查询成功");
        }
        else {
            System.out.println("查询失败");
        }

    }

    @Test
    void update(){
        Client client = new Client();
        client.setClient_id("2");
        client.setTel("999999");
        client.setAddress("996");
        Client client1 = clientRepository.save(client);
        System.out.println(client1);
    }
    @Test
    void delete(){
        clientRepository.deleteById("4");
    }

}
