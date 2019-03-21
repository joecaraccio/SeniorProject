package com.sandbox.sandbox;


//contains up and down buttons to allow incriment and decrement
public class AdjustorButton {
    private float Value;
    private float IncrimentSize;
    private int Coordinate; //x,y,z etc.
    //x = 0, y = 1, z = 2

    public AdjustorButton(float is, Node n, String cord){

    }

    public void Up(){
        Value += IncrimentSize;
    }

    public void Down(){
        Value -= IncrimentSize;
    }

    //Adjust node
    public void Adjust(float delta){
        if(cord == 0){
            
        }
    }




}