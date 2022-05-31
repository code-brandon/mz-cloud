package com.mz.common.security.serializer.requse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class Oauth2RequestJackson2Deserializer extends StdDeserializer<OAuth2Request> {

    protected Oauth2RequestJackson2Deserializer() {
        super(OAuth2Request.class);
    }

    @Override
    public OAuth2Request deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        JsonNode jsonNode = objectMapper.readTree(p);

        // requestParameters
        JsonNode requestParametersJsonNode = jsonNode.get("requestParameters");
        Map<String, String> requestParameters = objectMapper.readValue(requestParametersJsonNode.traverse(objectMapper), Map.class);

        // clientId
        JsonNode clientIdJsonNode = jsonNode.get("clientId");
        String clientId = objectMapper.readValue(clientIdJsonNode.traverse(objectMapper), String.class);

        // authorities
        JsonNode authoritiesJsonNode = jsonNode.get("authorities");
        Collection<? extends GrantedAuthority> authorities = objectMapper.readValue(authoritiesJsonNode.traverse(objectMapper), Set.class);

        // approved
        JsonNode approvedJsonNode = jsonNode.get("approved");
        boolean approved = objectMapper.readValue(approvedJsonNode.traverse(objectMapper), Boolean.class);

        // scope
        JsonNode scopeJsonNode = jsonNode.get("scope");
        Set<String> scope = objectMapper.readValue(scopeJsonNode.traverse(objectMapper), Set.class);

        // resourceIds
        JsonNode resourceIdsJsonNode = jsonNode.get("resourceIds");
        Set<String> resourceIds = objectMapper.readValue(resourceIdsJsonNode.traverse(objectMapper), Set.class);

        // redirectUri
        JsonNode redirectUriJsonNode = jsonNode.get("redirectUri");
        String redirectUri = objectMapper.readValue(redirectUriJsonNode.traverse(objectMapper), String.class);

        // responseTypes
        JsonNode responseTypesJsonNode = jsonNode.get("responseTypes");
        Set<String> responseTypes = objectMapper.readValue(responseTypesJsonNode.traverse(objectMapper), Set.class);

        // extensionProperties
        JsonNode extensionPropertiesJsonNode = jsonNode.get("extensions");
        Map<String, Serializable> extensionProperties = objectMapper.readValue(extensionPropertiesJsonNode.traverse(objectMapper), Map.class);

        OAuth2Request auth2Request = new OAuth2Request(requestParameters, clientId, authorities, approved, scope, resourceIds, redirectUri, responseTypes, extensionProperties);
        return auth2Request;
    }

}