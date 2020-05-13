package com.alexander.makarov.drawing.command;

import spock.lang.*

class CreateCanvasSpecification extends Specification {
    def "I create 20x4 canvas"() {
        given:
            def arguments = new String[]{ "C", "20", "4" }
            def createCanvasCommand = new CreateCanvasCommand(arguments)
        when:
            def executionResults = createCanvasCommand.execute()
        then:
            executionResults.getCanvas().getHeight() == 4
            executionResults.getCanvas().getWidth() == 20
    }

    def "I cannot create canvas with negative dimensions" () {
        given:
            def arguments = new String[]{ "C", "-5", "-4" }
            def createCanvasCommand = new CreateCanvasCommand(arguments)
        when:
            def executionResults = createCanvasCommand.execute()
        then:
            executionResults.getMessage() == "Arguments must be positive";
    }
}