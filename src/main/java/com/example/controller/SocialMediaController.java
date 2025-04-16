package com.example.controller;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
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
@ComponentScan(basePackages = "com.example.entity")
@Component
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    MessageService messageService;

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
        }
        catch(InvalidAccountException e){
            return ResponseEntity.status(400).body(account);
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(409).body(account);
        }
        return ResponseEntity.status(200).body(account);
    }
    // User story 2
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account){
        account = accountService.loginAccount(account);
        if(account == null){
            return ResponseEntity.status(400).body(account);
        }
        return ResponseEntity.status(200).body(account);
    }
    // User story 3
    @PostMapping("/messages")
    public ResponseEntity newMessage(@RequestBody Message message){
        try{
            message = messageService.addMessage(message);
        }
        catch(InvalidMessageException e){
            return ResponseEntity.status(400).body(message);
        }
        return ResponseEntity.status(200).body(message);
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
    public ResponseEntity patchMessage(@PathVariable int messageId, @RequestBody Message message){
        try{
            int rows = messageService.changeMessage(message);
        }
        catch(InvalidMessageException e){
            return ResponseEntity.status(400).body("");
        }
        return ResponseEntity.status(200).body(1);
    }

    // User story 8
    @GetMapping("accounts/{accountId}/message")
    public ResponseEntity messagesByUser(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.messagesByUser(messageId));
    }
}
