package com.phone;

public class OperatingFactory {
    public Operation getOperation(String oper){
        if(oper.equals("+")){
            return new Addition();
        }
        else if(oper.equals("-")){
            return new Substration();
        }
        else if(oper.equals("*")){
            return new Multiplication();
        }
        else if(oper.equals("/"))
            return new Division();
        else
            return new OperTest();
    }
}
