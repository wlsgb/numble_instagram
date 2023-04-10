package com.instagram.numble_instagram.service.feed;

import com.instagram.numble_instagram.exception.invalidRequest.NotPostRegUserException;
import com.instagram.numble_instagram.exception.notFound.PostNotFoundException;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.model.entity.feed.PostEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.feed.PostRepository;
import com.instagram.numble_instagram.util.file.FileUtil;
import com.instagram.numble_instagram.util.file.ImageFileStoreImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostWriteService {

    private final PostRepository postRepository;
    private final ImageFileStoreImpl imageFileStore;

    /**
     * 글 등록
     */
    public PostResponse register(String content, String imageUrl, UserEntity regUser) {
        PostEntity newPost = PostEntity.register(content, imageUrl, regUser);
        newPost = postRepository.save(newPost);
        return PostResponse.convertResponse(newPost);
    }

    /**
     * 글 수정
     */
    public PostResponse modify(UserEntity user, Long postId, String content, MultipartFile imageFile) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        checkRegUser(user, post);
        post.changeContent(content);
        checkImageAndChange(imageFile, post);
        return PostResponse.convertResponse(post);
    }

    /**
     * 글 삭제
     */
    public void delete(UserEntity user, Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        checkRegUser(user, post);
        if (post.isImageUrl())
            imageFileStore.deleteFile(post.getImageUrl());
        postRepository.delete(post);
    }

    /**
     * 등록자 체크
     */
    private static void checkRegUser(UserEntity user, PostEntity post) {
        if (!post.isRegUser(user))
            throw new NotPostRegUserException();
    }

    /**
     * 이미지 체크후 변경
     */
    private void checkImageAndChange(MultipartFile imageFile, PostEntity post) {
        if (!FileUtil.existFile(imageFile))
            return;

        if (post.isImageUrl())
            imageFileStore.deleteFile(post.getImageUrl());

        String imageUrl = imageFileStore.uploadFile(imageFile);
        post.changeImageUrl(imageUrl);
    }
}
