package com.example.authservice.hateoas;

import lombok.Data;

@Data
public class CollectionInfo {
	private final int querySize;

	private final int size;

	private final int page;

	private final int totalPage;

	private final long totalCount;

	public CollectionInfo(int querySize, int size, int page, long totalCount) {
		this.querySize = querySize;
		this.size = size;
		this.page = page;
		this.totalCount = totalCount;
		if (querySize == 0 || totalCount == 0) {
			this.totalPage = 1;
		} else {
			this.totalPage = (int) Math.ceil((double) totalCount / querySize);
		}
	}
}
