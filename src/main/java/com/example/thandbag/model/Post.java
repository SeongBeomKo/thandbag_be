package com.example.thandbag.model;

import com.example.thandbag.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Boolean closed;

    @Column
    private Boolean share;

    @ManyToOne
    private User user;

    @Column
    private Category category;

    @Column
    private int totalHitCount;


    //api 작성시 추가된 부분
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<PostImg> imgList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList;

    public void closePost() {
        this.closed = true;
    }
    public void updateTotalHit(int totalHitCount) { this.totalHitCount = totalHitCount;}

}
