/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marcovillano.avventura_testuale;

import java.io.Serializable;

/**
 *
 * @author marcovillano
 */
public abstract class Puzzle implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String description;
    protected String solution;
    protected boolean solved;

    public Puzzle(String description, String solution) {
        this.description = description;
        this.solution = solution;
        this.solved = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSolved() {
        return solved;
    }

    public abstract boolean solve(String playerInput);

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}

