package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨. -> 용량이 큼

    private int count; // 조회수
    
    // 기본이 EAGER --> 한 개만 가져오면 되기 때문
    @ManyToOne(fetch = FetchType.EAGER) // Many = Board, User = One -> 한명의 유저는 여러 개의 게시판 작성 가능. 한개의 게시판은 한명에 의해 적혀진다.
    @JoinColumn(name = "userId") // 필드
    private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.
    
    // 기본이 Lazy --> 여러 개를 가져와야하기 때문
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // mappedBy 연관관계의 주인이 아니다. (난 FK가 아니에요) DB에 칼럼을 만들지 마세요 (필드 이름을 적으면 됨.)
//    @JoinColumn(name = "replyId")
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> replys; // reply 정보가 한개가 아니기에

    @CreationTimestamp // 데이터가 인서트 혹은 업데이트시 자동으로 현재 시간이 들어감
    private Timestamp createDate;
}
