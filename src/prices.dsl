

partner.alle { partner ->
    ereignisse.alle { ereignis ->
      von heute bis 2.jahre alle { tag ->
          new TestObj(partner, tag, ereignis)
      }
    }
}
println "ok"