# 🎶 Psaumes chantés

Application Android personnelle pour écouter des psaumes chantés (mp3) hébergés en ligne, avec la possibilité d'y attacher une annotation personnelle.

---

## 🎯 Fonctionnalités

- Liste des psaumes disponibles, récupérée depuis le dossier distant
- Lecture d'un mp3 en streaming (les fichiers ne sont jamais téléchargés sur l'appareil)
- Pause / reprise / arrêt de la lecture
- Avance et recul rapide (15 secondes) dans la piste en cours
- Ajout, modification et suppression d'une annotation texte par psaume (boutons + et 🗑️)

---

## 🏗️ Stack technique

| Brique                 | Rôle |
|------------------------|---|
| **Kotlin / Jetpack Compose** | UI déclarative |
| **Hilt**               | Injection de dépendances |
| **Room3 + Ksp**         | Stockage local des annotations (SQLite) |
| **Ktor Client**        | Récupération de la liste des mp3 |
| **Media3 (ExoPlayer)** | Lecture audio en streaming |
| **MVI**                | Architecture des écrans (State / Intent) |

---

## 📁 Structure du projet

```
app/src/main/java/fr/quinquenaire/psaumes_chantes/
├── di/                     # Modules Hilt (réseau, base de données, repository)
├── data/
│   ├── remote/             # Ktor : récupération + parsing du listing des mp3
│   ├── local/               # Room : annotations (entité, dao, base)
│   ├── player/              # Enveloppe autour de Media3 (ExoPlayer)
│   └── repository/         # Implémentation du repository
├── domain/
│   ├── model/               # Modèles métier
│   ├── repository/         # Interface du repository
│   └── usecase/            # Cas d'usage
└── presentation/
    └── psaumes/             # Écran (MVI : contrat, ViewModel, stateful/stateless, previews)
```

---

## ⚙️ Configuration locale (obligatoire avant de lancer l'app)

L'adresse du serveur hébergeant les mp3 n'est **jamais committée** : elle est lue à la compilation depuis `local.properties` (fichier ignoré par git).

1. Copier `local.properties.sample` en `local.properties`
2. Renseigner la clé `psaumes.baseUrl` avec l'adresse réelle du dossier (terminée par `/`)
3. Lancer l'app depuis Android Studio

---

## 📝 Notes

- Dernière modification **août 2026**
- Application personnelle, pas destinée à être publiée

---

## 👩‍💻 Auteure

**Jacqueline** — [@jacquelineRaynaud](https://github.com/jacqueline-raynaud)
Développeuse Android · Formatrice 30 ans d'expérience · Kotlin & Compose enthusiast
