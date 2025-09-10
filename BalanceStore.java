 package com.example.tappnpay;
 import android.content.Context;
 import android.content.SharedPreferences;
 public class BalanceStore {
 private static final String PREF = "tapnpay_prefs_v1";
 private SharedPreferences sp;
 public BalanceStore(Context ctx) {
 sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
 // initialize default balances if missing
 if (!sp.contains("balance_payer")) {
 sp.edit().putFloat("balance_payer", 500.0f).apply(); // default ₹500
 }
 if (!sp.contains("balance_receiver")) {
 7
sp.edit().putFloat("balance_receiver", 100.0f).apply(); // default 
₹100
 }
 }
 public double getBalance(String who) {
 if (who.equals("payer")) return sp.getFloat("balance_payer", 0f);
 else return sp.getFloat("balance_receiver", 0f);
 }
 public void addBalance(String who, double amount) {
 if (who.equals("receiver")) {
 float cur = sp.getFloat("balance_receiver", 0f);
 sp.edit().putFloat("balance_receiver", (float)(cur +
 amount)).apply();
 }
 }
 public void subtractBalance(String who, double amount) {
 if (who.equals("payer")) {
 float cur = sp.getFloat("balance_payer", 0f);
 sp.edit().putFloat("balance_payer", (float)(cur- amount)).apply();
 }
 }
 }
