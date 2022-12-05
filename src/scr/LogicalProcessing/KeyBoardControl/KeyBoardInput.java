package scr.LogicalProcessing.KeyBoardControl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardInput implements KeyListener {

    private static KeyBoardInput instance;

    public static KeyBoardInput getKeyBoardInput()
    {
        if(instance == null)
        {
            instance  = new KeyBoardInput();
        }
        return instance;
    }


    private int[] keys = new int[256];
    private boolean[] key_state_up = new boolean[256];
    private boolean[] key_state_down = new boolean[256];
    private boolean keyPressed = false;
    private boolean keyReleased = false;


    public KeyBoardInput()
    {

    }
    @Override
    public void keyTyped(KeyEvent e) {



    }

    @Override
    public void keyPressed(KeyEvent e) {
        if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
            keys[e.getKeyCode()] = (int) System.currentTimeMillis();
            key_state_down[e.getKeyCode()] = true;
            key_state_up[e.getKeyCode()] = false;
            keyPressed = true;
            keyReleased = false;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
            keys[e.getKeyCode()] = 0;
            key_state_up[e.getKeyCode()] = true;
            key_state_down[e.getKeyCode()] = false;
            keyPressed = false;
            keyReleased = true;
        }

    }

    public boolean isKeyDown( int key ) {
        return key_state_down[key];
    }

    public boolean isKeyUp( int key ) {
        return key_state_up[key];
    }

    public boolean isAnyKeyDown() {
        return keyPressed;
    }

    public boolean isAnyKeyUp() {
        return keyReleased;
    }

    public void update() {
        key_state_up = new boolean[256];
        keyReleased = false;

    }

}
