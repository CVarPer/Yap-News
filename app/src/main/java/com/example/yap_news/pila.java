package com.example.yap_news;

public class pila {

    static class Nodo {
        String info;
        Nodo sig;
    }

    private Nodo raiz;
    private int size;

    public void Pila(){
        raiz = null;
        size = 0;
    }

    public void push(String x){
        Nodo nuevo;
        nuevo = new Nodo();
        nuevo.info = x;
        if (raiz==null){
            nuevo.sig = null;
            raiz = nuevo;
        }
        else{
            nuevo.sig = raiz;
            raiz = nuevo;
        }
        size += 1;
    }

    public String pop(){
        if (raiz!=null){
            String informacion = raiz.info;
            raiz = raiz.sig;
            size -= 1;
            return informacion;
        }
        else{
            return null;
        }
    }

    public boolean isEmpty(){
        Nodo reco = raiz;
        return reco == null;
    }

    public String peek(){
        Nodo reco = raiz;
        if(reco == null){
            System.out.print("No puedes sacar elementos de una pila vacía");
        }
        return reco.info;

    }

    public int size(){
        return this.size;
    }
}
