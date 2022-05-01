/*
 * J16ColorPanel.java
 *
 * Created on 1 luglio 2004, 22.24
 */

package sw_emulator.swing;

import java.awt.*;
import java.awt.event.*;

/**
 * Show the standard C64 16 color palette
 *
 * This code manage even the primary color of MCM text mode
 *
 * @author  ice
 */
public class J16ColorPanel extends javax.swing.JPanel {
    
  private Dimension swatchSize=new Dimension(15,15);
  private Dimension gap=new Dimension (1,1);
  
  /** true if the char is primary */
  private boolean primary=false;
  
  /** The color index it is now selected*/
  private int selectedIndex=0;
  
  /** C64 color palette*/
  public static Color[] c64Color={
      new Color(0,0,0),
      new Color(255,255,255),
      new Color(0xe0,0x40,0x40),
      new Color(0x60,0xff,0xff),
      new Color(0xe0,0x60,0xe0),
      new Color(0x40,0xe0,0x40),
      new Color(0x40,0x40,0xe0),      
      new Color(0xff,0xff,0x40),
      new Color(0xe0,0xa0,0x40),
      new Color(0x9c,0x74,0x48),
      new Color(0xff,0xa0,0xa0),
      new Color(0x54,0x54,0x54),
      new Color(0x88,0x88,0x88),      
      new Color(0xa0,0xff,0xa0),
      new Color(0xa0,0xa0,0xff),
      new Color(0xc0,0xc0,0xc0)
  };
  
  /** Color names for c64 palette */
  private String[] colorName={
     "Black",
     "White",
     "Red",
     "Cyan",
     "Pink",
     "Green",
     "Blue",
     "Yellow",
     "Orange",
     "Brown",
     "Light red",
     "Dark grey",
     "Medium grey",
     "Light green",
     "Light blue",
     "Light gray"
  };
    
  /** Creates new form J8ColorPanel */
  public J16ColorPanel() {
    initComponents();
    setToolTipText(""); // register for events
    setOpaque(true);
    //setBackground(Color.white);
    setRequestFocusEnabled(false);    
  }
    
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
      // Add your handling code here:
      selectedIndex=getIndexForLocation(evt.getX(), evt.getY());  
      repaint();
    }//GEN-LAST:event_formMousePressed

  public void paintComponent(java.awt.Graphics g) {
    int w=getWidth();
    int h=getHeight();
    swatchSize.width=w/16;
    swatchSize.height=h;
    
    g.setColor(getBackground());
    g.fillRect(0,0,getWidth(), getHeight());
    for (int index = 0; index < 16; index++) {
      if (!(index>7 && primary)) {        
        g.setColor( getColorForCell(index) ); 
        int x = index * (swatchSize.width + gap.width);
        int y =  gap.height;
        g.fillRect( x, y, swatchSize.width, swatchSize.height);
        g.setColor(Color.black);
        g.drawLine( x+swatchSize.width-1, y, x+swatchSize.width-1, y+swatchSize.height-1);
        g.drawLine( x, y+swatchSize.height-1, x+swatchSize.width-1, y+swatchSize.height-1);
        if (index==selectedIndex) {
          Color c=new Color(255-c64Color[index].getRed(),255-c64Color[index].getGreen(),255-c64Color[index].getBlue());
          g.setColor(c);
          g.drawOval(x+swatchSize.width/6, y+swatchSize.height/4, h/2, h/2);
        }
      }    
    }
  }        
 
  /**
   *  Get the color fot the given index
   * 
   * @param index the index of color
   * @return the color as rgb value
   */ 
  private java.awt.Color getColorForCell(int index) {
    return c64Color[index];  
  }
 
  /**
   * Get the color index for the given location
   *
   * @param x x location to check
   * @param y y location to check
   * @return the color index at the given location
   */
  public int getIndexForLocation( int x, int y ) {
    int index= x / (swatchSize.width + gap.width);  
    if (index>15) return 0;
    if (primary && index>7) return 0;
    return index;
  }
 
  public String getToolTipText(MouseEvent e) {
    return colorName[getIndexForLocation(e.getX(), e.getY())];  
  }
 
  public Dimension getPreferredSize() {
    int x = 16 * (swatchSize.width + gap.width) -1;
    int y =  2*(swatchSize.height + gap.height) -1;
    return new Dimension( x, y );
  } 
  
  /**
   * Get the color index selected
   *
   * @return the selected index
   */
  public int getSelectedIndex() {
    return selectedIndex;
  }
  
  /**
   * Set the selectd index of color
   *
   * @param index the index to set
   */
  public void setSelectedIndex(int index) {
    if (index<0 || index>15) return;
    selectedIndex=index;
    repaint();
  }
  
  /**
   * Get the selected color
   *
   * @return the selected color
   */
  public Color getSelectedColor() {
    return c64Color[selectedIndex];
  }  
  
  /**
   * Set the selected color
   * 
   * @param color the color to select
   */
  public void setSelectedColor(Color color) {
    for (int i=0; i<16; i++) {
      if (c64Color[i].equals(color)) {
        selectedIndex=i;
      }
    }
    repaint();
  }

  /**
   * Return the index of the given rgb color
   *
   * @param color the color
   * @return the index of this color
   */
  public static int getIndexForColor(Color color) {
    for (int i=0; i<16; i++) {
      if (c64Color[i].equals(color)) {
        return i;
      }
    }
    return 0;
  }
  
  /**
   * Get the rgb color for the given index
   *
   * @param index the index of the color
   * @return the rgb color
   */
  public static Color getColorForIndex(int index) {
    return c64Color[index];
  }
  
  /**
   * Set this as primary color
   *
   * @param primary if this is a primary color
   */
  public void setPrimary(boolean primary) {
    this.primary=primary;
  }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
