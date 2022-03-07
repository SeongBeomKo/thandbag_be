package com.example.thandbag.controller;

import com.example.thandbag.dto.chat.ChatMessageDto;
import com.example.thandbag.repository.RedisRepository;
import com.example.thandbag.security.jwt.JwtDecoder;
import com.example.thandbag.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatController {

    private final JwtDecoder jwtDecoder;
    private final RedisRepository chatRedisRepository;
    private final ChatService chatService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message,
                        @Header("Authorization") String token,
                        @Header("refreshToken") String refreshToken) {
        /* 토큰 정보 추출 */
        String tokenInfo = token.substring(7);
        String tokenInfo2 = refreshToken.substring(7);
        String username = jwtDecoder.decodeUsername(tokenInfo);
        String nickname = chatService.getNickname(username);

        /* 로그인 회원 정보로 대화명 설정 */
        message.setSender(nickname);
        message.setUserCount(chatRedisRepository
                .getUserCount(message.getRoomId()));

        /* Websocket에 발행된 메시지를 redis로 발행(publish) */
        chatService.sendChatMessage(message);
    }

}