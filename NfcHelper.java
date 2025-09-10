package com.example.tappnpay;
 import android.nfc.NdefMessage;
 import android.nfc.NdefRecord;
 import android.nfc.NfcEvent;
 import android.os.Bundle;
 import java.nio.charset.Charset;
 public class NfcHelper {
 // Build a text NDEF message with payload like: PAY|50|Om|CARD123
 public static NdefMessage buildPaymentMessage(String amount, String
 payerName, String payerCardId) {
 String payload = "PAY|" + amount + "|" + payerName + "|" + payerCardId;
 byte[] textBytes = payload.getBytes(Charset.forName("UTF-8"));
 NdefRecord record = NdefRecord.createMime("text/plain", textBytes);
 return new NdefMessage(new NdefRecord[]{record});
 }
 // Parse text/plain payload
public static String parsePayload(NdefMessage message) {
 if (message == null) return null;
 NdefRecord[] records = message.getRecords();
 if (records.length == 0) return null;
 try {
 byte[] payload = records[0].getPayload();
 return new String(payload, Charset.forName("UTF-8"));
 } catch (Exception e) {
 e.printStackTrace();
 return null;
 }
 }
 }
