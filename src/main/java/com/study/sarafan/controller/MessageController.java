package com.study.sarafan.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.study.sarafan.domain.Message;
import com.study.sarafan.domain.Views;
import com.study.sarafan.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("message")
public class MessageController{
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @JsonView(Views.IdName.class)
    @GetMapping
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreatingDate(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDb,
            @RequestBody Message message
    ) {
        BeanUtils.copyProperties(message, messageFromDb, "id"); //will copy all fields except id

        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }
}
