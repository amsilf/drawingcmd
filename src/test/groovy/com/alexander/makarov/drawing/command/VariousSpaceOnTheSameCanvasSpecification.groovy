package com.alexander.makarov.drawing.command

import spock.lang.Specification

class VariousSpaceOnTheSameCanvasSpecification extends Specification {

    def "I cannot run unavailable command" () {
        when:
            def commandFactory = new CommandFactory();
            commandFactory.getCommand('D 1 2')
        then:
            thrown RuntimeException
    }

    def "I draw two lines, rectangle, and fill various areas with various colours" () {
        given:
            def createCanvas = new CreateCanvasCommand("C", "20", "4")
            def drawLine1 = new DrawLineCommand("L", "1", "2", "6", "2")
            def drawLine2 = new DrawLineCommand("L", "6", "3", "6", "4")
            def drawRectangle = new DrawRectangleCommand("R", "14", "1", "18", "3")
            def fillArea = new FillAreaCommand("B", "10", "3", "o")
        when:
            def canvas = createCanvas.execute(null).canvas
            canvas = drawLine1.execute(canvas).canvas
            canvas = drawLine2.execute(canvas).canvas
            canvas = drawRectangle.execute(canvas).canvas
            canvas = fillArea.execute(canvas).canvas
        then:
            canvas.getDotColour(1, 1) == 'o' as char
            canvas.getDotColour(1, 2) == 'x' as char
            canvas.getDotColour(1, 3) == ' ' as char
    }

    def "I split canvas by 4 squares and fill them by different colours" () {
        given:
            def createCanvas = new CreateCanvasCommand("C", "5", "5")
            def drawLine1 = new DrawLineCommand("L", "3", "1", "3", "5")
            def drawLine2 = new DrawLineCommand("L", "1", "3", "5", "3")
            def fillArea1 = new FillAreaCommand("B", "1", "1", "a")
            def fillArea2 = new FillAreaCommand("B", "4", "1", "b")
            def fillArea3 = new FillAreaCommand("B", "1", "4", "c")
            def fillArea4 = new FillAreaCommand("B", "4", "4", "d")
        when:
            def canvas = createCanvas.execute(null).canvas
            canvas = drawLine1.execute(canvas).canvas
            canvas = drawLine2.execute(canvas).canvas
            canvas = fillArea1.execute(canvas).canvas
            canvas = fillArea2.execute(canvas).canvas
            canvas = fillArea3.execute(canvas).canvas
            canvas = fillArea4.execute(canvas).canvas
        then:
            canvas.getDotColour(1, 1) == 'a' as char
            canvas.getDotColour(4, 1) == 'b' as char
            canvas.getDotColour(1, 4) == 'c' as char
            canvas.getDotColour(4, 4) == 'd' as char
    }

}