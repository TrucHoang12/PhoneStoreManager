/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Button;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author DELL
 */
public class ThemButton extends JButton {

    public ThemButton() {
        this.setText("Thêm");
        this.setIcon(new ImageIcon(this.getClass().getResource("/com/PhoneStoreManager/image/icons8_add_30px.png")));
    }
}
