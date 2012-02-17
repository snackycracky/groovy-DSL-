partner.alle { partner ->
    ereignisse.alle { ereignis ->
      (heute.bis(heute + 2.years)).each({ day ->
                  new TestObj(partner, day,ereignis)
             });
    }
}