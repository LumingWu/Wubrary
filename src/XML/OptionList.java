/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import java.util.ArrayList;

/**
 *
 * @author Luming Wu
 */
public class OptionList implements Comparable{
    private ArrayList<String> _list;
    private String _id;
    
    public OptionList(){
        _list =  new ArrayList<String>();
    }
    public OptionList(String id){
        _id = id;
        _list = new ArrayList<String>();        
    }
    public OptionList(String id, ArrayList<String> list){
        _list = list;
        _id = id;
    }
    public OptionList(ArrayList<String> list){
        _list = list;
    }
    
    public String getID(){
        return _id;
    }
    
    public ArrayList<String> getList(){
        return _list;
    }
    
    public void setID(String id){
        _id = id;
    }
    
    public void setList(ArrayList<String> list){
        _list = list;
    }
    
    public void insert(String item){
        _list.add(item);
    }
    
    public boolean isOptionList(){
        return _list.size() > 1;
    }
    
    public boolean isOption(){
        return _list.size() == 1;
    }
    
    public boolean isEmpty(){
        return _list.isEmpty();
    }

    @Override
    public int compareTo(Object o) {
        return this.getID().compareTo(o.toString());
    }
    
    public String toString(){
        return _id;
    }
            
}
