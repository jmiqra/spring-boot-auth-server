package com.asraf.models.pathvariable;

import com.asraf.constants.ColumnType;

import lombok.Data;

@Data
public class ColumnPathvariable {

	private String columnName;

	private String columnType;

	public ColumnType getColumnTypeEnum() {
		return ColumnType.valueOf(this.columnType.toUpperCase());
	}

}
