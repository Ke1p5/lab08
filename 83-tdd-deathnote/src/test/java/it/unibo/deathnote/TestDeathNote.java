package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {

    private DeathNote deathNote;

    @BeforeEach
    public void dropDeathNote() {
        this.deathNote = new DeathNoteImpl();
    }

    @Test
    public void testInconsisentRules() {
        try {
            deathNote.getRule(0);
            deathNote.getRule(-1);
        } catch (IllegalArgumentException e){
            Assertions.assertNotEquals("", e.getMessage());
            Assertions.assertNotEquals(null, e.getMessage());
        }
    }

    @Test
    public void testAllRules() {
        for (String rule: DeathNote.RULES) {
            Assertions.assertNotEquals("", rule);
            Assertions.assertNotEquals(null, rule);
        }
    }

    @Test
    public void testName() {
        Assertions.assertEquals(false, deathNote.isNameWritten("Enzo Puglioli"));
        deathNote.writeName("Enzo Puglioli");
        Assertions.assertEquals(true, deathNote.isNameWritten("Enzo Puglioli"));
        Assertions.assertEquals(false, deathNote.isNameWritten("Oriana Bordoni"));
        Assertions.assertEquals(false, deathNote.isNameWritten(""));
    }

    @Test
    public void testCause() {
        try {
            this.deathNote.writeDeathCause("Car accident");
        } catch (IllegalStateException e) {
            Assertions.assertEquals("No death cause provided or Death Note is empty.", e.getMessage());
        }
        deathNote.writeName("Enzo Puglioli");
        assertEquals("Heart attack", deathNote.getDeathCause("Enzo Puglioli"));
        deathNote.writeName("Luca Bertozzi");
        boolean checkTrue = deathNote.writeDeathCause("Karting accident");
        assertEquals(checkTrue, true);
        assertEquals(deathNote.getDeathCause("Luca Bertozzi"), "Karting accident");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Interrupted Sleep!");
        }
        boolean checkFalse = deathNote.writeDeathCause("Motorcycle accident.");
        assertEquals(checkFalse, false);
        assertEquals(deathNote.getDeathCause("Luca Bertozzi"), "Karting accident");
    }

    @Test
    public void testDetails() {
        try {
            deathNote.writeDetails("At 8:23 AM on his way to work.");
        } catch (IllegalStateException e) {
            Assertions.assertEquals("No details provided or Death Note is empty.", e.getMessage());
        }
        deathNote.writeName("Enzo Puglioli");
        Assertions.assertNull(deathNote.getDeathDetails("Enzo Puglioli"));
        boolean justGone = deathNote.writeDetails("Ran for too long.");
        Assertions.assertTrue(justGone);
        Assertions.assertEquals(deathNote.getDeathDetails("Enzo Puglioli"), "Ran for too long.");
        deathNote.writeName("Gianna Bordoni");
        try {
            Thread.sleep(6100);
        } catch (InterruptedException e) {
            System.out.println("Interrupted Sleep!");
        }    
        boolean alreadyGone = deathNote.writeDetails("Alzheimer and old age.");
        Assertions.assertFalse(alreadyGone);
        Assertions.assertNull(deathNote.getDeathDetails("Gianna Bordoni"));
    }
}