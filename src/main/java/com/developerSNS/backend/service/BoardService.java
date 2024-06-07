package com.developerSNS.backend.service;

import com.developerSNS.backend.domain.Board;
import com.developerSNS.backend.domain.User;
import com.developerSNS.backend.dto.request.BoardCreateRequest;
import com.developerSNS.backend.dto.request.BoardUpdateRequest;
import com.developerSNS.backend.dto.response.BoardDetailResponse;
import com.developerSNS.backend.dto.response.BoardListResponse;
import com.developerSNS.backend.dto.response.CommentResponse;
import com.developerSNS.backend.error.exception.board.BoardNotFoundException;
import com.developerSNS.backend.error.exception.user.UserNotFoundException;
import com.developerSNS.backend.repository.BoardRepository;
import com.developerSNS.backend.repository.CommentRepository;
import com.developerSNS.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    // 게시물 작성
    public void createBoard(BoardCreateRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        Board board = Board.of(user, request);
        boardRepository.save(board);
    }

    // 게시글 전체 조회
    public List<BoardListResponse> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream()
                .map(BoardListResponse::of)
                .collect(Collectors.toList());
    }

    // 게시물 상세 조회
    public BoardDetailResponse getBoardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        List<CommentResponse> commentResponses = commentRepository.findAllByBoardId(boardId).stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
        return BoardDetailResponse.of(board, commentResponses);
    }

    // 게시물 수정
    public void updateBoard(Long boardId, BoardUpdateRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        board.update(request);
    }

    // 게시글 삭제
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        boardRepository.delete(board);
    }

    // 사용자 게시물 조회
    public List<BoardListResponse> getBoardListById(Long userId) {
        List<Board> boardList = boardRepository.findAllByUserId(userId);
        return boardList.stream()
                .map(BoardListResponse::of)
                .collect(Collectors.toList());
    }
}