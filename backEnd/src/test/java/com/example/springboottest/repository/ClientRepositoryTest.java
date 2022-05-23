package com.example.springboottest.repository;

import com.example.springboottest.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;
    @Test
    void findAll(){
        List<Client> clients =clientRepository.findAll();
        for(Client client:clients)
            System.out.println(client);

    }
    @Test
    void findPage(){
        Pageable pageable = PageRequest.of(0,5);
        Page<Client> pageClients = clientRepository.findAll(pageable);
        System.out.println(pageClients.getTotalPages());
        System.out.println(pageClients.getTotalElements());
        System.out.println(pageClients.getNumberOfElements());
        //int elements = pageClients.getTotalElements();
        List<Client> clients = pageClients.getContent();
        for(Client client:clients)
            System.out.println(client);
        /*Page<Client> testPage = new PageImpl<Client>(clients,new PageRequest(pageClients.getTotalPages(),pageClients.getTotalElements(),Sort.unsorted()),clients.size());

        System.out.println(testPage.getTotalPages());
        System.out.println(testPage.getTotalElements());*/
    }
}