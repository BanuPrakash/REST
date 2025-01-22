package com.adobe.orderapp.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.adobe.orderapp.dto.Message;
import com.adobe.orderapp.dto.Person;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final List<Message> messages = new ArrayList<>();

    public MessageService() {
        Person b = new Person(1L, "Rita", "Rao", "rita@adobe.com", "1 Good street", "69003", "Lyon", "France");
        Person s = new Person(2L, "Rakesh", "Sharma", "rakesh@adobe.com", "2 Obama street", "1000", "Brussel", "Belgium");
        Person a = new Person(3L, "Anne", "Hathaway", "anne@adobe.com", "3 Warren street", "10011", "New York", "USA");

        Message info = new Message(1L, "Info", "This is an information message", b, s, a);
        Message warning = new Message(2L, "Warning", "This is a warning message", s, a);
        Message alert = new Message(3L, "Alert", "This is an alert message", s, b);

        messages.add(info);
        messages.add(warning);
        messages.add(alert);
    }

    public List<Message> getAll() {
        return messages;
    }

    public Message get(Long id) {
        return this.messages.stream().filter((m) -> m.getId() == id).findFirst().get();
    }

    public Message create(Message message) {
        this.messages.add(message);
        return message;
    }
}