/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tic_Tac_Toe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author khatam
 */
public class ComputerTest {
    
    public ComputerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isMovesLeft method, of class Computer.
     */
    @Test
    public void testIsMovesLeft() {
        System.out.println("isMovesLeft");
        Cell[][] board = null;
        Boolean expResult = null;
        Boolean result = Computer.isMovesLeft(board);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluate method, of class Computer.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        Cell[][] b = null;
        int expResult = 0;
        int result = Computer.evaluate(b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of minimax method, of class Computer.
     */
    @Test
    public void testMinimax() {
        System.out.println("minimax");
        Cell[][] board = null;
        int depth = 0;
        Boolean isMax = null;
        int expResult = 0;
        int result = Computer.minimax(board, depth, isMax);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBestMove method, of class Computer.
     */
    @Test
    public void testFindBestMove() {
        System.out.println("findBestMove");
        Cell[][] board = null;
        Computer.Move expResult = null;
        Computer.Move result = Computer.findBestMove(board);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of play method, of class Computer.
     */
    @Test
    public void testPlay() {
        System.out.println("play");
        Computer instance = new Computer();
        instance.play();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
