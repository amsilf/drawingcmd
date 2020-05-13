package com.credit.swiss.drawing.dataobjects;

public class CommandExecutionResult {

    private Canvas canvas;
    private String message;
    private boolean isSuccessful;

    public CommandExecutionResult(Canvas canvas, String message, boolean isSuccessful) {
        this.canvas = canvas;
        this.message = message;
        this.isSuccessful = isSuccessful;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
