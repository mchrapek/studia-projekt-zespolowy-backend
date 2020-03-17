package com.journeyplanner.mail.domain.template;

import io.vavr.control.Option;

import java.util.Map;

public interface TemplateParser {

    Option<String> parse(String templateName, Map<String, String> params);
}
