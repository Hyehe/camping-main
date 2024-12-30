package com.ict.camping.domain.chat.controller;

import com.ict.camping.domain.chat.vo.ChatMessage;
import com.ict.camping.domain.chat.vo.ChatRoomVO;
import com.ict.camping.domain.chat.service.ChatService;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/rooms")
    public List<ChatRoomVO> getAllRooms() {
        return chatService.findAllRooms();
    }

    @PostMapping("/rooms")
    public ChatRoomVO createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    // @MessageMapping("/message")
    // @SendTo("/topic/messages")
    // public ChatMessage sendMessage(ChatMessage message) {
    //     return message;
    // }
}
