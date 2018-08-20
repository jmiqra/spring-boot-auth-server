package com.asraf.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.asraf.controllers.BaseController;
import com.asraf.entities.BaseEntity;
import com.asraf.resources.BaseResource;

public abstract class BaseResourceAssembler<TEntity extends BaseEntity, TResource extends BaseResource>
		extends ResourceAssemblerSupport<TEntity, TResource> {

	public BaseResourceAssembler(Class<? extends BaseController> controllerClass, Class<TResource> resourceClass) {
		super(controllerClass, resourceClass);
	}

}
