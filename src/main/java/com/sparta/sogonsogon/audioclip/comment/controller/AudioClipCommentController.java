package com.sparta.sogonsogon.audioclip.comment.controller;

import com.sparta.sogonsogon.audioclip.comment.dto.CommentRequestDto;
import com.sparta.sogonsogon.audioclip.comment.dto.CommentResponseDto;
import com.sparta.sogonsogon.audioclip.comment.service.AudioClipCommentService;
import com.sparta.sogonsogon.dto.StatusResponseDto;
import com.sparta.sogonsogon.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audioclip/comment")
public class AudioClipCommentController {

    private final AudioClipCommentService audioClipCommentService;

    @PostMapping("/{audioclipId}")
    @Operation(summary = "오디오 클립 댓글 생성", description = "해당 오디오 클립에 있는 댓글을 생성 할수 있습니다. ")
    public StatusResponseDto<CommentResponseDto> createdComment(@PathVariable Long audioclipId, @RequestBody String content, @Parameter(hidden = true)@AuthenticationPrincipal UserDetailsImpl userDetails){
        return audioClipCommentService.createComment(audioclipId, content, userDetails);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "오디오 클립 댓글 수정", description = "해당 오디오 클립에 있는 댓글을 수정 할수 있습니다. ")
    public StatusResponseDto<CommentResponseDto> updatedComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @Parameter(hidden = true)@AuthenticationPrincipal UserDetailsImpl userDetails){
        return audioClipCommentService.updateComment(commentId, requestDto, userDetails);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "오디오 클립 댓글 삭제", description = "해당 오디오 클립에 있는 댓글을 삭제 할수 있습니다. ")
    public StatusResponseDto<String> deleteComment(@PathVariable Long commentId, @Parameter(hidden = true)@AuthenticationPrincipal UserDetailsImpl userDetails ){
        return audioClipCommentService.deleteComment(commentId, userDetails);
    }

    @GetMapping("/{audioclipId}")
    @Operation(summary = "오디오 클립 댓글 전체 조회", description = "오디오 클립에 있는 전체 댓글을 볼 수 있습니다.")
    public StatusResponseDto<List<CommentResponseDto>> getComments(@PathVariable Long audioclipId){
        return audioClipCommentService.getComments(audioclipId);

    }


}
