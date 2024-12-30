package com.ict.camping.domain.chat.vo;

import java.util.UUID;

public class ChatRoomVO {
    private String roomId;
    private String name;

    public ChatRoomVO(String name) {
        this.roomId = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getRoomId() {
        return roomId;
    }
    public String getName() {
        return name;
    }

    public void setRoomId(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRoomId'");
    }

    public void setName(String string) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }
}
