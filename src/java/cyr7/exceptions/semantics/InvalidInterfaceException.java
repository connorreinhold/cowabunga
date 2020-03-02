package cyr7.exceptions.semantics;

import cyr7.exceptions.ErrorType;
import cyr7.exceptions.LexerException;
import cyr7.exceptions.ParserException;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class InvalidInterfaceException extends SemanticException {

    /**
     *
     */
    private static final long serialVersionUID = 4987332303897695788L;
    private final static String SOURCE_FILE_ERROR_MSG = "Invalid interface used";
    private ErrorType interfaceErrorType;
    private String interfaceErrorMsg;
    private String interfaceFilename;
    private int interfaceLine;
    private int interfaceCol;

    private void setup(ErrorType type, String filename, String msg, int line,
            int col) {
        this.interfaceErrorType = type;
        this.interfaceFilename = filename;
        this.interfaceErrorMsg = msg;
        this.interfaceLine = line;
        this.interfaceCol = col;
    }

    public InvalidInterfaceException(ParserException ixiExn, Location loc) {
        super(SOURCE_FILE_ERROR_MSG, loc);
        setup(ErrorType.Syntax,
                ixiExn.filename,
                ixiExn.errorMsg,
                ixiExn.line,
                ixiExn.column);
    }

    public InvalidInterfaceException(LexerException ixiExn, Location loc) {
        super(SOURCE_FILE_ERROR_MSG, loc);
        setup(ErrorType.Lexical,
                ixiExn.filename,
                ixiExn.errorMsg,
                ixiExn.line,
                ixiExn.col);
    }

    public InvalidInterfaceException(SemanticException ixiExn, Location loc) {
        super(SOURCE_FILE_ERROR_MSG, loc);
        setup(ErrorType.Semantic,
                ixiExn.filename,
                ixiExn.errorMsg,
                ixiExn.line,
                ixiExn.col);
    }

    public ErrorType getInterfaceErrorType() {
        return this.interfaceErrorType;
    }

    public int getInterfaceLine() {
        return this.interfaceLine;
    }

    public int getInterfaceCol() {
        return this.interfaceCol;
    }

    public String getInterfaceFilename() {
        return this.interfaceFilename;
    }

    public String getInterfaceErrorMsg() {
        return this.interfaceErrorMsg;
    }


}
