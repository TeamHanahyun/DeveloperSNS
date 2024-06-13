package com.developerSNS.backend.service;

import com.developerSNS.backend.domain.Board;
import com.developerSNS.backend.domain.Comment;
import com.developerSNS.backend.domain.User;
import com.developerSNS.backend.dto.request.CommentCreateRequest;
import com.developerSNS.backend.dto.request.CommentUpdateRequest;
import com.developerSNS.backend.error.exception.board.BoardNotFoundException;
import com.developerSNS.backend.error.exception.comment.CommentNotFoundException;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardRepository boardRepository;

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

    @DisplayName("댓글 생성 - 성공")
    @Test
    void createComment_success() {
        CommentCreateRequest request = CommentCreateRequest.builder()
                .boardId(board.getId())
                .userId(user.getId())
                .content(comment.getContent())
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(boardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(board));

        commentService.createComment(request);

        verify(userRepository, times(1)).findById(request.getUserId());
        verify(boardRepository, times(1)).findById(request.getBoardId());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @DisplayName("댓글 생성 - 유저를 찾을 수 없음")
    @Test
    void createComment_userNotFound() {
        CommentCreateRequest request = CommentCreateRequest.builder()
                .boardId(board.getId())
                .userId(user.getId())
                .content(comment.getContent())
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(UserNotFoundException.class, () -> commentService.createComment(request));

        verify(userRepository, times(1)).findById(request.getUserId());
        verify(boardRepository, never()).findById(request.getBoardId());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @DisplayName("댓글 생성 - 게시믈울 찾을 수 없음")
    @Test
    void createComment_boardNotFound() {
        CommentCreateRequest request = CommentCreateRequest.builder()
                .boardId(board.getId())
                .userId(user.getId())
                .content(comment.getContent())
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(boardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(BoardNotFoundException.class, () -> commentService.createComment(request));

        verify(userRepository, times(1)).findById(request.getUserId());
        verify(boardRepository, times(1)).findById(request.getBoardId());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @DisplayName("댓글 수정 - 성공")
    @Test
    void updateComment_success() {
        CommentUpdateRequest request = CommentUpdateRequest.builder()
                .content("newContent")
                .build();
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        Comment response = commentService.updateComment(comment.getId(), request);

        assertThat(response.getContent()).isEqualTo("newContent");
        verify(commentRepository, times(1)).findById(comment.getId());
    }

    @DisplayName("댓글 수정 - 댓글을 찾을 수 없음")
    @Test
    void updateComment_commentNotFound() {
        CommentUpdateRequest request = CommentUpdateRequest.builder()
                .content("newContent")
                .build();
        when(commentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(CommentNotFoundException.class, () -> commentService.updateComment(comment.getId(), request));

        verify(commentRepository, times(1)).findById(comment.getId());
    }

    @DisplayName("댓글 삭제 - 성공")
    @Test
    void deleteComment_success() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(comment));

        commentService.deleteComment(comment.getId());

        verify(commentRepository, times(1)).findById(comment.getId());
    }

    @DisplayName("댓글 삭제 - 댓글을 찾을 수 없음")
    @Test
    void deleteComment_commentNotFound() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(CommentNotFoundException.class, () -> commentService.deleteComment(comment.getId()));

        verify(commentRepository, times(1)).findById(comment.getId());
    }
}