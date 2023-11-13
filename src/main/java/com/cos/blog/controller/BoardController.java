package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 컨트롤러로 세션을 어떻게 찾는 지?
    // @AuthenticationPrincipal PrincipalDetail principal
    @GetMapping({"","/"})
    public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boards", boardService.글목록(pageable)); //request라고 보면 됨.
        System.out.println("로그인 됨");
        return "index"; // viewResolver 작동!! -> prefix, suffix를 넣어줌
    }

    @GetMapping("/board/{id}")
    public String findById(Model model, @PathVariable int id) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String board() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("board",boardService.글상세보기(id));
        return "board/updateForm";
    }
}
