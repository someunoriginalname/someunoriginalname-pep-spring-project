package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }
    // User story 3
    /**
     * 
     * @param message
     * @return
     * @throws InvalidMessageException
     */
    public Message addMessage(Message message) throws InvalidMessageException{
        if(message.getMessageText() == "" || 
        message.getMessageText().length() > 255 ||
        (accountRepository.findAccountById(message.getPostedBy()).isEmpty()))
            throw new InvalidMessageException();
        return messageRepository.save(message);
    }
    // User story 4
    /**
     * 
     * @return
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    // User story 5
    /**
     * 
     * @param id
     * @return
     */
    public Message getMessage(int id){
        Optional <Message> message = messageRepository.findMessageById(id);
        if(message.isPresent())
            return message.get();
        return null;
    }
    // User story 6
    /**
     * 
     * @param id
     * @return
     */
    public int removeMessage(int id){
        return messageRepository.deleteMessageById(id);
    }
    // User story 7
    /**
     * 
     * @param message
     * @return
     * @throws InvalidMessageException
     */
    public int changeMessage(Message message) throws InvalidMessageException{
        if(message.getMessageText().isEmpty() || message.getMessageText().length() > 255)
            throw new InvalidMessageException();
        return messageRepository.updateMessageById(message.getMessageText(), message.getMessageId());
    }
    // User story 8
    /**
     * 
     * @param id
     * @return
     */
    public List<Message> messagesByUser(int id){
        return messageRepository.findMessagesByPostedBy(id);
    }
}
