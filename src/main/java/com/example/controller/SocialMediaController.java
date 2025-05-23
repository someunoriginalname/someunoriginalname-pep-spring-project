package com.example.controller;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.RepeatableContainers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }
    // User story 1
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account account){
        try{
            account = accountService.persistAcconut(account);
            return ResponseEntity.status(200).body(account);
        }
        catch(InvalidAccountException e){
            return ResponseEntity.status(400).body(account);
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(409).body(account);
        }
    }
    // User story 2
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account){
        account = accountService.loginAccount(account);
        if(account == null){
            return ResponseEntity.status(401).body(account);
        }
        return ResponseEntity.status(200).body(account);
    }
    // User story 3
    @PostMapping("/messages")
    public ResponseEntity newMessage(@RequestBody Message message){
        try{
            message = messageService.addMessage(message);
            return ResponseEntity.status(200).body(message);
        }
        catch(InvalidMessageException e){
            return ResponseEntity.status(400).body(message);
        }
        
    }
    // User story 4
    @GetMapping("/messages")
    public ResponseEntity messages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    // User story 5
    @GetMapping("/messages/{messageId}")
    public ResponseEntity message(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.getMessage(messageId));
    }

    // User story 6
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessage(@PathVariable int messageId){
        int rows = messageService.removeMessage(messageId);
        if(rows == 1)
            return ResponseEntity.status(200).body(rows);
        else
            return ResponseEntity.status(200).body("");

    }
    
    // User story 7
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity patchMessage(@PathVariable int messageId, @RequestBody Map<String, String> request){
        String messageText = request.get("messageText");
        Message message = new Message();
        System.out.println(messageText);
        message.setMessageId(messageId);
        message.setMessageText(messageText);
        try{
            int rows = messageService.changeMessage(message);
            return ResponseEntity.status(200).body(rows);
        }
        catch(InvalidMessageException e){
            return ResponseEntity.status(400).body("");
        }
    }

    // User story 8
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity messagesByUser(@PathVariable int accountId){
        return ResponseEntity.status(200).body(messageService.messagesByUser(accountId));
    }
}
