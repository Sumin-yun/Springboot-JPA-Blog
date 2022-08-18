package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

//스프링이 컴포넌트 스캔을 통해 bean에 등록(IOC)
@Service
public class BoardService {

  @Autowired
  private BoardRepository boardRepository;


  @Transactional
  public void 글쓰기(Board board, User user) {
    board.setCount(0);
    board.setUser(user);
    boardRepository.save(board);
  }

  @Transactional(readOnly = true)
  public Page<Board> 글목록(Pageable pageable){
    return boardRepository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Board 글상세보기(int id) {
    return boardRepository.findById(id)
        .orElseThrow(() -> {
          return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
        });
  }

  @Transactional
  public void 글삭제하기(int id) {
    boardRepository.deleteById(id);
  }

  @Transactional
  public void 글수정하기(int id, Board requestBoard) {
    Board board = boardRepository.findById(id)
        .orElseThrow(() -> {
          return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
        });           //영속화 완료
    board.setTitle(requestBoard.getTitle());
    board.setContent(requestBoard.getContent());
    //서비스 종료시 트렌젝션 종료됨. 이때, 더티체킹이 일어남 -> 자동업데이트 (commit)
  }

}

//  @Transactional(readOnly = true)
//  public User 로그인(User user) {
//    return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//  }