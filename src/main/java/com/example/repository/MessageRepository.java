package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

    // User story number 5
    /**
     * 
     * @param messageID
     * @return Message with id of our param
     */
    @Query("FROM Message WHERE messageId = :messageId")
    Optional<Message> findMessageById(@Param("messageId") int messageID);

    // User story number 6
    /**
     * 
     * @param messageID
     * @return number of rows
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Message WHERE messageId = :messageId")
    int deleteMessageById(@Param("messageId") int messageID);
    // User story number 7
    /**
     * 
     * @param messageText
     * @param messageId
     * @return Number of rows updated (1 or 0)
     */
    @Modifying
    @Transactional
    @Query("UPDATE Message SET messageText = :messageText WHERE messageId = :messageId")
    int updateMessageById(@Param("messageText") String messageText, @Param("messageId") int messageId);

    // User story number 8
    /**
     * 
     * @param postedBy
     * @return List of messages with by user with the id postedBy
     */
    @Query("FROM Message WHERE postedBy = :postedBy")
    List<Message> findMessagesByPostedBy(@Param("postedBy") int postedBy);
}
