package t02;/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 



/*
 * GridBagLayoutDemo.java requires no other files.
 */

import javax.swing.*;
import java.awt.*;

public class GridBagLayoutDemo {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    public static void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            cons.fill = GridBagConstraints.HORIZONTAL;
        }

        button = new JButton("Button 1");
        if (shouldWeightX) {
            cons.weightx = 0.5;
        }
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.gridx = 0;
        cons.gridy = 0;
        pane.add(button, cons);

        button = new JButton("Button 2");
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 0.5;
        cons.gridx = 1;
        cons.gridy = 0;
        pane.add(button, cons);

        button = new JButton("Button 3");
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 0.5;
        cons.gridx = 2;
        cons.gridy = 0;
        pane.add(button, cons);

        button = new JButton("Long-Named Button 4");
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipady = 40;      //make this component tall
        cons.weightx = 0.0;
        cons.gridwidth = 3;
        cons.gridx = 0;
        cons.gridy = 1;
        pane.add(button, cons);

        button = new JButton("5");
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipady = 0;       //reset to default
        cons.weighty = 1.0;   //request any extra vertical space
        cons.anchor = GridBagConstraints.PAGE_END; //bottom of space
        cons.insets = new Insets(10, 0, 0, 0);  //top padding
        cons.gridx = 1;       //aligned with button 2
        cons.gridwidth = 2;   //2 columns wide
        cons.gridy = 2;       //third row
        pane.add(button, cons);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
