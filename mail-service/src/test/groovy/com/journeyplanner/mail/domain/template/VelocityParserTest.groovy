package com.journeyplanner.mail.domain.template

import org.apache.velocity.app.VelocityEngine
import spock.lang.Specification

class VelocityParserTest extends Specification {

    private static final String TEMPLATE_NAME = "test.vm";

    private VelocityEngine velocityEngine

    private VelocityParser velocityParser

    def setup() {
        VelocityConfig config = new VelocityConfig()
        velocityEngine = config.configVelocity()

        velocityParser = new VelocityParser(velocityEngine)
    }

    def "velocity engine should pass params to velocity template"() {
        given:
        def params = new HashMap<String, String>()
        params.put("firstName", "Indiana")

        when:
        def text = velocityParser.parse(TEMPLATE_NAME, params)

        then:
        text.isDefined()
        assert text.get().contains(params.get("firstName"))
    }
}
