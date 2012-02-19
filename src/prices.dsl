
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
//vielleicht auch hier einprogrammieren wie oft ein preisupdate zum partner hochgeladen werden soll.

liste = []

Hotel.Zimmertypen.alle { typ ->

  von heute bis 3.months alleTage { tag ->

        tagesPreis = typ.grundpreis

        ereignisse.alle { ereignis ->

            TagInnerhalbEreignis = tag.innerhalb ereignis
            if(TagInnerhalbEreignis) println "innerhalb"
            tagesPreis += wenn TagInnerhalbEreignis dann 10 prozent tagesPreis   // oder auch:  10 / tagesPreis * 100

            tageEntfernt = tage von: heute, bis: ereignis.von // oder: abstand { von heute bis ereignis.von }
            // println "${tageEntfernt} weil anfang von ${ereignis.name} ist ${ereignis.von}"

            nichtvorbei = tageEntfernt > 0
            bald = tageEntfernt < 10



            lastMinuteRabatt = (tageEntfernt * 0.5).prozent tagesPreis

            tagesPreis += wenn bald.und(nichtvorbei) dann lastMinuteRabatt
        }

        // 0.5 = die hälfte aller zimmer ist belegt.
        tagesauslastung = auslastung tag
        // abhängig von der Auslastung wird ein teil von einem drittel der grundkostn aufaddiert.
        tagesPreis += tagesauslastung / gesamtzimmer * (typ.grundpreis / 3)


        wochenendaufschlag = wenn tag.wochenende dann 10 prozent tagesPreis
        assert (tag.wochenende)? wochenendaufschlag != 0 : wochenendaufschlag == 0
        tagesPreis += wochenendaufschlag

        liste << [typ.name, tag, tagesPreis]
        println "$typ.name, $tag, $tagesPreis }"

     }

  }



assert liste.size() != 0


