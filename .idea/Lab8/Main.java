package org.example;

import javax.swing.*;

public class Main {
    private static void createAndShowGUI(){
        MazeFrame frame = new MazeFrame();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}
