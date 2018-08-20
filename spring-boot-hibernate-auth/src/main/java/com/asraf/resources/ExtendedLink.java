package com.asraf.resources;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.Getter;

@JsonInclude(Include.NON_EMPTY)
public class ExtendedLink extends Link {

	private static final long serialVersionUID = 1L;

	@Getter
	private HttpMethod method;

	@Getter
	private Search search;

	public ExtendedLink(Link link) {
		super(link.getHref(), link.getRel());
	}

	public ExtendedLink withMethod(HttpMethod method) {
		this.method = method;
		return this;
	}

	public ExtendedLink withSearchableData() {
		this.search = new Search().initParam().initSort().initPagination().initOperators().initFullExample();
		return this;
	}

	public ExtendedLink withPageableData() {
		this.search = new Search().initSort().initPagination().initPageableExample();
		return this;
	}

	@Data
	@JsonInclude(Include.NON_EMPTY)
	private class Search {
		private String param;
		private String sort;
		private String pagination;
		private Map<String, String> operators;
		private String example;

		public Search initParam() {
			this.param = "search={(<resourceProperty | resourceProperty.parentProperty> <operators> <value>)}";
			return this;
		}

		public Search initSort() {
			this.sort = "sort={property,asc | desc}";
			return this;
		}

		public Search initPagination() {
			this.pagination = "page={intValue}&size={intValue}";
			return this;
		}

		public Search initOperators() {
			this.operators = new LinkedHashMap<>();
			this.operators.put("Logical AND", "; | and");
			this.operators.put("Logical OR", ", | or");
			this.operators.put("Equal to", "==");
			this.operators.put("Not equal to", "!=");
			this.operators.put("Less than", "=lt= | <");
			this.operators.put("Less than or equal to", "=le= | <=");
			this.operators.put("Greater than", "=gt= | >");
			this.operators.put("Greater than or equal to", "=ge= | >=");
			this.operators.put("In", "=in=");
			this.operators.put("Not in", "=out=");
			this.operators.put("Parent access operator", ".");
			this.operators.put("Like operator", "*");
			return this;
		}

		public Search initFullExample() {
			this.example = "...endpoint?search=(prop1==val1*;prop2>val2,prop3==val3);prop4=in=(val4,val5,val6)&page=0&size=10&sort=prop5,asc&sort=prop6,desc";
			return this;
		}

		public Search initPageableExample() {
			this.example = "...endpoint?page=0&size=10&sort=prop5,asc&sort=prop6,desc";
			return this;
		}

	}

}