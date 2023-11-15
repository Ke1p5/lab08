package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

import java.util.Map;
import java.util.TreeMap;

public class DeathNoteImpl implements DeathNote {
    
    private static final int MAX_RULES = 12;
    private static final long TIME_FOR_CAUSE = 40L;
    private static final long TIME_FOR_DETAILS = 6040L;
    private static final int CAUSE = 0;
    private static final int DETAILS = 1;
    private static final int SIZE = 2; 
    private Map<String, String[]> notes;
    private String currentName;
    private long startName;
    private long finishName;
    private long startCause;
    private long finishCause;

    public DeathNoteImpl() {
        this.notes = new TreeMap<String, String[]>();
        this.currentName = "";
    }

    public String getRule(final int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > MAX_RULES) {
            throw new IllegalArgumentException("That rule does not exists.");
        } else {
            return RULES.get(ruleNumber - 1);
        }
    }

    public void writeName(final String name) {
        if (name == null) {
            throw new NullPointerException("Must write a name.");
        } else {
            currentName = name;
            this.notes.put(name, new String[SIZE]);
            this.notes.get(this.currentName)[CAUSE] = "Heart attack";
            this.startName = this.startCause = System.currentTimeMillis();
        }
    }

    public boolean writeDeathCause(final String cause) {
        if (cause == null || this.notes.isEmpty()) {
            throw new IllegalStateException("No death cause provided or Death Note is empty.");
        }
        this.finishName = System.currentTimeMillis();
        //this.startCause = System.currentTimeMillis();
        if (this.finishName - this.startName <= TIME_FOR_CAUSE) {
            this.notes.get(this.currentName)[CAUSE] = cause;
            return true;
        } else {
            return false;
        }
    }

    public boolean writeDetails(final String details) {
        if (details == null || this.notes.isEmpty()) {
            throw new IllegalStateException("No details provided or Death Note is empty.");
        }
        this.finishCause = System.currentTimeMillis();
        if (this.finishCause - this.startCause <= TIME_FOR_DETAILS) {
            this.notes.get(this.currentName)[DETAILS] = details;
            return true;
        } else {
            return false;
        }
    }

    public String getDeathCause(String name) {
        if (!notes.containsKey(name)) {
            throw new IllegalArgumentException("That name is not on the Death Note.");
        }
        return this.notes.get(name)[CAUSE];
    }

    public String getDeathDetails(final String name) {
        if (!notes.containsKey(name)) {
            throw new IllegalArgumentException("That name is not on the Death Note.");
        }
        return this.notes.get(name)[DETAILS];
    }

    public boolean isNameWritten(final String name) {
        return this.notes.containsKey(name);
    }
}
