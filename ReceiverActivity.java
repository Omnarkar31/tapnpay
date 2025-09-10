 package com.example.tappnpay;
 import android.content.Intent;
 import android.nfc.NdefMessage;
 import android.nfc.NfcAdapter;
 import android.nfc.NfcEvent;
 import android.nfc.tech.Ndef;
 import android.os.Bundle;
 import android.widget.Button;
 import android.widget.TextView;
 import android.widget.Toast;
 import androidx.appcompat.app.AppCompatActivity;
 public class ReceiverActivity extends AppCompatActivity {
 private TextView tvReceiverName, tvReceiverBalance, tvLastPayer,
 tvLastAmount;
 private Button btnBack;
 private BalanceStore balanceStore;
 private final String receiverName = "ReceiverPhone"; // settable in real app
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 
super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_receiver);
 tvReceiverName = findViewById(R.id.tvReceiverName);
 tvReceiverBalance = findViewById(R.id.tvReceiverBalance);
 tvLastPayer = findViewById(R.id.tvLastPayer);
 tvLastAmount = findViewById(R.id.tvLastAmount);
 btnBack = findViewById(R.id.btnBack);
 balanceStore = new BalanceStore(this);
 tvReceiverName.setText(receiverName);
 updateBalUI();
 btnBack.setOnClickListener(v-> finish());
 // handle intent if opened from NFC
 Intent intent = getIntent();
 if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
 NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
 processNfcIntent(intent);
 }
 }
 @Override
 protected void onNewIntent(Intent intent) {
 super.onNewIntent(intent);
 if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
 processNfcIntent(intent);
 }
 }
 private void processNfcIntent(Intent intent) {
 NdefMessage[] messages = null;
 if (intent.hasExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)) {
 messages = (NdefMessage[])
 intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
 }
 if (messages == null || messages.length == 0) return;
 String payload = NfcHelper.parsePayload(messages[0]);
 if (payload == null) return;
 // expected format: PAY|amount|payerName|payerCardId
 if (payload.startsWith("PAY|")) {
 String[] parts = payload.split("\\|", 4);
 if (parts.length >= 4) {
 String amountStr = parts[1];
 String payerName = parts[2];
 
String payerCard = parts[3];
 double amount = 0;
 try { amount = Double.parseDouble(amountStr); } catch
 (Exception e) { }
 // update balances
 balanceStore.addBalance("receiver", amount);
 balanceStore.subtractBalance("payer", amount);
 tvLastPayer.setText(payerName + " (" + payerCard + ")");
 tvLastAmount.setText("₹ " + String.format("%.2f", amount));
 updateBalUI();
 Toast.makeText(this, "Received ₹" + amountStr + " from " +
 payerName, Toast.LENGTH_LONG).show();
 }
 }
 }
 private void updateBalUI() {
 double bal = balanceStore.getBalance("receiver");
 tvReceiverBalance.setText(String.format("₹ %.2f", bal));
 }
 }
