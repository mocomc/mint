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
public class InstallException extends Exception{
    
    public InstallException(String msg){
        super(msg);
    }
    
    public InstallException(Exception e){
        super(e);
    }
    
    public InstallException(String msg, Exception e){
        super(msg, e);
    }
    
}
