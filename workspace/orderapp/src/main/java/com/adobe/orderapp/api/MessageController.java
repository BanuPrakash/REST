package com.adobe.orderapp.api;


import com.adobe.orderapp.dto.Message;
import com.adobe.orderapp.dto.MessageView;
import com.adobe.orderapp.service.MessageService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @JsonView(MessageView.Summary.class)
    @GetMapping()
    public List<Message> getAllMessages() {
        return messageService.getAll();
    }

    @JsonView(MessageView.SummaryWithRecipients.class)
    @GetMapping("/with-recipients")
    public List<Message> getAllMessagesWithRecipients() {
        return messageService.getAll();
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable Long id) {
        return this.messageService.get(id);
    }

    @PostMapping()
    public Message create(@RequestBody Message message) {
        return this.messageService.create(message);
    }

}
