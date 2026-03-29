package com.example;

public class Konto {
    private String bank;
    private int konto_nummer;
    private int saldo;
    private String ägare;

    public Konto(String bank, int konto_nummer, int saldo, String ägare){
        this.bank = bank;
        this.konto_nummer = konto_nummer;
        this.saldo = saldo;
        this.ägare = ägare;
    }

    public String getÄgare(){
        return ägare;
    }

    public int getSaldo(){
        return saldo;
    }

    public void sätt_in(int belopp){
        saldo += belopp;
        IO.println("Satte in: " + belopp + "kr. Nytt saldo: " + saldo + "kr");
    }

    public boolean ta_ut(int belopp){
        if (belopp > saldo) {
            IO.println("Inte tillräckligt stort saldo!");
            return false;
        } else {
            saldo -= belopp;
            IO.println("Tog ut: " + belopp + "kr. Nytt saldo: " + saldo + "kr");
            return true;
        }
    }
}
