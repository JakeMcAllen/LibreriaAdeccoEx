Progetto per la gestione delle REST di un applicazione: "LIBRERIA".

Per gestire la persistenza ho usato Hibernate, mentre il frameWork Sprign per la creazione dell'applicazione WEB.
Il client può effettuare operazioni di lettura e scrittura dati nel formato "XML" che in "JSON".

Nella classe "src/main/java/restController" sonno definiti tre file per la gestione delle REST.
Ho deciso di dedicare un file per "argomento" in modo da permettere, in caso di modifica o revisione, una ricerca mirata.
In ognuno di questi tre file la prima REST e preceduta da un esempio con apposita URL per richiamare il metodo, configurazione e dati di input. 

Ho deciso di utilizzare delle classi java per il dialogo con il client. Queste classi sono delle versioni semplificate da quelle che poi sono usate all'interno del sistema per la gestione delle operazioni e delle persistenza.
Ho deciso di creare due classi simili per lo stesso oggetto per aggiungere un uteriore livello di "distanza" del cliente dall'interno sistema.

Ho allegato alla consegna il database che ho creato. Questo è un databese su MySQL per la gestione della libreria già parzialmente popolato.
Nella cartella: "src/main/java/dom" sono definiti i file che interfacciamo il collegamento col database. 
I alcuni metodi sono chiamate delle funzioni hibernet, mentre in altre ho avuto la possibilità di operarte direttamente sul database in SQL.


All'arrivo di una REST questa viene gestita dal RestController più appropriato, successivamente viene chiamata un metodo dalla directory "Services". 
In questi file è gestita la loggica del servizi. Normalmente è in questi file che vengono aportate le modifiche nel caso il cliente del progetto volesse delle modifiche.
Infine viegono chiama una o più operazioni sul un metodo della directory "DAO". Queste contengono delle operazioni atomiche per operare sul database.


Progetto di Giorgio Allena (giorgio@allena.it)