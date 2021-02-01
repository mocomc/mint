/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author whoana
 */
public class Test {
    public static void main(String args[]){
        try{
            String value = "{\"label\":\"lb-11\",\"detailView\":\"{detailView:'Y'}\"}";
            System.out.println(value.replaceAll("\'","\'\'"));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
