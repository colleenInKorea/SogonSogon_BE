package com.sparta.sogonsogon.audioAlbum.service;

import com.sparta.sogonsogon.audioAlbum.dto.AudioAlbumRequestDto;
import com.sparta.sogonsogon.audioAlbum.dto.AudioAlbumResponseDto;
import com.sparta.sogonsogon.audioAlbum.entity.AudioAlbum;
import com.sparta.sogonsogon.audioAlbum.repository.AudioAlbumRepository;
import com.sparta.sogonsogon.enums.ErrorMessage;
import com.sparta.sogonsogon.member.entity.Member;
import com.sparta.sogonsogon.member.repository.MemberRepository;
import com.sparta.sogonsogon.security.UserDetailsImpl;
import com.sparta.sogonsogon.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AudioAlbumService {

    private final MemberRepository memberRepository;
    private final AudioAlbumRepository audioAlbumRepository;
    private final S3Uploader s3Uploader;

    // 오디오앨범 생성
    @Transactional
    public AudioAlbumResponseDto createAudioAlbum(AudioAlbumRequestDto requestDto, UserDetailsImpl userDetails) throws IOException {
        // 유저 확인
        Member member = memberRepository.findByMembername(userDetails.getUsername()).orElseThrow(
                () -> new InsufficientAuthenticationException(ErrorMessage.ACCESS_DENIED.getMessage()) // 401 Unauthorized
        );

        // 오디오앨범 제목 중복 확인
        Optional<AudioAlbum> found = audioAlbumRepository.findByTitle(requestDto.getTitle());
        if (found.isPresent()) {
            throw new DuplicateKeyException(ErrorMessage.DUPLICATE_AUDIOALBUM_NAME.getMessage()); // 409 Conflict
        }

        // 오디오앨범 사진 추가
        String imageUrl = s3Uploader.uploadFiles(requestDto.getBackgroundImageUrl(), "audioAlbumImages");

        AudioAlbum audioAlbum = AudioAlbum.builder()
                .title(requestDto.getTitle())
                .instruction(requestDto.getInstruction())
                .backgroundImageUrl(imageUrl)
                // categoryType(requestDto.getCategoryType())
                .build();

        audioAlbum = audioAlbumRepository.save(audioAlbum);
        return AudioAlbumResponseDto.of(audioAlbum);
    }

    // 오디오앨범 전체 조회
    @Transactional
    public Map<String, Object> findAllAudioAlbum(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable sortedPageable = PageRequest.of(page, size, sort);
        Page<AudioAlbum> audioAlbumPage = audioAlbumRepository.findAll(sortedPageable);
        List<AudioAlbumResponseDto> audioAlbumResponseDtoList = audioAlbumPage.getContent().stream().map(AudioAlbumResponseDto::new).toList();

        // 생성된 오디오앨범의 개수
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("audioAlbumCount", audioAlbumPage.getTotalElements());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("result", audioAlbumResponseDtoList);
        responseBody.put("metadata", metadata);

        return responseBody;
    }
}
