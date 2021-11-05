/**
 * @(#)JDialogProject.java 2019/12/03
 *
 * ICE Team free software group
 *
 * This file is part of C64 Java Software Emulator.
 * See README for copyright notice.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307  USA.
 */
package sw_emulator.swing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.DatatypeConverter;
import sw_emulator.software.memory.MemoryFlags;
import sw_emulator.swing.main.FileManager;
import sw_emulator.swing.main.Project;
import sw_emulator.swing.main.Relocate;
import sw_emulator.swing.main.TargetType;

/**
 * Dialog for project
 * 
 * @author ice
 */
public class JProjectDialog extends javax.swing.JDialog {
    /** File chooser */
    JFileChooser fileChooser=new JFileChooser();    
    
     /** File chooser for memory flags */
    JFileChooser memFileChooser=new JFileChooser();    
    
    /** The project to use (create an emty one not used) */
    Project project=new Project();
    
    /** Dialog for constants table */
    JConstantDialog jConstantDialog=new JConstantDialog(null, true);
    
    /**
     * Creates new form JDialogProject
     */
    public JProjectDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Shared.framesList.add(this);
        Shared.framesList.add(fileChooser);
        Shared.framesList.add(memFileChooser);
        
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PSID/RSID tune", "sid"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MUS tune", "mus"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Multi PRG C64 program", "mpr"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PRG C64 program", "prg", "bin"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CRT C64 cartridge", "crt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("VSF Vice snapshot file", "vsf"));
    }
    
  /**
   * Convert a unsigned short (containing in a int) to Exe upper case 4 chars
   *
   * @param value the short value to convert
   * @return the exe string rapresentation of byte
   */
  protected String ShortToExe(int value) {
    int tmp=value;

    if (value<0) return "????";
    
    String ret=Integer.toHexString(tmp);
    int len=ret.length();
    switch (len) {
      case 1:
        ret="000"+ret;
        break;
     case 2:
        ret="00"+ret;
        break;
     case 3:
        ret="0"+ret;
        break;
    }
    return ret.toUpperCase(Locale.ENGLISH);
  } 
    
    /**
     * Get relocate table as string description
     * 
     * @return the string description
     */
    private String getRelocateDesc() {
      if (project==null || project.relocates==null) return "";
      
      String res="";
      for (Relocate relocate:project.relocates) {
        res+=ShortToExe(relocate.fromStart)+":"+ShortToExe(relocate.fromEnd)+" => "+
             ShortToExe(relocate.toStart)+":"+ShortToExe(relocate.toEnd)+"\n";
      }
      
      return res;
    }
    
    /**
     * Set up the dialog with the project to use 
     * 
     * @param project the project to use 
     */
    public void setUp(Project project) {
      this.project=project;  
      jConstantDialog.setUp(project.constant);
      
      jTextFieldProjectName.setText(project.name);
      jTextFieldInputFile.setText(project.file);
      if (project.description!=null) jTextAreaDescr.setText(project.description);
      else jTextAreaDescr.setText("");
      jSpinnerCRT.setValue(project.chip);
      if (project.fileType!=null) {
        switch (project.fileType) {
          case PRG:
            jRadioButtonPRG.setSelected(true);
            break;
          case SID:
            jRadioButtonSID.setSelected(true);
            break;
          case MUS:
            jRadioButtonMUS.setSelected(true);
            break;
          case MPR:
            jRadioButtonMPR.setSelected(true);
            break;    
          case CRT:
            jRadioButtonCRT.setSelected(true);
            break;   
          case VSF:
            jRadioButtonVSF.setSelected(true);
            break;     
        }
      } else {
          jRadioButtonPRG.setSelected(false);
          jRadioButtonSID.setSelected(false);
          jRadioButtonMUS.setSelected(false);
          jRadioButtonMPR.setSelected(false);  
          jRadioButtonCRT.setSelected(false);  
          jRadioButtonVSF.setSelected(false);  
        }
      if (project.targetType!=null) {
        switch (project.targetType) {
          case C64:
            jRadioButtonC64.setSelected(true);
            break;
          case C1541:
            jRadioButtonC1541.setSelected(true);
            break;
          case C128:  
            jRadioButtonC128.setSelected(true);
            break;  
          case VIC20:
            jRadioButtonVic20.setSelected(true);
            break;   
          case PLUS4:
            jRadioButtonPlus4.setSelected(true);
            break;        
        }          
      } else {
          jRadioButtonC64.setSelected(true);       
          jRadioButtonC1541.setSelected(false);
          jRadioButtonC128.setSelected(false);
          jRadioButtonVic20.setSelected(false);
          jRadioButtonPlus4.setSelected(false);
        }
      jTextAreaRelocate.setText(getRelocateDesc());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupFileType = new javax.swing.ButtonGroup();
        buttonGroupTarget = new javax.swing.ButtonGroup();
        jPanelCenter = new javax.swing.JPanel();
        jLabelProjectName = new javax.swing.JLabel();
        jTextFieldProjectName = new javax.swing.JTextField();
        jLabelInputFile = new javax.swing.JLabel();
        jTextFieldInputFile = new javax.swing.JTextField();
        jButtonSelect = new javax.swing.JButton();
        jLabelFileType = new javax.swing.JLabel();
        jRadioButtonSID = new javax.swing.JRadioButton();
        jRadioButtonMUS = new javax.swing.JRadioButton();
        jRadioButtonPRG = new javax.swing.JRadioButton();
        jLabelFileDes = new javax.swing.JLabel();
        jScrollPaneDescr = new javax.swing.JScrollPane();
        jTextAreaDescr = new javax.swing.JTextArea();
        jLabelSidLd = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JButton();
        jButtonAddNext = new javax.swing.JButton();
        jButtonInit = new javax.swing.JButton();
        jRadioButtonMPR = new javax.swing.JRadioButton();
        jLabelFileTarget = new javax.swing.JLabel();
        jRadioButtonC64 = new javax.swing.JRadioButton();
        jRadioButtonC1541 = new javax.swing.JRadioButton();
        jRadioButtonC128 = new javax.swing.JRadioButton();
        jRadioButtonVic20 = new javax.swing.JRadioButton();
        jRadioButtonPlus4 = new javax.swing.JRadioButton();
        jRadioButtonCRT = new javax.swing.JRadioButton();
        jSpinnerCRT = new javax.swing.JSpinner();
        jLabelConstant = new javax.swing.JLabel();
        jButtonEdit = new javax.swing.JButton();
        jRadioButtonVSF = new javax.swing.JRadioButton();
        jLabelRelocate = new javax.swing.JLabel();
        jButtonRelocateAdd = new javax.swing.JButton();
        jScrollPaneRelocate = new javax.swing.JScrollPane();
        jTextAreaRelocate = new javax.swing.JTextArea();
        jPanelDn = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Project");
        setResizable(false);

        jPanelCenter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelProjectName.setText("Project Name:");

        jLabelInputFile.setText("File to disassemblate:");

        jButtonSelect.setText("Select");
        jButtonSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectActionPerformed(evt);
            }
        });

        jLabelFileType.setText("File Type:");

        buttonGroupFileType.add(jRadioButtonSID);
        jRadioButtonSID.setText("SID");
        jRadioButtonSID.setEnabled(false);

        buttonGroupFileType.add(jRadioButtonMUS);
        jRadioButtonMUS.setText("MUS");
        jRadioButtonMUS.setEnabled(false);

        buttonGroupFileType.add(jRadioButtonPRG);
        jRadioButtonPRG.setText("PRG");
        jRadioButtonPRG.setEnabled(false);

        jLabelFileDes.setText("File description:");

        jTextAreaDescr.setEditable(false);
        jTextAreaDescr.setColumns(20);
        jTextAreaDescr.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextAreaDescr.setRows(5);
        jScrollPaneDescr.setViewportView(jTextAreaDescr);

        jLabelSidLd.setText("SIDLD memory flag: ");

        jButtonClear.setText("Clear");
        jButtonClear.setToolTipText("Clear the memory flag as of all undefined");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonAddNext.setText("Add next");
        jButtonAddNext.setToolTipText("Add next SIDLD file to memory flag");
        jButtonAddNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddNextActionPerformed(evt);
            }
        });

        jButtonInit.setText("Init");
        jButtonInit.setToolTipText("Init the memory flag as all of executable code");
        jButtonInit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInitActionPerformed(evt);
            }
        });

        buttonGroupFileType.add(jRadioButtonMPR);
        jRadioButtonMPR.setText("MPR");
        jRadioButtonMPR.setEnabled(false);

        jLabelFileTarget.setText("Target machine:");

        buttonGroupTarget.add(jRadioButtonC64);
        jRadioButtonC64.setSelected(true);
        jRadioButtonC64.setText("C64");
        jRadioButtonC64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonC64ActionPerformed(evt);
            }
        });

        buttonGroupTarget.add(jRadioButtonC1541);
        jRadioButtonC1541.setText("C1541");
        jRadioButtonC1541.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonC1541ActionPerformed(evt);
            }
        });

        buttonGroupTarget.add(jRadioButtonC128);
        jRadioButtonC128.setText("C128");
        jRadioButtonC128.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonC128ActionPerformed(evt);
            }
        });

        buttonGroupTarget.add(jRadioButtonVic20);
        jRadioButtonVic20.setText("Vic20");
        jRadioButtonVic20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonVic20ActionPerformed(evt);
            }
        });

        buttonGroupTarget.add(jRadioButtonPlus4);
        jRadioButtonPlus4.setText("Plus4");
        jRadioButtonPlus4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonPlus4ActionPerformed(evt);
            }
        });

        buttonGroupFileType.add(jRadioButtonCRT);
        jRadioButtonCRT.setText("CRT");
        jRadioButtonCRT.setEnabled(false);

        jSpinnerCRT.setModel(new javax.swing.SpinnerNumberModel(0, 0, 255, 1));
        jSpinnerCRT.setEnabled(false);
        jSpinnerCRT.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerCRTStateChanged(evt);
            }
        });

        jLabelConstant.setText("Constants table:");

        jButtonEdit.setText("Edit");
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });

        buttonGroupFileType.add(jRadioButtonVSF);
        jRadioButtonVSF.setText("VSF");
        jRadioButtonVSF.setEnabled(false);

        jLabelRelocate.setText("Relocate table:");

        jButtonRelocateAdd.setText("Add");
        jButtonRelocateAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelocateAddActionPerformed(evt);
            }
        });

        jTextAreaRelocate.setEditable(false);
        jTextAreaRelocate.setColumns(20);
        jTextAreaRelocate.setRows(5);
        jScrollPaneRelocate.setViewportView(jTextAreaRelocate);

        javax.swing.GroupLayout jPanelCenterLayout = new javax.swing.GroupLayout(jPanelCenter);
        jPanelCenter.setLayout(jPanelCenterLayout);
        jPanelCenterLayout.setHorizontalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneDescr)
                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabelInputFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelProjectName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabelFileType, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCenterLayout.createSequentialGroup()
                                .addComponent(jRadioButtonSID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonMUS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonPRG)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonMPR)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonCRT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinnerCRT, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonVSF)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanelCenterLayout.createSequentialGroup()
                                .addComponent(jTextFieldInputFile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSelect))
                            .addComponent(jTextFieldProjectName)))
                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFileDes)
                            .addGroup(jPanelCenterLayout.createSequentialGroup()
                                .addComponent(jLabelFileTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonC64)
                                .addGap(6, 6, 6)
                                .addComponent(jRadioButtonC1541)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonC128)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonVic20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonPlus4)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelConstant, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelSidLd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelRelocate, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonRelocateAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCenterLayout.createSequentialGroup()
                                .addComponent(jButtonInit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonAddNext)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPaneRelocate))))
                .addContainerGap())
        );
        jPanelCenterLayout.setVerticalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelProjectName)
                    .addComponent(jTextFieldProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelInputFile)
                    .addComponent(jTextFieldInputFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSelect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButtonSID)
                        .addComponent(jRadioButtonMUS)
                        .addComponent(jRadioButtonPRG)
                        .addComponent(jRadioButtonMPR)
                        .addComponent(jRadioButtonCRT)
                        .addComponent(jSpinnerCRT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButtonVSF))
                    .addComponent(jLabelFileType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFileTarget)
                    .addComponent(jRadioButtonC64)
                    .addComponent(jRadioButtonC1541)
                    .addComponent(jRadioButtonC128)
                    .addComponent(jRadioButtonVic20)
                    .addComponent(jRadioButtonPlus4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelFileDes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneDescr, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClear)
                    .addComponent(jLabelSidLd)
                    .addComponent(jButtonAddNext)
                    .addComponent(jButtonInit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConstant)
                    .addComponent(jButtonEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonRelocateAdd)
                            .addComponent(jLabelRelocate)))
                    .addComponent(jScrollPaneRelocate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

        jPanelDn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jPanelDn.add(jButtonClose);

        getContentPane().add(jPanelDn, java.awt.BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectActionPerformed
      int retValue=fileChooser.showOpenDialog(this);
      
      if (retValue==JFileChooser.APPROVE_OPTION) {
        project.file=fileChooser.getSelectedFile().getAbsolutePath();
        jTextFieldInputFile.setText(project.file);        
        
        // go to read the file
        try {
          project.setData(FileManager.instance.readFile(project.file));
          jTextAreaDescr.setText(project.description);
          switch (project.fileType) {
            case CRT:
              jRadioButtonCRT.setSelected(true);
              jRadioButtonC64.setSelected(true);  
              project.targetType=TargetType.C64;
              jRadioButtonC1541.setEnabled(true);
              jRadioButtonC128.setEnabled(true);
              jRadioButtonVic20.setEnabled(true);
              jRadioButtonPlus4.setEnabled(true);
              jSpinnerCRT.setEnabled(true);
              break;      
            case SID:   
              jRadioButtonSID.setSelected(true);
              jRadioButtonC64.setSelected(true); 
              project.targetType=TargetType.C64;
              jRadioButtonC1541.setEnabled(false);
              jRadioButtonC128.setEnabled(false);
              jRadioButtonVic20.setEnabled(false);
              jRadioButtonPlus4.setEnabled(false);
              jSpinnerCRT.setEnabled(false);
              break;
            case MUS:
              jRadioButtonMUS.setSelected(true);
              project.targetType=TargetType.C64;
              jRadioButtonC64.setEnabled(false);
              jRadioButtonC1541.setEnabled(false);
              jRadioButtonC128.setEnabled(false);
              jRadioButtonVic20.setEnabled(false);
              jRadioButtonPlus4.setEnabled(false);     
              jSpinnerCRT.setEnabled(false);
              break;
            case PRG:
              jRadioButtonPRG.setSelected(true);
              jRadioButtonC64.setEnabled(true);
              project.targetType=TargetType.C64;
              jRadioButtonC1541.setEnabled(true);
              jRadioButtonC128.setEnabled(true);
              jRadioButtonVic20.setEnabled(true);
              jRadioButtonPlus4.setEnabled(true); 
              jSpinnerCRT.setEnabled(false);
              break;  
            case MPR:
              jRadioButtonMPR.setSelected(true);
              jRadioButtonC64.setEnabled(true);
              project.targetType=TargetType.C64;
              jRadioButtonC1541.setEnabled(true);
              jRadioButtonC128.setEnabled(true);
              jRadioButtonVic20.setEnabled(true);
              jRadioButtonPlus4.setEnabled(true);  
              jSpinnerCRT.setEnabled(false);
              break;      
            case VSF:
              jRadioButtonVSF.setSelected(true);
              jRadioButtonC64.setEnabled(true);
              project.targetType=TargetType.C64;
              jRadioButtonC1541.setEnabled(true);
              jRadioButtonC128.setEnabled(true);
              jRadioButtonVic20.setEnabled(true);
              jRadioButtonPlus4.setEnabled(true);  
              jSpinnerCRT.setEnabled(false);
              break;  
            case UND:
              jRadioButtonSID.setSelected(false);  
              jRadioButtonMUS.setSelected(false);  
              jRadioButtonPRG.setSelected(false); 
              jRadioButtonMPR.setSelected(false);
              jRadioButtonC64.setEnabled(true);
              project.targetType=TargetType.C64;
              jRadioButtonC1541.setEnabled(true);
              jRadioButtonC128.setEnabled(true);
              jRadioButtonVic20.setEnabled(true);
              jRadioButtonPlus4.setEnabled(true);   
              jSpinnerCRT.setEnabled(false);
              break;
          }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading the file.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        
        project.fileType=project.fileType.getFileType(project.inB);
        jTextAreaRelocate.setText(getRelocateDesc());        
      } 
    }//GEN-LAST:event_jButtonSelectActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
       project.name=jTextFieldProjectName.getText();
       
       if (project.file==null || "".equals(project.file)) {
         if (JOptionPane.showConfirmDialog(this, "No file inserted. Closing will erase all in project. Do you want to close anywere?", "Warning", JOptionPane.WARNING_MESSAGE)==JOptionPane.OK_OPTION) setVisible(false);
         else return;
       }
        
       setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
      // clear memory flags
      project.memoryFlags=new byte[0xffff];        
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonAddNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddNextActionPerformed
      if (project.memoryFlags==null) project.memoryFlags=new byte[0xffff]; 
          
      int retValue=memFileChooser.showOpenDialog(this);
      
      if (retValue==JFileChooser.APPROVE_OPTION) {
        String[] file=new String[1];
        file[1]=memFileChooser.getSelectedFile().getAbsolutePath();
        MemoryFlags memoryFlags=new MemoryFlags(file);
        project.memoryFlags=memoryFlags.orMemory(memoryFlags.getMemoryState(0, 0xFFFF), project.memoryFlags);       
      }  
    }//GEN-LAST:event_jButtonAddNextActionPerformed

    private void jButtonInitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInitActionPerformed
      // ser as executable
      MemoryFlags memoryFlags=new MemoryFlags((String[])null);
      project.memoryFlags=memoryFlags.getMemoryState(0, 0xFFFF);
    }//GEN-LAST:event_jButtonInitActionPerformed

    private void jRadioButtonC64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonC64ActionPerformed
      project.targetType=TargetType.C64;
    }//GEN-LAST:event_jRadioButtonC64ActionPerformed

    private void jRadioButtonC1541ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonC1541ActionPerformed
      project.targetType=TargetType.C1541;
    }//GEN-LAST:event_jRadioButtonC1541ActionPerformed

    private void jRadioButtonC128ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonC128ActionPerformed
      project.targetType=TargetType.C128;  
    }//GEN-LAST:event_jRadioButtonC128ActionPerformed

    private void jRadioButtonVic20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonVic20ActionPerformed
      project.targetType=TargetType.VIC20;
    }//GEN-LAST:event_jRadioButtonVic20ActionPerformed

    private void jRadioButtonPlus4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonPlus4ActionPerformed
      project.targetType=TargetType.PLUS4;
    }//GEN-LAST:event_jRadioButtonPlus4ActionPerformed

    private void jSpinnerCRTStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerCRTStateChanged
      project.chip=(Integer)jSpinnerCRT.getValue();
    }//GEN-LAST:event_jSpinnerCRTStateChanged

    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed
      jConstantDialog.setVisible(true);
    }//GEN-LAST:event_jButtonEditActionPerformed

    private void jButtonRelocateAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelocateAddActionPerformed
      Relocate relocate=new Relocate();
      
      relocate.fromStart=Integer.parseInt(JOptionPane.showInputDialog("Starting address (in hex) from where to copy"),16);
      relocate.fromEnd=Integer.parseInt(JOptionPane.showInputDialog("Ending address (in hex) from where to copy"),16);
      relocate.toStart=Integer.parseInt(JOptionPane.showInputDialog("Starting address (in hex) to where to copy"), 16);
      relocate.toEnd=Integer.parseInt(JOptionPane.showInputDialog("Ending address (in hex) to where to copy"), 16);
      
      if (!relocate.isValidRange()) {
        JOptionPane.showMessageDialog(this, "Invalid range of addresses", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
      
      int size=0;
      if (project.relocates!=null) size=project.relocates.length;
      
      // copy the value in the list
      Relocate[] relocates2=new Relocate[size+1];
      if (size>0) System.arraycopy(project.relocates, 0, relocates2, 0, project.relocates.length);
      relocates2[size]=relocate;
            
      project.relocates=relocates2;
      
      
      jTextAreaRelocate.setText(getRelocateDesc());
    }//GEN-LAST:event_jButtonRelocateAddActionPerformed
    
    /**
     * @param args the command line arguments
     */
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JProjectDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JProjectDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JProjectDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JProjectDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JProjectDialog dialog = new JProjectDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupFileType;
    private javax.swing.ButtonGroup buttonGroupTarget;
    private javax.swing.JButton jButtonAddNext;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JButton jButtonInit;
    private javax.swing.JButton jButtonRelocateAdd;
    private javax.swing.JButton jButtonSelect;
    private javax.swing.JLabel jLabelConstant;
    private javax.swing.JLabel jLabelFileDes;
    private javax.swing.JLabel jLabelFileTarget;
    private javax.swing.JLabel jLabelFileType;
    private javax.swing.JLabel jLabelInputFile;
    private javax.swing.JLabel jLabelProjectName;
    private javax.swing.JLabel jLabelRelocate;
    private javax.swing.JLabel jLabelSidLd;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelDn;
    private javax.swing.JRadioButton jRadioButtonC128;
    private javax.swing.JRadioButton jRadioButtonC1541;
    private javax.swing.JRadioButton jRadioButtonC64;
    private javax.swing.JRadioButton jRadioButtonCRT;
    private javax.swing.JRadioButton jRadioButtonMPR;
    private javax.swing.JRadioButton jRadioButtonMUS;
    private javax.swing.JRadioButton jRadioButtonPRG;
    private javax.swing.JRadioButton jRadioButtonPlus4;
    private javax.swing.JRadioButton jRadioButtonSID;
    private javax.swing.JRadioButton jRadioButtonVSF;
    private javax.swing.JRadioButton jRadioButtonVic20;
    private javax.swing.JScrollPane jScrollPaneDescr;
    private javax.swing.JScrollPane jScrollPaneRelocate;
    private javax.swing.JSpinner jSpinnerCRT;
    private javax.swing.JTextArea jTextAreaDescr;
    private javax.swing.JTextArea jTextAreaRelocate;
    private javax.swing.JTextField jTextFieldInputFile;
    private javax.swing.JTextField jTextFieldProjectName;
    // End of variables declaration//GEN-END:variables
}
