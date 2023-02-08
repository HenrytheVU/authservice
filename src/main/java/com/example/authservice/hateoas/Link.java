package com.example.authservice.hateoas;

import lombok.Getter;
import lombok.With;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class Link {

	@Getter
	@With
	private final String rel;
	@With
	private final String baseHref;

	private final List<Object> subPaths = new ArrayList<>();

	private final MultiValueMap<String, Object> queryParams = new LinkedMultiValueMap<>();

	public Link(String rel, String baseHref) {
		this.rel = rel;
		this.baseHref = baseHref;
	}

	public static String urlValueOf(Map<String, String> filters) {
		return filters.entrySet().stream().map(e -> "filter=" + e.getKey() + ":" + e.getValue()).collect(joining("&"));
	}

	public Link param(String name, Object value) {
		this.queryParams.add(name, value);
		return this;
	}

	public Link params(Map<String, String> filters) {
		filters.forEach((key, value) -> this.queryParams.add("filter", key + ":" + value));
		return this;
	}

	public Link placeholder(String paramName) {
		this.queryParams.add(paramName, ":" + paramName);
		return this;
	}

	public Link path(Object subPath) {
		this.subPaths.add(URLEncoder.encode(subPath.toString(), StandardCharsets.UTF_8));
		return this;
	}

	public Link pathRaw(Object subPath) {
		this.subPaths.add(subPath);
		return this;
	}

	public String getHref() {
		return this.baseHref.concat(subPaths.stream().map(s -> "/" + s).collect(joining("")))
		                    .concat(queryParams.isEmpty() ? "" : "?")
		                    .concat(queryParams.entrySet()
		                                       .stream()
		                                       .map(e -> e.getValue()
		                                                  .stream()
		                                                  .map(value -> e.getKey() + "=" + value)
		                                                  .collect(joining("&")))
		                                       .collect(joining("&")));
	}

	public Link query(String query) {
		return param(QUERY, query);
	}

	public Link page(int page) {
		return param(PAGE, page);
	}

	public Link size(int size) {
		return param(SIZE, size);
	}

	public Link orderBy(String orderBy) {
		return param(ORDER_BY, orderBy);
	}

	public Link sortOrder(String sortOrder) {
		return param(SORT_ORDER, sortOrder);
	}

	public Link filter(Map<String, String> filters) {
		return params(filters);
	}

	public Link queryPlaceholder() {
		return placeholder(QUERY);
	}

	public Link orderByPlaceholder() {
		return placeholder(ORDER_BY);
	}

	public Link sortOrderPlaceHolder() {
		return placeholder(SORT_ORDER);
	}

	public static final String FIRST = "first";
	public static final String LAST = "last";
	public static final String NEXT = "next";
	public static final String PREV = "prev";
	public static final String QUERY = "query";
	public static final String PAGE = "page";
	public static final String SIZE = "size";
	public static final String ORDER_BY = "orderBy";
	public static final String SORT_ORDER = "sortOrder";

}
