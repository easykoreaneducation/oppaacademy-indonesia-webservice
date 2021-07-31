package com.oppaacademy.springboot.config.auth.dto;

import com.oppaacademy.springboot.domain.user.Role;
import com.oppaacademy.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String attributeName;
    private String attributeEmail;
    private String attributeOpenid;
    private String attributePicture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String attributeName,
                           String attributeEmail,
                           String attributeOpenid,
                           String attributePicture) {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.attributeName = attributeName;
        this.attributeEmail = attributeEmail;
        this.attributeOpenid = attributeOpenid;
        this.attributePicture = attributePicture;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributesName,
                                     Map<String, Object> attributes) {

        if("facebook".equals(registrationId)) {
            return ofFacebook(userNameAttributesName, attributes);
        }

        return ofGoogle(userNameAttributesName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .attributeName((String) attributes.get("name"))
                .attributeEmail((String) attributes.get("email"))
                .attributeOpenid((String) attributes.get("sub"))
                .attributePicture((String) attributes.get("picture"))
                .build();
    }

    private static OAuthAttributes ofFacebook(String userNameAttributeName,
                                              Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .attributeName((String) attributes.get("name"))
                .attributeEmail((String) attributes.get("email"))
                .attributeOpenid((String) attributes.get("id"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .user_name(attributeName)
                .user_email(attributeEmail)
                .user_openid(attributeOpenid)
                .user_picture(attributePicture)
                .user_role(Role.GUEST)
                .build();
    }
}
