package edu.samir.demo.springboottesting.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private Main main;

    @BeforeEach
    void setUp() {
        this.main = new Main();
    }

    @Test
    void ShouldReturnFormattedUppercase(){
        String samir = "samir";
        String result = main.format(samir);

        assertEquals("SAMIR", result);
    }
}