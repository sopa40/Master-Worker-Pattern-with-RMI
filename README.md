# Master-Worker-Pattern with RMI
Das Ziel dieser Aufgabe ist es, ein Framework zu schreiben, das ein Problem (Aufgabe) in Teilprobleme
(Teilaufgaben) zerlegt und dieses Teilprobleme dann verteilt berechnen lässt. Es soll wie folgt funktionieren:

- Der __Master__ wird auf einem bekannten Rechner gestartet. Er stellt zwei Schnittstellen (interfaces) bereit.
Eine Schnittstelle interagiert mit den Workern (Master-Interface), die andere Schnittstelle mit einem
Client(Server-Interface).

- Der __Client__ kann dem Master einen Auftrag (Job) übergeben. Dieser Job besteht aus einer Funktion
(Code), die es dem Master erlaubt, anhand der Anzahl der vorhandenen Worker Teilaufgaben zu erstellen.

- Die __Worker__ registrieren sich bei dem Master. Nun laden die Worker nach Aufforderung durch den Master
eine Teilaufgabe über das Task-Interface (Code und Parameter) herunter und führen diesen Code lokal
aus. Die Ergebnisse liefern sie dem Master zurück. Dies geschieht so lange, bis es keine Teilaufgaben mehr
bei dem Server gibt
