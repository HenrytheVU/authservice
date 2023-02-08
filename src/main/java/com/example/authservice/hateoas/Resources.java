package com.example.authservice.hateoas;

import lombok.Data;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.example.authservice.hateoas.Link.*;

@Data
public class Resources<T extends AbstractResource> extends AbstractResource {

	private final CollectionInfo _info;

	private final List<T> resources;

	public Resources(CollectionInfo _info, List<T> resources) {
		this._info = _info;
		this.resources = resources;
	}

	private boolean hasFirstPage() {
		return _info.getTotalCount() > _info.getSize() && _info.getPage() > 1;
	}

	private boolean hasLastPage() {
		return hasNextPage();
	}

	private boolean hasNextPage() {
		return _info.getPage() < _info.getTotalPage();
	}

	private boolean hasPrevPage() {
		return _info.getPage() > 1 && _info.getTotalPage() != 1 && _info.getPage() < _info.getTotalPage() + 1;
	}

	public void addFirst(UriComponentsBuilder uriComponentsBuilder, Link first) {
		if (hasFirstPage()) {
			addLink(uriComponentsBuilder, first);
		}
	}

	public void addLast(UriComponentsBuilder uriComponentsBuilder, Link last) {
		if (hasLastPage()) {
			addLink(uriComponentsBuilder, last);
		}
	}

	public void addNext(UriComponentsBuilder uriComponentsBuilder, Link next) {
		if (hasNextPage()) {
			addLink(uriComponentsBuilder, next);
		}
	}

	public void addPrev(UriComponentsBuilder uriComponentsBuilder, Link prev) {
		if (hasPrevPage()) {
			addLink(uriComponentsBuilder, prev);
		}
	}

	@Override
	public void setLinks(UriComponentsBuilder uriBuilder, List<Link> links) {
		this._links.clear();
		links.forEach(l -> {
			switch (l.getRel()) {
				case FIRST -> addFirst(uriBuilder, l);
				case LAST -> addLast(uriBuilder, l);
				case NEXT -> addNext(uriBuilder, l);
				case PREV -> addPrev(uriBuilder, l);
				default -> addLink(uriBuilder, l);
			}
		});
	}
}
