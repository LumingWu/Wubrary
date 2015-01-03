/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FX;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author LuLu
 */
public class SmartButton extends Label {
    private ImageView _static;
    private ImageView _react;
    
    private int state;
    public SmartButton() {
        
    }

    public void setPressedNode(ImageView node) {
        _react = node;
    }

    public void setReleasedNode(ImageView node) {
        _static = node;
        setGraphic(_static);
        state = 1;
    }
    
    public void flip(){
        switch(state){
            case 1:{
                setGraphic(_react);
                state = 2;
                break;
            }
            case 2:{
                setGraphic(_static);
                state = 1;
                break;
            }
        }
    }
    
}
