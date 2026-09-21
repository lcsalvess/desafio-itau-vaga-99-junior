package com.lucas.desafioitauvaga99junior.entity;

public class Estatistica {
    private long count;
    private Double sum;
    private Double avg;
    private Double min;
    private Double max;

    public Estatistica() {
    }

    public Estatistica(long count, Double sum, Double avg, Double min, Double max) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
    }

    public long getCount() {
        return count;
    }

    public Double getSum() {
        return sum;
    }

    public Double getAvg() {
        return avg;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }
}
