package com.developerSNS.backend.controller;

import com.developerSNS.backend.domain.Comment;
import com.developerSNS.backend.dto.request.CommentCreateRequest;
import com.developerSNS.backend.dto.request.CommentUpdateRequest;
import com.developerSNS.backend.result.ResultResponse;
import com.developerSNS.backend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.developerSNS.backend.result.ResultCode.*;

@Tag(name = "Comment", description = "댓글 관련 API입니다. ")
@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "새 댓글을 등록합니다.")
    @PostMapping
    public ResponseEntity<ResultResponse> createComment(@RequestBody @Valid CommentCreateRequest request) {
        commentService.createComment(request);
        return ResponseEntity.ok(ResultResponse.of(COMMENT_CREATE_SUCCESS));
    }

    @Operation(summary = "댓글 수정", description = "기존 댓글 내용을 수정합니다.")
    @PutMapping("/{commentId}")
    public ResponseEntity<ResultResponse> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentUpdateRequest request) {
        Comment comment = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(ResultResponse.of(COMMENT_UPDATE_SUCCESS, comment));
    }

    @Operation(summary = "댓글 삭제", description = "기존 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResultResponse> updateComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(ResultResponse.of(COMMENT_DELETE_SUCCESS));
    }
}
