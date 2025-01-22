package com.adobe.orderapp.dto;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

public class Message {
    @JsonView(MessageView.Summary.class)
    private Long id;

    @JsonView(MessageView.Summary.class)
    @JsonFormat(pattern = "dd-MMM-yy")
    private LocalDate created;

    @JsonView(MessageView.Summary.class)
    private String title;

    @JsonView(MessageView.Summary.class)
    private Person author;

    @JsonView(MessageView.SummaryWithRecipients.class)
    private List<Person> recipients;

    private String body;


    public Message() {
        this.created = LocalDate.now();
    }

    public Message(Long id, String title, String body, Person author, Person... recipients) {
        this();
        this.id = id;
        this.title = title;
        this.body = body;
        this.author = author;
        this.recipients = Arrays.asList(recipients);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public List<Person> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Person> recipients) {
        this.recipients = recipients;
    }
}