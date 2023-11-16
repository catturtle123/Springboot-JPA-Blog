package com.cos.blog.repository;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value = "insert into reply(userId, boardId, content) VALUES (?1, ?2, ?3)", nativeQuery = true)
    int mSave(int userId, int boardId, String content); // 업데이트된 행의 개수를 리턴해줌.
}
