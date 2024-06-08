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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 생성
    public void createComment(CommentCreateRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow(BoardNotFoundException::new);
        Comment comment = Comment.builder()
                .board(board)
                .user(user)
                .content(request.getContent())
                .build();
        commentRepository.save(comment);
    }

    // 댓글 수정
    public Comment updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.update(request.getContent());
        return comment;
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.softDelete();
    }
}
