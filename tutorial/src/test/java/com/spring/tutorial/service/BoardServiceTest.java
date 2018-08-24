package com.spring.tutorial.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.tutorial.domain.Board;
import com.spring.tutorial.dto.BoardDTO;
import com.spring.tutorial.dto.BoardListDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardServiceTest {

	@Autowired private BoardService boardService;
	
	@Test
	public void A_insertTest() throws Exception {
		
		for (int i = 1; i < 31; i++) {
			BoardDTO model = new BoardDTO();
			model.setTitle("제목 " + i);
			model.setContent("This is content " + (31 - i));
			model.setRegUserId("admin");
			
			BoardDTO result = boardService.insert(model);
			
			assertEquals("제목 " + i, result.getTitle());
			assertEquals("This is content " + (31 - i), result.getContent());
			assertNotNull(result.getRegDatetime());
		}
		
		for (int i = 1; i < 31; i++) {
			BoardDTO model = new BoardDTO();
			model.setTitle("This is title " + i);
			model.setContent("내용 " + (31 - i));
			model.setRegUserId("admin");
			
			BoardDTO result = boardService.insert(model);
			
			assertEquals("This is title " + i, result.getTitle());
			assertEquals("내용 " + (31 - i), result.getContent());
			assertNotNull(result.getRegDatetime());
		}
		
	}
	
	@Test
	public void B_listTest() throws Exception {
		
		BoardListDTO model = new BoardListDTO();
		model.setPage(1);
		model.setPageSize(10);
		model.setTitle("제목");
		
		BoardListDTO result = boardService.list(model);
		
		assertEquals(30, result.getTotalCount());
		assertEquals(10, result.getList().size());
		assertEquals("제목 30", result.getList().get(0).getTitle());
		
		
		model.setContent("내용");
		
		BoardListDTO result2 = boardService.list(model);
		
		assertEquals(0, result2.getTotalCount());
		assertEquals(0, result2.getList().size());
		
		
		model.setTitle("This is title");
		model.setContent("내용 30");
		
		BoardListDTO result3 = boardService.list(model);
		
		assertEquals(1, result3.getTotalCount());
		assertEquals(1, result3.getList().size());
		assertEquals("This is title 1", result3.getList().get(0).getTitle());
		assertEquals("내용 30", result3.getList().get(0).getContent());
		
	}
	
	@Test
	public void C_viewTest() throws Exception {
		
		BoardListDTO model = new BoardListDTO();
		model.setTitle("제목 30");
		
		BoardListDTO list = boardService.list(model);
		
		long id = list.getList().get(0).getId();
		
		BoardDTO view = boardService.view(id);
		
		
		assertEquals(id, view.getId());
		assertEquals("제목 30", view.getTitle());
		assertEquals("This is content 1", view.getContent());
		
	}
	
	@Test
	public void D_updateTest() throws Exception {
		
		BoardListDTO search = new BoardListDTO();
		search.setTitle("제목 30");
		
		BoardListDTO list = boardService.list(search);
		Board prev = list.getList().get(0);
		
		long id = prev.getId();
		
		BoardDTO model = new BoardDTO();
		model.setTitle("제목 30 수정");
		model.setContent("내용 수정 1");
		model.setEditUserId("수정자");
		
		BoardDTO result = boardService.update(model, id);
		
		assertEquals("제목 30 수정", result.getTitle());
		assertEquals("내용 수정 1", result.getContent());
		assertEquals(prev.getId(), result.getId());
		assertNotEquals(prev.getTitle(), result.getTitle());
		assertNotEquals(prev.getContent(), result.getContent());
		
	}
	
	@Test
	public void E_deleteTest() throws Exception {
		
		BoardListDTO search = new BoardListDTO();
		search.setPageSize(100);
		search.setTitle("제목");
		
		BoardListDTO list = boardService.list(search);
		
		for (Board domain : list.getList()) {
			boardService.delete(domain.getId());
		}
		
		search.setTitle("This is title");
		
		list = boardService.list(search);
		
		for (Board domain : list.getList()) {
			boardService.delete(domain.getId());
		}
		
		search.setTitle(null);
		list = boardService.list(search);
		
		assertTrue(list.getList().size() <= 0);
		assertTrue(list.getTotalCount() == 0);
		
	}
	
}
