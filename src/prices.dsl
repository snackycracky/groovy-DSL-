
/*

vorgefertigte Variablen:

hotel.roomTypes                             = liste mit allen zimmertypen
typ.grundpreis                              = festgelegter Grundpreis fuer den jeweiligen Typ
heute                                       = jetzige Zeitpunkt
partner                                     = liste mit allen partnern und deren eigenschaften
ereignisse                                  = liste mit allen ereignissen und deren eigenschaften
liste alle { eins ->                        = iterire ueber alle elemente aus der liste und benenne das aktuelle "eins"
alle von:liste, aufdrosseln:{ eins ->       = das selbe nur anders geschrieben
von zeitpunkt bis zeitabschnitt alleTage    = liste mit jedem tag von zeipunkt bis zeitabschnitt
ergebnis = addiere 30 zu ergebnis           = addition
prozentZahl.prozent(ProzentVonDieserZahl)   = ProzentVonDieserZahl / prozentZahl * 100
tag.innerhalb(Zeitraum)                     = der tag ist ein Datum und der Zeitraum hat ein von und ein bis. von und Bis sind beide Daten. Gibt wahr oder nicht wahr zurück
wenn Wahrheitswert dann zahl                = die zahl wird nur dann addiert, wenn der wahrheitswert positiv ist ansonsten 0.
liste = []                                  = neue leere liste wird definiert
liste << ding                               = ding wird zur liste hinzugefuegt
x = 0                                       = die variable x wird definiert und erhaelt den wert 0
------------------------------*/

liste = []

hotel.roomTypes alle { typ ->

  ereignisse alle { ereignis ->

     von heute bis 1.jahr alleTage { tag ->

        tagesPreis = typ.grundpreis

        tagesPreis += wenn tag.innerhalb(ereignis) dann 10.prozent(tagesPreis)  // 10 / tagesPreis * 100

        liste << [typ.name, tag, tagesPreis]
        println "$typ.name, $tag, $tagesPreis"

     }

  }

}

assert liste.size() != 0


