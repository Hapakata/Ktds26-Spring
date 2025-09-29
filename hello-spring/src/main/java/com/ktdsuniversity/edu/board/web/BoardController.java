package com.ktdsuniversity.edu.board.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.board.service.impl.BoardServiceImpl;
import com.ktdsuniversity.edu.board.vo.BoardListVO;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;

import jakarta.validation.Valid;

@Controller
public class BoardController {
	
	@Autowired
	private BoardServiceImpl boardService;
	
	@GetMapping("/view/{id}")
	public String viewBoardDetailPage(@PathVariable("id") String id, Model model) {
		System.out.println(id+"id=======");
		BoardVO viewDetail = this.boardService.selectBoardOne(id); 
		System.out.println(id);
		model.addAttribute("detail",viewDetail);
		return "board/view";
	}
	
	@GetMapping("/list")
	public String boardListPage(Model model) {
		
		BoardListVO boardList = this.boardService.selectBoardList();
		int listCount = this.boardService.selectBoardListCount();
		
		boardList.setListCount(listCount);
		System.out.println("======" + listCount);
		model.addAttribute("list",boardList);
		
		return "board/list";
	}
	
	@GetMapping("/write")
	public String writePageView(String id, Model model) {
		
		BoardVO viewDetail = this.boardService.selectBoardOne(id); 
		
		model.addAttribute("detail",viewDetail);
		return "board/write";
	}
	
	// 포스트는 알아서 쏙쏙 들어가네 편하다!
	// 들어갔다 반환까지! 키야
	@PostMapping("/write")
	public String writePage(@Valid RequestCreateBoardVO requestCreateBoardVO ,
							BindingResult bindingResult,
							Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("writeData",requestCreateBoardVO);
			System.out.println("유효값 아님!");
			return "board/write";
		}
		
		boolean isSuccess = this.boardService.writeBoard(requestCreateBoardVO);
		return "redirect:/view/" + requestCreateBoardVO.getId();
	}
	
	@GetMapping("/modify/{id}")
	public String modifyPageView(@PathVariable("id") String id, Model model) {
		BoardVO viewDetail = this.boardService.selectBoardOne(id); 
		
		model.addAttribute("detail",viewDetail);
		return "board/modify";
	}
	
	@PostMapping("/modify/{id}")
	public String wirtePage(@PathVariable("id") String id, @Valid RequestCreateBoardVO requestCreateBoardVO
														 , BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("detail",requestCreateBoardVO);
			return "redirect:/modify/" + requestCreateBoardVO.getId();
		}
		
		requestCreateBoardVO.setId(id);
		boolean isSuccess = this.boardService.modifyBoard(requestCreateBoardVO);
		return "redirect:/view/" + requestCreateBoardVO.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String boardDeleteListPage(@PathVariable("id") String id) {
		
		boolean isSuccess = this.boardService.boardDelete(id);
		return "redirect:/list";
	}

}




