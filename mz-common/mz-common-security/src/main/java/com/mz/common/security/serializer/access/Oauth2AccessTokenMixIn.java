package com.mz.common.security.serializer.access;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonSerialize(using = Oauth2AccessTokenJackson2Serializer.class)
@JsonDeserialize(using = Oauth2AccessTokenJackson2Deserializer.class)
public interface Oauth2AccessTokenMixIn {
}