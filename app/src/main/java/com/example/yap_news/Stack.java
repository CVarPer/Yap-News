package com.example.yap_news;

public class Stack<T>{
    private int top;
    private static final int N = 1000000;
    private final T[] StackArray;

    public Stack(){
        this(N);
    }

    public Stack(int n){
        top = 0;
        StackArray = (T[]) new Object[n];
    }

    public boolean empty(){
        return top <= 0;
    }

    public boolean full(){
        return top >= StackArray.length;
    }

    public int getTop(){return top;}

    public T pop(){
        if(empty())
            throw new RuntimeException("Stack is Empty");
        top--;
        return StackArray[top];
    }
    public T peek(){
        if(empty())
            throw new RuntimeException("Stack is Empty");
        return StackArray[--top];
    }
    public void push(T item){
        if(full())
            throw new RuntimeException("Stack is full");
        StackArray[top] = item;
        top++;
    }
    public T[] getStackArray(){
        return StackArray;
    }
}
