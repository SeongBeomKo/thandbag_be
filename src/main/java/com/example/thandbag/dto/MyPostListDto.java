package com.example.thandbag.dto;

import com.example.thandbag.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPostListDto {

    private Long userId;
    private String nickname;
    private String lvIcon;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String imgUrl;
    private Boolean closed;
    private Category category;
}
