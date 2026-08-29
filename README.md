# 🧱 Template Android — Ktor · Hilt · Ksp · Room

> **Un point de départ prêt à l'emploi pour mes apps Android.**

Template personnel préconfigué avec les briques essentielles d'une application Android moderne. 
L'objectif : ne plus repartir de zéro à chaque nouveau projet.

---

## 🎯 À quoi ça sert ?

Quand je démarre une nouvelle app, je veux pouvoir me concentrer sur les **fonctionnalités métier** — pas sur la configuration de Hilt, Room ou Ktor pour la énième fois. Ce template me fournit une base solide et testée avec :

- ✅ **Injection de dépendances** déjà câblée (Hilt)
- ✅ **Base de données locale** prête à l'emploi (Room3 + Ksp)
- ✅ **Client réseau** configuré (Ktor)
- ✅ **Architecture Clean** en place (Repository pattern, séparation des couches)

---

## 🏗️ Stack technique

| Brique                 | Rôle |
|------------------------|---|
| **Kotlin**             | Langage principal |
| **Jetpack Compose**    | UI déclarative |
| **Hilt**               | Injection de dépendances |
| **Room**               | Base de données locale (SQLite) |
| **KSP**                | Processeur d'annotations (remplace kapt) |
| **Ktor Client**        | Appels réseau / API REST |
| **Clean Architecture** | Séparation data / domain / presentation |
| **Audio3**             | gestion des mp3 |


---

## 📁 Structure du projet

```
app/
├── di/                 # Modules Hilt (DatabaseModule, NetworkModule…)
├── data/
│   ├── repository/     # pour les implémentations
│   ├── appdatabase/    # Room
│   ├── dao/            # Room
│   ├── entities/       # Room
│   ├── mapper/
│   └── remote/         # Ktor (API Client, DTOs)
├── domain/
│   ├── model/          # Modèles métier
│   ├── usecases/
│   └── repository/     # Interfaces Repository
├── presentation/
│   ├── ui/             # Composables (écrans, composants)
│   └── viewmodel/      # ViewModels

```

---

## 🚀 Comment l'utiliser

1. **Cliquer sur "Use this template"** sur GitHub (bouton vert)
2. Nommer le nouveau dépôt avec le nom de ton projet
3. Cloner et ouvrir dans Android Studio
4. Renommer le package de base (`com.example.template` → `com.monapp`)
5. Coder tes fonctionnalités métier — le socle est prêt !

---

## 📝 Notes

- Dernière modification  **août 2026**
- Pensé pour mes projets personnels mais libre d'utilisation
- Les versions des dépendances sont celles qui fonctionnent ensemble — vérifier les mises à jour avant de les bumper

---

## 👩‍💻 Auteure

**Jacqueline** — [@jacquelineRaynaud](https://github.com/jacqueline-raynaud)  
Développeuse Android · Formatrice 30 ans d'expérience · Kotlin & Compose enthusiast
