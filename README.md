# tapNpay
 How to import & run (quick steps)

1. Create a New Android Studio project (Empty Activity, Java). Minimum SDK API 19+ (KitKat) or API 21
   recommended.
2. Replace the auto-generated files with the code above (use package 
3. Enable NFC on both test phones (Settings → NFC).
4. Install the app on both phones.
   On the Receiver phone, open 
  com.example.tappnpay ).
  ReceiverActivity (open the app and tap "Open Receiver Screen").
  This opens a screen that listens for incoming NDEF messages when tapped.
  On the Payer phone, open the app, type the amount to send in the amount field, and bring phones
  close (back-to-back). When the NFC handshake occurs, the payer will push an NDEF message and the
 receiver will parse and update balance
