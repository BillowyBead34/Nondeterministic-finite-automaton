package com.sw.model.automata.exceptions;

/**
 *
 * @author HikingCarrot7
 */
public class CadenaVaciaException extends RuntimeException {

    /**
     * Creates a new instance of <code>NoHayCadenaException</code> without detail message.
     */
    public CadenaVaciaException() {
    }

    /**
     * Constructs an instance of <code>NoHayCadenaException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CadenaVaciaException(String msg) {
        super(msg);
    }
}
