package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
public class BoardApiController {

        @Autowired
        private BoardService boardService;

        @Autowired
        private ReplyRepository repository;

        @PostMapping("/api/board")
        public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principalDetail) {

            boardService.글쓰기(board, principalDetail.getUser());
            return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        }

        @DeleteMapping("/api/board/{id}")
        public void delete(@PathVariable int id) {
            boardService.글삭제하기(id);
        }

        @PutMapping("/api/board/{id}")
        public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
            System.out.println("A");
            boardService.글수정하기(id, board);
            return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        }

        // 데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
        @PostMapping("/api/board/{boardId}/reply")
        public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {

            boardService.댓글쓰기(replySaveRequestDto);
            return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
        }

        @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
            repository.deleteById(replyId);
            return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
        }
}
