package com.mz.common.security.serializer.access;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessTokenJackson2Deserializer;

import java.io.IOException;

public class Oauth2AccessTokenJackson2Deserializer extends StdDeserializer<OAuth2AccessToken> {

    private static final OAuth2AccessTokenJackson2Deserializer DESERIALIZER = new OAuth2AccessTokenJackson2Deserializer();

    public Oauth2AccessTokenJackson2Deserializer() {
        super(OAuth2AccessToken.class);
    }

    @Override
    public OAuth2AccessToken deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return DESERIALIZER.deserialize(p, ctxt);
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return DESERIALIZER.deserialize(p, ctxt);
    }
}