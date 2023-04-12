package com.instagram.numble_instagram.model.dto.follow.request;

import jakarta.validation.constraints.NotNull;

public record FollowRequest(@NotNull Long toUserId) {
}
