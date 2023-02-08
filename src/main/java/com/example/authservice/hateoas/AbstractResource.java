package com.example.authservice.hateoas;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.val;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class AbstractResource {
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	final Map<String, String> _links = new HashMap<>();

	public void addLink(UriComponentsBuilder uriBuilder, Link... links) {
		for (val link : links) {
			_links.put(link.getRel(), uriBuilder.cloneBuilder().path(link.getHref()).build().toUriString());
		}
	}

	public void setLinks(UriComponentsBuilder uriBuilder, List<Link> links) {
		_links.clear();
		links.forEach(l -> this.addLink(uriBuilder, l));
	}
}
