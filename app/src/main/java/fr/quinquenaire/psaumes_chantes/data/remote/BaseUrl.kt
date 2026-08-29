package fr.quinquenaire.psaumes_chantes.data.remote

import javax.inject.Qualifier

/** Qualifie l'injection de l'URL du dossier distant (voir BuildConfig / local.properties). */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl
