package com.instagram.numble_instagram.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CursorResult<T> {
	private List<T> list;
	private Boolean hasNext;
}
