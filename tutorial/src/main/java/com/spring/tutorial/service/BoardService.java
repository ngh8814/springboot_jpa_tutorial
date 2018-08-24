package com.spring.tutorial.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.spring.tutorial.domain.Board;
import com.spring.tutorial.domain.BoardRepository;
import com.spring.tutorial.dto.BoardDTO;
import com.spring.tutorial.dto.BoardListDTO;

import javassist.NotFoundException;

@Service
public class BoardService {

	@Autowired private BoardRepository boardRepository;
	
	public BoardListDTO list(BoardListDTO model) throws Exception {
		Page<Board> page = boardRepository.findAll(model.toSpecification(), model.toPage());
		model.setList(page.getContent());
		model.setTotalCount(page.getTotalElements());
		
		return model;
	}
	
	public BoardDTO view(Long id) throws Exception {
		Optional<Board> opt = boardRepository.findById(id);
		
		if (opt.isPresent()) {
			return new BoardDTO(opt.get());
		} else {
			throw new NotFoundException("리소스를 찾을 수 없습니다.");
		}
	}
	
	public BoardDTO insert(BoardDTO model) throws Exception {
		Board board = boardRepository.save(model.toEntity());
		return new BoardDTO(board);
	}
	
	public BoardDTO update(BoardDTO model, long id) throws Exception {
		BoardDTO view = this.view(id);
		
		view.setTitle(model.getTitle());
		view.setContent(model.getContent());
		view.setEditUserId(model.getEditUserId());
		Board board = boardRepository.save(view.toEntity());
		
		return new BoardDTO(board);
	}
	
	public void delete(long id) throws Exception {
		this.view(id);
		boardRepository.deleteById(id);
	}
}
