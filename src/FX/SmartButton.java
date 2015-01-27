/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FX;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

/**
 *
 * @author LuLu
 */
public class SmartButton extends Label {
    private ImageView _press;
    private ImageView _release;
    private AudioClip _presssound;
    private AudioClip _releasesound;

    public void setPressedNode(ImageView node) {
        _press = node;
    }

    public void setReleasedNode(ImageView node) {
        _release = node;
    }
    
    public void setPressedSound(AudioClip presssound){
        _presssound = presssound;
    }
    
    public void setReleaseSound(AudioClip releasesound){
        _releasesound = releasesound;
    }
    
    public void switchState(ButtonState state){
        switch(state){
            case PRESSED:{
                setGraphic(_press);
                _presssound.play();
                break;
            }
            case RELEASED:{
                setGraphic(_release);
                _releasesound.play();
                break;
            }
        }
    }
    public enum ButtonState{
        PRESSED, RELEASED
    }
}
