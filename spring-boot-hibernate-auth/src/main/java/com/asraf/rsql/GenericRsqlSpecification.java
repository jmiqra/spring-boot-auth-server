package com.asraf.rsql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.asraf.utils.DateUtils;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

@SuppressWarnings("serial")
public class GenericRsqlSpecification<T> implements Specification<T> {

	private String property;
	private ComparisonOperator operator;
	private List<String> arguments;

	public GenericRsqlSpecification(final String property, final ComparisonOperator operator,
			final List<String> arguments) {
		super();
		this.property = property;
		this.operator = operator;
		this.arguments = arguments;
	}

	@Override
	public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
		final List<Object> args = castArguments(root);
		final Object argument = args.get(0);
		switch (RsqlSearchOperation.getSimpleOperator(operator)) {

		case EQUAL: {
			if (argument instanceof String) {
				return builder.like(this.getCriteriaPath(root), argument.toString().replace('*', '%'));
			} else if (argument == null) {
				return builder.isNull(this.getCriteriaPath(root));
			} else if (argument instanceof Date) {
				return builder.equal(this.getCriteriaPath(root), (Date) argument);
			} else {
				return builder.equal(this.getCriteriaPath(root), argument);
			}
		}
		case NOT_EQUAL: {
			if (argument instanceof String) {
				return builder.notLike(this.getCriteriaPath(root), argument.toString().replace('*', '%'));
			} else if (argument == null) {
				return builder.isNotNull(this.getCriteriaPath(root));
			} else {
				return builder.notEqual(this.getCriteriaPath(root), argument);
			}
		}
		case GREATER_THAN: {
			if (argument instanceof Date) {
				return builder.greaterThan(this.getCriteriaPath(root), (Date) argument);
			}
			return builder.greaterThan(this.getCriteriaPath(root), argument.toString());
		}
		case GREATER_THAN_OR_EQUAL: {
			if (argument instanceof Date) {
				return builder.greaterThanOrEqualTo(this.getCriteriaPath(root), (Date) argument);
			}
			return builder.greaterThanOrEqualTo(this.getCriteriaPath(root), argument.toString());
		}
		case LESS_THAN: {
			if (argument instanceof Date) {
				return builder.lessThan(this.getCriteriaPath(root), (Date) argument);
			}
			return builder.lessThan(this.getCriteriaPath(root), argument.toString());
		}
		case LESS_THAN_OR_EQUAL: {
			if (argument instanceof Date) {
				return builder.lessThanOrEqualTo(this.getCriteriaPath(root), (Date) argument);
			}
			return builder.lessThanOrEqualTo(this.getCriteriaPath(root), argument.toString());
		}
		case IN:
			return this.getCriteriaPath(root).in(args);
		case NOT_IN:
			return builder.not(this.getCriteriaPath(root).in(args));
		}

		return null;
	}

	// === private

	private List<Object> castArguments(final Root<T> root) {
		final List<Object> args = new ArrayList<Object>();
		final Class<? extends Object> type = this.getCriteriaPath(root).getJavaType();

		for (final String argument : arguments) {
			if (type.equals(Integer.class)) {
				args.add(Integer.parseInt(argument));
			} else if (type.equals(Long.class)) {
				args.add(Long.parseLong(argument));
			} else if (type.equals(Date.class)) {
				args.add(DateUtils.parseGmtDateOrTime(argument));
			} else {
				args.add(argument);
			}
		}

		return args;
	}

	private <RET> Path<RET> getCriteriaPath(final Root<T> root) {
		String[] pathObjects = property.split("[.]");
		Path<RET> criteriaPath = pathObjects.length == 1 ? root.get(pathObjects[0])
				: root.get(pathObjects[0]).<RET>get(pathObjects[1]);
		return criteriaPath;
	}
}