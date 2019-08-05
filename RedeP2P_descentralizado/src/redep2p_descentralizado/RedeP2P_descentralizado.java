/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redep2p_descentralizado;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author gabriel e Samuel
 */
public class RedeP2P_descentralizado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JButton open = new JButton();
        JFileChooser  fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("files", "pdf", "txt", "png");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new java.io.File("../"));
        fc.setDialogTitle("Choose a file");
        if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
            System.out.println("You chose: " + fc.getSelectedFile().getAbsolutePath());
        }
    }
    
}
