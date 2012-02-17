
/*---- vorgefertigte Variablen:

heute = jetzige Zeitpunkt
partner = liste mit allen partnern und deren eigenschaften
ereignisse = liste mit allen ereignissen und deren eigenschaften
liste alle { eins -> = iterire ueber alle elemente aus der liste und benenne das aktuelle "eins"
alle von:liste, aufdrosseln:{ eins -> = das selbe nur anders geschrieben
von zeitpunkt bis zeitabschnitt alleTage = liste mit jedem tag von zeipunkt bis zeitabschnitt

------------------------------*/

partner alle { partner ->

    ereignisse alle { ereignis ->

       von heute bis 1.jahr alleTage { tag ->

          new TestObj(partner.name, tag, ereignis.name )

       }

   }

}
println "ok"