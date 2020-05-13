package com.alexander.makarov.drawing.command;

import com.credit.swiss.drawing.dataobjects.Canvas;
import com.credit.swiss.drawing.dataobjects.CommandExecutionResult;

public class QuiteCommand extends AbstractCommand {

    public static final String COMMAND_LETTER = "Q";

    public QuiteCommand(String[] args) {
        super(args);
    }

    @Override
    protected String verifyAndParseArguments(Canvas canvas, String[] args) {
        return VERIFIED;
    }

    @Override
    protected CommandExecutionResult run(Canvas canvas) {
        return new CommandExecutionResult(null, "Thank you for using drawing cmd tool",true);
    }
}
