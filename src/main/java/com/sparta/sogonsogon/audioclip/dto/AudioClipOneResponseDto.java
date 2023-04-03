package com.sparta.sogonsogon.audioclip.dto;

import com.sparta.sogonsogon.audioclip.entity.AudioClip;
import com.sparta.sogonsogon.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AudioClipOneResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String audioclipImageUrl;
    private String audioclipUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String membernickname;
    private String membername; //오디오 올린 주인 고유 아이디

    private int isLikeCount;
    private boolean isLikeCheck;

    @Builder
    public AudioClipOneResponseDto(AudioClip audioClip, boolean isLikeCheck){
        this.id = audioClip.getId();
        this.title = audioClip.getTitle();
        this.contents = audioClip.getContents();
        this.audioclipImageUrl = audioClip.getAudioclipImageUrl();
        this.audioclipUrl = audioClip.getAudioclipUrl();
        this.membernickname = audioClip.getMember().getNickname();
        this.membername = audioClip.getMember().getMembername();
        this.createdAt = audioClip.getCreatedAt();
        this.modifiedAt = audioClip.getModifiedAt();
        this.isLikeCount = audioClip.getAudioClipLikes().size();
        this.isLikeCheck = isLikeCheck;
    }

}
