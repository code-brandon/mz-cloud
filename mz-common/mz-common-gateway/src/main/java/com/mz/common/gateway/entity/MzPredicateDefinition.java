package com.mz.common.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MzPredicateDefinition {

	@NotNull
	private String name;

	private Map<String, String> args = new LinkedHashMap<>();


}
