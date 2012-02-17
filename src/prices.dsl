
/*---- vorgefertigte Variablen:

heute = jetzige Zeitpunkt
partner = liste mit allen partnern und deren eigenschaften
ereignisse = liste mit allen ereignissen und deren eigenschaften
von zeitpunkt bis zeitabschnitt alleTage = liste mit jedem tag von zeipunkt bis zeitabschnitt

------------------------------*/

alle von:partner, aufdrosseln:{ partner ->

    ereignisse alle { ereignis ->

       von heute bis 1.jahr alleTage { tag ->

          new TestObj(partner.name, tag, ereignis)

       }

   }

}
println "ok"