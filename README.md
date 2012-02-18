

# testing groovy DSL features for a price calculation logic #

## pre defined language components in German

<table>
<tbody><tr>
<td> hotel.roomTypes</td>
<td> = liste mit allen zimmertypen</td>
</tr>
<tr>
<td> prozentZahl.prozent(ProzentVonDieserZahl)</td>
<td> = ProzentVonDieserZahl / prozentZahl * 100</td>
</tr>
<tr>
<td> ergebnis = addiere 30 zu ergebnis</td>
<td> = addition</td>
</tr>
<tr>
<td> von zeitpunkt bis zeitabschnitt alleTage</td>
<td> = liste mit jedem tag von zeipunkt bis zeitabschnitt</td>
</tr>
<tr>
<td> alle von:liste, aufdrosseln:{ eins -&gt;</td>
<td> = das selbe nur anders geschrieben</td>
</tr>
<tr>
<td> liste alle { eins -&gt;</td>
<td> = iterire ueber alle elemente aus der liste und benenne das aktuelle "eins"</td>
</tr>
<tr>
<td> ereignisse</td>
<td> = liste mit allen ereignissen und deren eigenschaften</td>
</tr>
<tr>
<td> partner</td>
<td> = liste mit allen partnern und deren eigenschaften</td>
</tr>
<tr>
<td> heute</td>
<td> = jetzige Zeitpunkt</td>
</tr>
<tr>
<td> typ.grundpreis</td>
<td> = festgelegter Grundpreis fuer den jeweiligen Typ</td>
</tr>
<tr>
<td> tag.innerhalb(Zeitraum)</td>
<td> = der tag ist ein Datum und der Zeitraum hat ein von und ein bis. von und Bis sind beide Daten. Gibt wahr oder nicht wahr zurück</td>
</tr>
<tr>
<td> wenn Wahrheitswert dann zahl</td>
<td> = die zahl wird nur dann addiert, wenn der wahrheitswert positiv ist ansonsten 0.</td>
</tr>
<tr>
<td> liste = []</td>
<td> = neue leere liste wird definiert</td>
</tr>
<tr>
<td> liste </td>
<td> = ding wird zur liste hinzugefuegt</td>
</tr>
<tr>
<td> x = 0</td>
<td> = die variable x wird definiert und erhaelt den wert 0</td>
</tr>
</tbody></table>

# example #
<pre>
liste = []

hotel.roomTypes alle { typ ->

  ereignisse alle { ereignis ->

     von heute bis 1.jahr alleTage { tag ->

        tagesPreis = typ.grundpreis

        tagesPreis += wenn tag.innerhalb(ereignis) dann 10.prozent(tagesPreis)

        liste hinzufuegen [typ.name, tag, tagesPreis]

     }

  }

}

assert liste.size() != 0
</pre>

