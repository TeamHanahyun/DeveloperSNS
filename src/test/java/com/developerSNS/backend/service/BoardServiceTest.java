package com.developerSNS.backend.service;

import com.developerSNS.backend.domain.Board;
import com.developerSNS.backend.domain.Comment;
import com.developerSNS.backend.domain.User;
import com.developerSNS.backend.dto.request.BoardCreateRequest;
import com.developerSNS.backend.dto.request.BoardUpdateRequest;
import com.developerSNS.backend.dto.response.BoardDetailResponse;
import com.developerSNS.backend.dto.response.BoardListResponse;
import com.developerSNS.backend.error.exception.board.BoardNotFoundException;
import com.developerSNS.backend.error.exception.user.UserNotFoundException;
import com.developerSNS.backend.repository.BoardRepository;
import com.developerSNS.backend.repository.CommentRepository;
import com.developerSNS.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    private User user;

    private Board board;

    private Comment comment;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@example.com", "password", "nickname", new Date(), null, false);

        board = Board.builder()
                .id(1L)
                .user(user)
                .title("title")
                .content("boardContent")
                .category("category")
                .createdAt(new Date())
                .updatedAt(null)
                .isDeleted(false)
                .build();

        comment = new Comment(1L, board, user, "commentContent", new Date(), null, false);
    }

    @DisplayName("게시물 생성 - 성공")
    @Test
    void createBoard() {
        BoardCreateRequest request = BoardCreateRequest.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .userId(user.getId())
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        boardService.createBoard(request);

        verify(userRepository, times(1)).findById(request.getUserId());
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @DisplayName("게시물 생성 - 유저를 찾을 수 없음")
    @Test
    void createBoard_userNotFound() {
        BoardCreateRequest request = BoardCreateRequest.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .userId(user.getId())
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(UserNotFoundException.class, () -> boardService.createBoard(request));

        verify(userRepository, times(1)).findById(request.getUserId());
        verify(boardRepository, never()).save(any(Board.class));
    }

    @DisplayName("게시글 전체 조회")
    @Test
    void getBoardList() {
        when(boardRepository.findAll()).thenReturn(List.of(board));

        List<BoardListResponse> response = boardService.getBoardList();

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getTitle()).isEqualTo("title");
        verify(boardRepository, times(1)).findAll();
    }

    @DisplayName("게시글 상세 조회 - 성공")
    @Test
    void getBoardDetail_success() {
        when(boardRepository.findById(board.getId())).thenReturn(Optional.ofNullable(board));
        when(commentRepository.findAllByBoardId(board.getId())).thenReturn(List.of(comment));

        BoardDetailResponse response = boardService.getBoardDetail(board.getId());

        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getContent()).isEqualTo("boardContent");
        verify(boardRepository, times(1)).findById(board.getId());
        verify(commentRepository, times(1)).findAllByBoardId(board.getId());
    }

    @DisplayName("게시글 상세 조회 - 게시물을 찾을 수 없음")
    @Test
    void getBoardDetail_boardNotFound() {
        when(boardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(BoardNotFoundException.class, () -> boardService.getBoardDetail(board.getId()));

        verify(boardRepository, times(1)).findById(board.getId());
        verify(commentRepository, never()).findAllByBoardId(board.getId());
    }

    @DisplayName("게시물 수정 - 성공")
    @Test
    void updateBoard_success() {
        BoardUpdateRequest request = BoardUpdateRequest.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .build();
        when(boardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(board));

        boardService.updateBoard(board.getId(), request);

        verify(boardRepository, times(1)).findById(board.getId());
    }

    @DisplayName("게시물 수정 - 게시물을 찾을 수 없음")
    @Test
    void updateBoard_boardNotFound() {
        BoardUpdateRequest request = BoardUpdateRequest.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .build();
        when(boardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(BoardNotFoundException.class, () -> boardService.updateBoard(board.getId(), request));

        verify(boardRepository, times(1)).findById(board.getId());
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void deleteBoard_success() {
        when(boardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(board));

        boardService.deleteBoard(board.getId());

        verify(boardRepository, times(1)).findById(board.getId());
    }

    @DisplayName("게시글 삭제 - 게시물을 찾을 수 없음")
    @Test
    void deleteBoard_boardNotFound() {
        when(boardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(BoardNotFoundException.class, () -> boardService.deleteBoard(board.getId()));

        verify(boardRepository, times(1)).findById(board.getId());
    }

    @DisplayName("사용자 게시물 조회")
    @Test
    void getBoardListById() {
        when(boardRepository.findAllByUserId(anyLong())).thenReturn(List.of(board));

        List<BoardListResponse> response = boardService.getBoardListById(user.getId());

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getTitle()).isEqualTo("title");
        verify(boardRepository, times(1)).findAllByUserId(user.getId());
    }

}