# Seminarski-rad---aplikacija-za-inzajmljivanje-foto-video-opreme
Opis Projekta
Ovo je softverski sistem za upravljanje klijentima, trenerima i zakazivanje personalnih treninga, razvijen u Klijent-Server arhitekturi.

Aplikacija je razvijena u Java programskom jeziku i koristi MySQL bazu podataka za perzistenciju svih informacija.

Tehnologije
Programski jezik: Java
Baza podataka: MySQL Server (Preporučena verzija 8.0+)
Konekcija: JDBC (Potreban MySQL Connector/J drajver)
Arhitektura: Klijent/Server (Zahteva pokretanje dva zasebna dela)
Projekti: PSProjectServer (Serverski deo), PSProjectClient (Klijentski deo), PSProjectCommon(Zajednički deo)
Potrebno za pokretanje
Za testiranje aplikacije, potrebno je da imate instalirano i podešeno sledeće:

Java Development Kit (JDK) 8 ili noviji.
MySQL Server (preporučena verzija 8.0+).
IDE (IntelliJ IDEA, Eclipse, NetBeans) za uvoz i pokretanje Java projekta.
Postavljanje baze podataka
Pošto fajlovi baze nisu deo Git repozitorijuma, morate ručno kreirati bazu koristeći priloženi SQL skript.

1. Preuzmite SQL skriptu
U korenu ovog projekta nalazi se fajl: baza.sql.

2. Izvršite skriptu na svom MySQL Serveru
Pokrenite MySQL Server.
Uvezite fajl baza.sql koristeći bilo koji SQL klijent (npr. MySQL Workbench, SQLYog, PhpMyAdmin) ili komandnu liniju.
   Ovaj skript će:    * Kreirati bazu pod nazivom projekat.    * Kreirati sve potrebne tabele (trener, klijent, evidencija treninga, itd.).    * Ubaciti početne podatke za testiranje (test klijente/trenere).

Pokretanje Aplikacije i Konfiguracija Konekcije
Aplikacija se pokreće u dva koraka. Serverski deo mora biti pokrenut i konfigurisan pre klijentskog dela.

1. Pokretanje i Konfiguracija Serverskog Dela
Uvezite projekat u vaš IDE.
Pronađite i pokrenite glavnu klasu Serverskog dela:
Projekat: PSProjectServer
Klasa: main.Main.java
Otvoriće se forma za upravljanje serverom.
Idite na karticu Konfiguracija. Ovde možete prilagoditi port, URL, lozinku i korisničko ime baze prema vašoj lokalnoj MySQL instalaciji.
Pritiskom na dugme Sačuvaj trajno se menja config.properties fajl na serverskoj strani.
Vratite se na karticu za upravljanje i pritisnite dugme Pokreni Server.
2. Pokretanje Klijentskog Dela
Pronađite i pokrenite glavnu klasu Klijentskog dela:
Projekat: PSProjectClient
Klasa: main.Main.java
Nakon pokretanja, trebalo bi da se otvori prozor za prijavu.
Napomena: Ako serverski deo koristi određeni port (npr. 9000), proverite da li je taj port slobodan pre pokretanja.
