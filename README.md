O aplicatie scrisa in limbaj Java bazata pe ideea unei retele de socializare in care iti poti crea un cont, sa te imprietenesti cu alti utilizatori si sa trimiti mesaje unui alt utilizator sau sa primesti mesaje de la un alt utilizator.

Relatii intre entitati:
- Un utilizator al retelei are o lista de prieteni
- O prietenie se stabileste intre doi utilizatori ai retelei
- Reteaua este definita de multimea utilizatorilor si a relatiilor de prietenie dintre acestia
- Mesajele se pot trimite intre doi utilizatori care sunt prieteni

Cerinte non-funționale:
- Arhitectura stratificata 
- DDD (Domain Driven Design) 
- Validarea datelor (Strategy Pattern) 
- Definirea propriilor clase de exceptii pentru tratarea situatiilor speciale;
- GUI
- Repository de tip DataBase
- Asigurare persistenta datelor in baza de date (folosind PostgreSQL)


Functionalitati de baza:
- Gestiunea utilizatorilor: operatii CRUD
- Gestiunea relatiilor de prietenie intre utlizatori: adagare/stergere prieten
- Folosirea tipului Optional la tipul returnat de metodele update, delete, save si findOne
- Gestiunea mesajelor: trimitere/stergere mesaj
- Implementare sablon Observer pentru notificari
- Autentificare: logare/delogare
- Stabilirea relatiei intre utilizator si prieteni, stergerea in cascada

   
Cerinte functionale:
- Add/Remove utilizator
- Add/Remove prieten
- Add/Remove mesaj
- Creare cont (inregistrare) pentru un utilizator neinregistrat deja
- Logare utilizator
- Simulare trimitere invitatii de prietenie si adaugare relatie doar daca utilizatorul invitat o accepta
- Salvare cereri de prietenie si actualizarea statusul lor (pending, approved, rejected)

Descriere:

La deschiderea aplicatiei apare o fereastra de logare unde utilizatorul introduce usernameul si parola daca are deja un cont, sau poate alege optiunea de creare a contului in cazul in care nu are deja un cont. 

![image](https://github.com/cristianamihu/Java-project---Social-Network/assets/128689630/c5aaf615-63b6-432e-a600-a52880c1cff1)

![image](https://github.com/cristianamihu/Java-project---Social-Network/assets/128689630/eac993e9-6681-4973-b906-c22995ffb0ba)

Odata autentificat, apare o noua fereastra in care se poate vedea lista prietenilor utilizatorului conectat, posibilitatea de stergere a acestora si totodata posibilitatea de cautare a unui utilizator caruia i se poate trimite o cerere de prietenie. Dupa trimiterea cererii de prietenie, relatia este adaugata intre cei doi utilizatori doar dupa ce celalalt utilizator o accepta. Aceasta cerere are un status constant care se actualizeaza in functie de acceptarea sau nu a cererii (pending, approved, rejected).

Daca un utilizator selecteaza un anumit prieten din lista lui de prieteni poate alege sa poarte o conversatie cu acesta. Se deschide o fereastra de chat si ii poate trimite celuilalt utilizator un mesaj sau mai multe, totodata celalt poate face acelasi lucru, poate sa ii dea replay la mesaj. 

Pe de alta parte exista si optiunea de delogare, un utilizator poate sa iasa din cont si sa se conecteze un alt utilizator.
