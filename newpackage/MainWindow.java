
package newpackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class MainWindow extends javax.swing.JFrame {
    private String filename;
    private File lastPath = null;
    private String icon_path;
    private final LinkedList<String> unknown = new LinkedList<>();

    public MainWindow() {
        initComponents();
        setLocationRelativeTo(null);
        newFile();
    }
    
    private void newFile(){
        filename = "";
        removeIcon();
        unknown.clear();
        jTextFieldName.setText("");
        jTextFieldCommand.setText("");
        jCheckBoxTerminal.setSelected(false);
        jCheckBoxStartupNotify.setSelected(true);
    }
    
    private void removeIcon(){
        icon_path = "";
        jButtonIcon.setIcon(null);
    }
    
    private void saveFile(){
        if(filename != null && filename.length() > 0){
            saveFileAs(new File(filename));
        } else {
            saveFileAs(saveDialog());
        }
    }
    
    private void saveFileAs(File file){
        if(file != null){
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("[Desktop Entry]\n");
                bw.write("Name=" + jTextFieldName.getText().trim() + "\n");
                bw.write("Exec=" + jTextFieldCommand.getText().trim() + "\n");
                if(icon_path.length() > 0) bw.write("Icon=" + icon_path + "\n");
                bw.write("Type=Application\n");
                bw.write("Terminal=" + jCheckBoxTerminal.isSelected() + "\n");
                bw.write("StartupNotify=" + jCheckBoxStartupNotify.isSelected() + "\n");
                for(String s : unknown) bw.write(s+"\n");
                bw.close();
                filename = file.getAbsolutePath();
            } catch (IOException ex) {
                warningDialog("Could not save file to "+file.getAbsolutePath()+" "+ex.getMessage());
            }
        } else {
            filename = "";
            warningDialog("Could not save file.");
        }
    }
    
    private void openFile(){
        File file = openDialog();
        if(file != null){
            newFile();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                
                if(!line.trim().equals("[Desktop Entry]")){
                    warningDialog("Invalid data.");
                    br.close();
                    return;
                }
                
                while((line = br.readLine()) != null){
                    processLine(line);
                }
            } catch (IOException ex) {
                warningDialog("Error while reding file: " + ex.getMessage());
            }
        }
    }
    
    private void processLine(String line){
        if(line.trim().length() <= 0) return;
        
        String type, content;
        try {
            type = line.substring(0, line.indexOf("="));
        } catch (IndexOutOfBoundsException e){
            type = "";
        }
        
        try {
            content = line.substring(line.indexOf("=")+1);
        } catch (IndexOutOfBoundsException e){
            content = "";
        }
        
        switch(type){
            case "Name":
                jTextFieldName.setText(content);
                break;
            case "Exec":
                jTextFieldCommand.setText(content);
                break;
            case "Icon":
                icon_path = content;
                setIcon(icon_path);
                break;
            case "Type":
                break;
            case "Terminal":
                jCheckBoxTerminal.setSelected(content.equals("true") || content.equals("1"));
                break;
            case "StartupNotify":
                jCheckBoxStartupNotify.setSelected(content.equals("true") || content.equals("1"));
                break;
            default:
                unknown.add(line);
                break;
        }
    }
    
    private void loadIcon(){
        File file = openDialog();
        if(file != null){
            icon_path = file.getAbsolutePath();
            setIcon(icon_path);
        }
    }
    
    private void setIcon(String path){
        ImageIcon ii = new ImageIcon(path);
        jButtonIcon.setIcon(new ImageIcon(ii.getImage().getScaledInstance(jButtonIcon.getWidth(), -1,
                java.awt.Image.SCALE_SMOOTH)));
    }
    
    private File openDialog(){
        JFileChooser fch = new JFileChooser();
        fch.setCurrentDirectory(lastPath);
        fch.showOpenDialog(this);
        lastPath = fch.getCurrentDirectory();
        return fch.getSelectedFile();
    }
    
    private File saveDialog(){
        JFileChooser fch = new JFileChooser();
        fch.showSaveDialog(this);
        return fch.getSelectedFile();
    }
    
    private void warningDialog(String message){
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private void informationDialog(String message){
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void choosePath() {
        File file = openDialog();
        if(file != null){
            jTextFieldCommand.setText(file.getAbsolutePath());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButtonIcon = new javax.swing.JButton();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldCommand = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBoxTerminal = new javax.swing.JCheckBox();
        jCheckBoxStartupNotify = new javax.swing.JCheckBox();
        jButtonPath = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemSaveAs = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DesktopLinkCreator");
        setPreferredSize(new java.awt.Dimension(400, 200));
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWeights = new double[] {0.0, 0.0, 1.0, 0.0};
        getContentPane().setLayout(layout);

        jButtonIcon.setPreferredSize(new java.awt.Dimension(56, 48));
        jButtonIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonIconMousePressed(evt);
            }
        });
        jButtonIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIconActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 2, 8);
        getContentPane().add(jButtonIcon, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 4);
        getContentPane().add(jTextFieldName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 4, 2);
        getContentPane().add(jTextFieldCommand, gridBagConstraints);

        jLabel1.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 2, 2);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Command");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        getContentPane().add(jLabel2, gridBagConstraints);

        jCheckBoxTerminal.setText("Run in terminal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 0);
        getContentPane().add(jCheckBoxTerminal, gridBagConstraints);

        jCheckBoxStartupNotify.setSelected(true);
        jCheckBoxStartupNotify.setText("Startup notify");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 0);
        getContentPane().add(jCheckBoxStartupNotify, gridBagConstraints);

        jButtonPath.setText("...");
        jButtonPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPathActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 4);
        getContentPane().add(jButtonPath, gridBagConstraints);

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 4, 4);
        getContentPane().add(jButtonSave, gridBagConstraints);

        jMenuFile.setText("File");

        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNew.setText("New");
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemNew);

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOpen.setText("Open");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpen);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setText("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSave);

        jMenuItemSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSaveAs.setText("Save as");
        jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveAsActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSaveAs);
        jMenuFile.add(jSeparator1);

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar1.add(jMenuFile);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIconActionPerformed
        loadIcon();
    }//GEN-LAST:event_jButtonIconActionPerformed

    private void jButtonIconMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIconMousePressed
        if(evt.getButton() == 3){
            removeIcon();
        }
    }//GEN-LAST:event_jButtonIconMousePressed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        saveFile();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPathActionPerformed
        choosePath();
    }//GEN-LAST:event_jButtonPathActionPerformed

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        saveFile();
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewActionPerformed
        newFile();
    }//GEN-LAST:event_jMenuItemNewActionPerformed

    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenActionPerformed
        openFile();
    }//GEN-LAST:event_jMenuItemOpenActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        dispose();
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jMenuItemSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveAsActionPerformed
        saveFileAs(saveDialog());
    }//GEN-LAST:event_jMenuItemSaveAsActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonIcon;
    private javax.swing.JButton jButtonPath;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JCheckBox jCheckBoxStartupNotify;
    private javax.swing.JCheckBox jCheckBoxTerminal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemSaveAs;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTextFieldCommand;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
