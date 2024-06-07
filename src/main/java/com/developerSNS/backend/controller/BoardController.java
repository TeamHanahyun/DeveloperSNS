package com.developerSNS.backend.controller;

import com.developerSNS.backend.dto.request.BoardCreateRequest;
import com.developerSNS.backend.dto.request.BoardUpdateRequest;
import com.developerSNS.backend.dto.response.BoardDetailResponse;
import com.developerSNS.backend.dto.response.BoardListResponse;
import com.developerSNS.backend.result.ResultResponse;
import com.developerSNS.backend.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.developerSNS.backend.result.ResultCode.*;

@Tag(name = "Board", description = "게시물 관련 api입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시물 작성", description = "게시물을 작성합니다.")
    @PostMapping
    public ResponseEntity<ResultResponse> createBoard(@RequestBody @Valid BoardCreateRequest request) {
        boardService.createBoard(request);
        return ResponseEntity.ok(ResultResponse.of(BOARD_CREATE_SUCCESS));
    }

    @Operation(summary = "게시물 전체 조회", description = "전체 게시물을 조회합니다.")
    @GetMapping
    public ResponseEntity<ResultResponse> findBoardList() {
        List<BoardListResponse> boardListResponses = boardService.getBoardList();
        return ResponseEntity.ok(ResultResponse.of(BOARD_LIST_GET_SUCCESS, boardListResponses));
    }

    @Operation(summary = "게시물 상세 조회", description = "특정한 게시물을 조회합니다.")
    @GetMapping("/{boardId}")
    public ResponseEntity<ResultResponse> findBoardListByTitle(@PathVariable Long boardId) {
        BoardDetailResponse boardDetailResponse = boardService.getBoardDetail(boardId);
        return ResponseEntity.ok(ResultResponse.of(BOARD_DETAIL_GET_SUCCESS, boardDetailResponse));
    }

    @Operation(summary = "게시물 수정", description = "특정한 게시물 내용을 수정합니다.")
    @PutMapping("/{boardId}")
    public ResponseEntity<ResultResponse> updateBoard(@PathVariable Long boardId, @RequestBody @Valid BoardUpdateRequest request) {
        boardService.updateBoard(boardId, request);
        return ResponseEntity.ok(ResultResponse.of(BOARD_UPDATE_SUCCESS));
    }

    @Operation(summary = "게시물 삭제", description = "특정한 게시물을 삭제합니다.")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResultResponse> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok(ResultResponse.of(BOARD_DELETE_SUCCESS));
    }

    @Operation(summary = "게시물 상세 조회", description = "특정한 사용자의 게시물을 조회합니다.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResultResponse> getBoardListById(@PathVariable Long userId) {
        List<BoardListResponse> boardListResponses = boardService.getBoardListById(userId);
        return ResponseEntity.ok(ResultResponse.of(BOARD_LIST_GET_SUCCESS, boardListResponses));
    }
}