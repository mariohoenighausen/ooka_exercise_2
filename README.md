# Lösung zu Übung 2 des Moduls OOKA der Hochschule Bonn-Rhein-Sieg

## Aufgabe 1 (Laufzeitumgebung für Komponenten)

### FA1: Der Component Assembler muss die Laufzeitumgebung starten können.
### FA2: Der Component Assembler muss die Laufzeitumgebung stoppen können.

### FA3: Der Component Assembler muss neue Komponenten aus einem lokalen Verzeichnis des Rechners in die Laufzeitumgebung einsetzen (deployen) können.
### FA4: Der Component Assembler muss eingesetzte Komponenten in der Laufzeitumgebung ausführen können. Eine Start-Methode sollte dabei mit Hilfe einer Annotation im Rahmen eines Komponentenmodells definiert werden.
### FA5: Der Component Assembler soll mehrere unterschiedliche Komponenten gleichzeitig (parallel) ausführen können.
### FA6: Der Component Assembler soll mehrere Instanzen von einer einzelnen Komponente gleichzeitig (parallel) ausführen können.
### FA7: Der Component Assembler muss in der Lage sein, die Status der aktuell eingesetzten Komponenten über die Laufzeitumgebung abzurufen. Ein Status sollte pro Komponente folgendes (mindestens) beinhalten: Laufende Identifikationsnummer, Name, Zustand.
### FA8: Der Component Assembler muss Komponenten in der Laufzeitumgebung stoppen können. Auch hierzu sollte das Komponentenmodell eine „entsprechende“ Stop-Methode bereitstellen.
### FA9: Der Component Assembler muss Komponenten aus der Laufzeitumgebung löschen können.
### TA01: Die LZU soll die eingehenden Anfragen auf mehrere Instanzen über einen Dispatcher gleichmäßig oder nach fachlichen Kriterien verteilen (engl. load balancing).
   (Anmerkung: Erweiterung der FA06. Dies ist eine COULD-Anforderung, deren Implementierung sie erst mal später angehen sollten. Grundlegende konzeptionelle Überlegungen können sie aber jetzt schon angehen.)
   

## Teilaufgaben

### 1.)
Ein Komponentenmodell beschreibt die Struktur einer Komponente. Folgende strukturelle Eigenschaften sollte eine Komponente besitzen:
Eine „deploybare“ Komponente entspricht einem .jar-File,
- Ein .jar-File kann wiederum eine beliebige Menge aus Klassen enthalten kann. In
einem .jar-File muss genau eine Klasse mit einer annotierten start-Methode sowie
einer annotierten stop-Methode enthalten sein (die „Starting Class“)
- Klassen in einem .jar-File sollten in Packages angeordnet sein.
- Eine Komponente kann ein Meta-File besitzen, z.B. zur Beschreibung von
Abhängigkeiten ([optional]!).
- Eine Komponente kann ein lib-Verzeichnis zur Aufnahme von abhängigen Libraries
(z.B. JDBC-Treiber) besitzen ([optional]!). Ratschlag: bitte diese Libraries eher in den Classpath der Laufzeitumgebung ablegen. Das Auslesen von Libraries aus dem lib- Verzeichnis kann „tückisch“ sein.

### 2.) 
Als deploybare Beispiel-Komponente können sie die in der Übung Nr. 1 entwickelte Komponente eines Buchungssystems verwenden (.jar File als Package-Format!).
Diese Komponente muss dann entsprechend dem zu entwickelnden Komponentenmodell angepasst werden 
(z.B. um eine Start-Methode in einer internen Klasse Client, welche die Abfragen über das Provided Interface initiiert und den Cache setzt). Zugehörige Libraries (z.B. die JDBC-Treiber-Klassen) sollten sie in den Classpath der Laufzeitumgebung ablegen (siehe Anmerkung oben). Für Demo-Zwecke können sie aber auch zu Beginn eine einfache Komponente zusätzlich implementieren und deployen (z.B. für den Test bzw. Vorführung der parallelen Ausführbarkeit von Komponenten).

### 3.)
Modellieren sie zudem ein UML-basiertes Zustandsmodell für ihre Anwendung, um die möglichen Zustände darzustellen, die ihre Komponenten einnehmen können.
Berücksichtigen sie auch mögliche Zustandsübergänge inklusive annotierter Aktionen (Methoden) aus dem Kontext einer Komponente, die bei einem Zustandsübergang ggf. ausgeführt werden.

### 4.)
Testen sie den Classloader-Mechanismus ihrer Laufzeitumgebung angemessen mit Hilfe diverser JUnit-Tests. Sie sollten hierzu JUnit 4 oder JUnit 5 verwenden. Zeigen sie mittels des Tests auch, dass ihr Classloader ihre Klassen aus den Komponenten isoliert lädt. Weitere Hinweise dazu unten (Link auf Artikel in Jax-Center).

Manuelle Tests:
// loadComponent app/codesOOKA-1.0-SNAPSHOT.jar
// runComponentInstance My-Component 0.0.1
// destroyComponentInstance 0.0.1
// stopComponentInstance My-Component 0.0.1
// unloadComponentInstance My-Component 0.0.1
// listLoadedComponents 
// listComponentInstances

### 5.)
Dokumentieren sie kurz, welche Kriterien aus dem Rahmenwerk nach Crnkovic ihr Komponentenmodell erfüllt. Konzentrieren sie sich dabei auf die Bereiche „Lifecycle“ und „Construction“ (vgl. Kapitel 2, Folien 23-24).



## Quellen

- [Maven-site-plugin (Zur Erstellung von JavaDocs mit Maven)](https://maven.apache.org/plugins/maven-site-plugin/faq.html)
- [Multi-Module Maven Applications Baeldung](https://www.baeldung.com/maven-multi-module-project-java-jpms)
  - [Example source code for a Multi-Module maven application ](https://github.com/eugenp/tutorials/tree/master/maven-modules/multimodulemavenproject)
- [A Guide to Java 9 Modularity (Java modules) Baeldung ](https://www.baeldung.com/java-9-modularity)
- [Settings.xml Baeldung](https://www.baeldung.com/maven-settings-xml)
- [Stackoverfloew issue about jdbc driver not being modularized](https://stackoverflow.com/questions/58729980/java-9-jdbc-setup)
  - [Github issue to the same topic](https://github.com/pgjdbc/pgjdbc/issues/1979)
 

