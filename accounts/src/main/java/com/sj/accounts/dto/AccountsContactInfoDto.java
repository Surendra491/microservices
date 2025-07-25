package com.sj.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


/*
* Record is a new type of class introduced in JAVA 17.
* Sometimes we want Pojo class or DTO classes to simply act as data carries
* Record class have final felids and getters constructor, we can only initialize data once we cont change values
*
* Field names as to be as properties.yml file
* */

@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(String message, Map<String,String> contactDetails, List<String> onCallSupport) {
}
