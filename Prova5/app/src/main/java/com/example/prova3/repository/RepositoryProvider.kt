
package com.example.prova3.repository

object RepositoryProvider {
    val formattazioneRepository: FormattazioneRepository by lazy { FormattazioneRepository() }
    val gestioneAccountRepository: GestioneAccountRepository by lazy { GestioneAccountRepository() }
    val gestioneMenuRepository: GestioneMenuRepository by lazy { GestioneMenuRepository() }
    val gestioneOrdiniRepository: GestioneOrdiniRepository by lazy { GestioneOrdiniRepository() }
}
