/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.install.exeception;

/**
 *
 * @author whoana
 */
public class TaskException extends Exception{
    
    public TaskException(String msg){
        super(msg);
    }
    
    public TaskException(Exception e){
        super(e);
    }
    
    public TaskException(String msg, Exception e){
        super(msg, e);
    }
    
}
