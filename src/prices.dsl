partner.alle { partner ->
    ereignisse.alle { ereignis ->
      (heute.bis(heute + 2.jahre)).alle { tag ->

      }
    }
}
println "ok"