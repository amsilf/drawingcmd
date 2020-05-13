package com.alexander.makarov.drawing.command;

import com.credit.swiss.drawing.dataobjects.Canvas;
import com.credit.swiss.drawing.dataobjects.CommandExecutionResult;

public abstract class AbstractCommand {

    protected static final String NOT_VERIFIED = "not verified";
    protected static final String VERIFIED = "verified";

    private final String[] args;

    protected AbstractCommand(String[] args) {
        this.args = args;
    }

    public CommandExecutionResult execute(Canvas canvas) {
        String verificationMessage = verifyAndParseArguments(canvas, args);
        if (!verificationMessage.equals(VERIFIED)) {
            return new CommandExecutionResult(canvas, verificationMessage, false);
        }

        return run(canvas);
    }

    protected abstract String verifyAndParseArguments(Canvas canvas, String[] args);

    protected abstract CommandExecutionResult run(Canvas canvas);

    protected int toInteger(String str) throws Exception {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            throw new Exception(nfe);
        }
    }

}