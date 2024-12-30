package com.ict.camping.domain.chat.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ict.camping.domain.chat.vo.ChatRoomVO;

@Mapper
public interface ChatRoomMapper {
  void createChatRoom(ChatRoomVO chatRoom);
}
