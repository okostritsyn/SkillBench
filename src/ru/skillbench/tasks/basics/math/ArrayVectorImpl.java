package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

public class ArrayVectorImpl implements ArrayVector, Cloneable {
    /**
     * Create array of tasks.
     */
    private double[] arrayElem;

    public ArrayVectorImpl(){
        arrayElem = new double[0];
    }

    @Override
    public void set(double... elements) {
       arrayElem = new double[elements.length];
       System.arraycopy(elements , 0, arrayElem, 0, arrayElem.length);
    }

    @Override
    public double[] get() {
        return arrayElem;
    }

    @Override
    public ArrayVector clone() {
        ArrayVector cloneArray = new ArrayVectorImpl();
        cloneArray.set(get());
        return cloneArray;
    }

    @Override
    public int getSize() {
        return arrayElem.length;
    }

    @Override
    public void set(int index, double value) {
        if(index < 0)
            return;
        else if(index < getSize())
            arrayElem[index] = value;
        else{
            arrayElem = Arrays.copyOf(arrayElem, index+1);
            arrayElem[index] = value;
        }
    }

    @Override
    public double get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= getSize()){
            throw new ArrayIndexOutOfBoundsException("Index Out Of Bounds");
        }
        return arrayElem[index];
    }

    @Override
    public double getMax() {
        double maxElem = get(0);
        for (int i = 0;i<getSize();i++){
            maxElem = Math.max(maxElem, get(i));
        }
        return maxElem;
    }

    @Override
    public double getMin() {
        double minElem = get(0);
        for (int i = 0;i<getSize();i++){
            minElem = Math.min(minElem, get(i));
        }
        return minElem;
    }

    @Override
    public void sortAscending() {
        for(int i = getSize()-1 ; i > 0 ; i--){
            for(int j = 0; j < i; j++){
            if( get(j) > get(j+1)){
                double tmp = get(j);
                set(j,get(j+1));
                set(j+1,tmp);
                }
            }
        }
    }

    @Override
    public void mult(double factor) {
        for (int i = 0;i<getSize();i++){
            set(i,get(i)*factor);
        }
    }

    @Override
    public ArrayVector sum(ArrayVector anotherVector) {
        for (int i = 0;i<getSize();i++){
            if (i>anotherVector.getSize()){
                break;
            }
            set(i,get(i)+anotherVector.get(i));
        }
        return this;
    }

    @Override
    public double scalarMult(ArrayVector anotherVector) {
        double scMult = 0;
        for (int i = 0;i<getSize();i++){
            if (i>anotherVector.getSize()){
                break;
            }
            scMult += get(i)*anotherVector.get(i);
        }
        return scMult;
    }

    @Override
    public double getNorm() {
        return Math.sqrt(scalarMult(this));
    }
}
