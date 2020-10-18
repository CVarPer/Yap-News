package com.example.yap_news;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Stack;

public class StackList<T>{
    private int top;
    private static final int N = 1000;
    private T[] StackListArray;

    public StackList(){
        this(N);
    }

    public StackList(int n){
        top = 0;
        StackListArray = (T[]) new Object[n];
    }

    public boolean empty(){
        return top <= 0;
    }

    public boolean full(){
        return top >= StackListArray.length;
    }

    public int getTop(){return top;}

    public T pop(){
        if(empty())
            throw new RuntimeException("Stack is Empty");
        top--;
        return StackListArray[top];
    }
    public T peek(){
        if(empty())
            throw new RuntimeException("Stack is Empty");
        return StackListArray[--top];
    }
    public void push(T item){
        if(full())
            throw new RuntimeException("Stack is full");
        StackListArray[top] = item;
        top++;
    }
    public T[] getStackListArray(){
        return StackListArray;
    }
}
