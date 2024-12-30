package com.ict.camping.domain.chat.service;

import com.ict.camping.domain.chat.vo.ChatRoomVO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService {
    private final Map<String, ChatRoomVO> chatRooms = new HashMap<>();

    public List<ChatRoomVO> findAllRooms() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomVO findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoomVO createRoom(String name) {
        ChatRoomVO chatRoom = new ChatRoomVO(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
