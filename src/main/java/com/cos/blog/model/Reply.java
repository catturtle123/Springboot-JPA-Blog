package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board; // 알아서 클래스를 찾아서 연결이 됨.

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user; // int 값으로 만들어짐 -> id를 참조하기 때문

    @CreationTimestamp
    private Timestamp createDate;
}
