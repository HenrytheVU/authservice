package com.example.authservice.hateoas;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Resource<T> extends AbstractResource {

	@JsonUnwrapped
	private final T resource;

	public Resource(T resource) {
		this.resource = resource;
	}
}
