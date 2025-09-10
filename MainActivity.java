 package com.example.tappnpay;
 import android.content.Intent;
 import android.nfc.NdefMessage;
 import android.nfc.NfcAdapter;
 import android.nfc.NfcEvent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.TextView;
 import android.widget.Toast;
 import androidx.appcompat.app.AppCompatActivity;
 public class MainActivity extends AppCompatActivity implements
 NfcAdapter.CreateNdefMessageCallback {
 private NfcAdapter nfcAdapter;
 private EditText edtAmount;
 private TextView tvMyBalance, tvMyName, tvCardId;
 private Button btnSetPayer, btnGoReceiver;
 private String payerName = "Om"; // default — you can let user change
 private String payerCard = "CARD-EDGEFUSION-01";
 // store balance locally (SharedPreferences) as a simple prototype
 private BalanceStore balanceStore;
 
@Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);
 balanceStore = new BalanceStore(this);
 edtAmount = findViewById(R.id.edtAmount);
 tvMyBalance = findViewById(R.id.tvMyBalance);
 tvMyName = findViewById(R.id.tvMyName);
 tvCardId = findViewById(R.id.tvCardId);
 btnSetPayer = findViewById(R.id.btnSetPayer);
 btnGoReceiver = findViewById(R.id.btnGoReceiver);
 tvMyName.setText(payerName);
 tvCardId.setText(payerCard);
 updateBalanceUI();
 btnSetPayer.setOnClickListener(v-> {
 // for simplicity: change payer name from edit text (if user wants)
 String newName = edtAmount.getTag() == null ? payerName :
 payerName; // no interruption
 Toast.makeText(this, "Payer name: " + payerName,
 Toast.LENGTH_SHORT).show();
 });
 btnGoReceiver.setOnClickListener(v-> {
 startActivity(new Intent(this, ReceiverActivity.class));
 });
 nfcAdapter = NfcAdapter.getDefaultAdapter(this);
 if (nfcAdapter == null) {
 Toast.makeText(this, "NFC is not available on this device.",
 Toast.LENGTH_LONG).show();
 return;
 }
 nfcAdapter.setNdefPushMessageCallback(this, this);
 // Clicking anywhere to 'prepare' payment — we build NDEF when requested 
by NFC stack
 }
 private void updateBalanceUI() {
 double bal = balanceStore.getBalance("payer");
 tvMyBalance.setText(String.format("₹ %.2f", bal));
 }
 
@Override
 public NdefMessage createNdefMessage(NfcEvent event) {
 String amountStr = edtAmount.getText().toString().trim();
 if (amountStr.isEmpty()) amountStr = "0";
 // Build message
 NdefMessage msg = NfcHelper.buildPaymentMessage(amountStr, payerName,
 payerCard);
 return msg;
 }
 }
