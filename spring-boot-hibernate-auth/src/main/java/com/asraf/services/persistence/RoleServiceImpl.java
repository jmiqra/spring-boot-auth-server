package com.asraf.services.persistence;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.Role;
import com.asraf.repositories.RoleRepository;
import com.asraf.rsql.CustomRsqlVisitor;
import com.asraf.services.RoleService;
import com.asraf.utils.ExceptionPreconditions;
import com.asraf.utils.StringUtils;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	private RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public Role save(Role role) {
		return roleRepository.save(role);
	}

	public void delete(Role role) {
		roleRepository.delete(role);
	}

	public Role getById(Long id) {
		try {
			return roleRepository.findById(id).get();
		} catch (NoSuchElementException nseex) {
			return ExceptionPreconditions.entityNotFound(Role.class, "id", id.toString());
		}
	}

	public Iterable<Role> getAll() {
		return roleRepository.findAll();
	}

	public Page<Role> getByQuery(String search, Pageable pageable) {
		if (StringUtils.isNullOrEmpty(search))
			return roleRepository.findAll(pageable);
		Node rootNode = new RSQLParser().parse(search);
		Specification<Role> spec = rootNode.accept(new CustomRsqlVisitor<Role>());
		Page<Role> roles = roleRepository.findAll(spec, pageable);
		return roles;
	}

}
