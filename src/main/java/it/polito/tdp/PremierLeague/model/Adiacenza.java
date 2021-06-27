package it.polito.tdp.PremierLeague.model;

public class Adiacenza implements Comparable<Adiacenza> {
Integer m1;
Integer m2;
Integer peso;

public Adiacenza(Integer m1, Integer m2, int peso) {
	this.m1 = m1;
	this.m2 = m2;
	this.peso = peso;
}
public Integer getM1() {
	return m1;
}
public void setM1(Integer m1) {
	this.m1 = m1;
}
public Integer getM2() {
	return m2;
}
public void setM2(Integer m2) {
	this.m2 = m2;
}
public int getPeso() {
	return peso;
}
public void setPeso(int peso) {
	this.peso = peso;
}
@Override
public int compareTo(Adiacenza o) {
	// TODO Auto-generated method stub
	return -this.peso.compareTo(o.peso);
}


}
