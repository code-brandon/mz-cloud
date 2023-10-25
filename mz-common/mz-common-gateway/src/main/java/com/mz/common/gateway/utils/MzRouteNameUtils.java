/*
 * Copyright 2013-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mz.common.gateway.utils;

import com.mz.common.gateway.entity.MzFilterDefinition;
import com.mz.common.gateway.entity.MzGatewayRoute;
import com.mz.common.gateway.entity.MzPredicateDefinition;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Spencer Gibb
 */
public final class MzRouteNameUtils {

	private MzRouteNameUtils() {
		throw new AssertionError("Must not instantiate utility class.");
	}

	/**
	 * Generated name prefix.
	 */
	public static final String GENERATED_NAME_PREFIX = "_genkey_";

	private static final Pattern NAME_PATTERN = Pattern.compile("([A-Z][a-z0-9]+)");

	public static String generateName(int i) {
		return GENERATED_NAME_PREFIX + i;
	}

	public static Map<String, String> generateName(List<String> values) {
		Map<String, String> args = new LinkedHashMap<>();
		for (int i = 0; i < values.size(); i++) {
			args.put(generateName(i), values.get(i));
		}
		return args;
	}

	public static Map<String, String> generateName(String[] values) {
		Map<String, String> args = new LinkedHashMap<>();
		for (int i = 0; i < values.length; i++) {
			args.put(generateName(i), values[i]);
		}
		return args;
	}

	public static String normalizeToCanonicalPropertyFormat(String name) {
		Matcher matcher = NAME_PATTERN.matcher(name);
		StringBuffer stringBuffer = new StringBuffer();
		while (matcher.find()) {
			if (stringBuffer.length() != 0) {
				matcher.appendReplacement(stringBuffer,
						"-" + matcher.group(1).toLowerCase());
			}
			else {
				matcher.appendReplacement(stringBuffer, matcher.group(1).toLowerCase());
			}
		}
		return stringBuffer.toString();
	}

	private static String removeGarbage(String s) {
		int garbageIdx = s.indexOf("$Mockito");
		if (garbageIdx > 0) {
			return s.substring(0, garbageIdx);
		}
		return s;
	}


	public static void renameRouteArgs(MzGatewayRoute mzGatewayRoute) {
		List<MzFilterDefinition> filters = mzGatewayRoute.getFilters();
		List<MzPredicateDefinition> predicates = mzGatewayRoute.getPredicates();
		for (MzFilterDefinition filter : filters) {
			String[] values =  Optional.ofNullable(filter.getArgs()).orElse(new LinkedHashMap<>()).values().toArray(new String[0]);
			filter.setArgs(MzRouteNameUtils.generateName(values));
		}
		for (MzPredicateDefinition predicate : predicates) {
			String[] values = Optional.ofNullable(predicate.getArgs()).orElse(new LinkedHashMap<>()).values().toArray(new String[0]);
			predicate.setArgs(MzRouteNameUtils.generateName(values));
		}
	}

	public static void renameRouteArgs(List<MzGatewayRoute> mzGatewayRoutes) {
		for (MzGatewayRoute mzGatewayRoute : mzGatewayRoutes) {
			renameRouteArgs(mzGatewayRoute);
		}
	}
}
