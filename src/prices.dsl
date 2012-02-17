

partner.alle { partner ->
    ereignisse.alle { ereignis ->
      von heute bis 1.jahr alle { tag ->
          new TestObj(partner, tag, ereignis)
      }
    }
}
println "ok"