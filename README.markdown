# CinemaSeries - Application de découverte des films et des séries

## Aperçu

CinemaSeries est une application Android pour découvrir et gérer les films et les séries. Elle permet d’explorer les contenus, de sauvegarder les favoris, de gérer les profils et affiche un message d’erreur si l’appareil est hors connexion.

## Github
https://github.com/heisenbergwalter/Series-Movies

## Fonctionnalités

La navigation et la découverte des films et des séries, les favoris, le profil. L’inscription/la connexion, les détails des films/des séries, la sauvegarde des favoris localement.



## Les technologies

- **L’API REST** : la récupération des données via TMDB.
- **Room** : le stockage local (les favoris, les données des utilisateurs).
- **Retrofit** : les appels réseau et le parsing (Gson).
- **Postman** : les tests de l’API.
- **Glide** : le chargement des images.

## L’architecture

L’architecture MVVM : l’UI (MainActivity, les fragments), la logique (ViewModel, Repository), les données (Room, l’API). Les SharedPreferences via SessionManager pour les sessions. La gestion des erreurs : l’écran "Pas de connexion" (NetworkMonitor).

## L’installation

1. Cloner le dépôt.
2. Ouvrir dans Android Studio.
3. Ajouter une clé de l’API TMDB dans le projet.
4. Compiler et exécuter sur un appareil ou un émulateur (minSdk 24).

## La conception

L’icône, l’écran de démarrage et les images créés sur Canva pour un design cohérent.

## Les contributions

Les contributions sont bienvenues. Forkez, créez une branche, soumettez une pull request.

## La licence

Le projet est sous la licence MIT. Voir le fichier `LICENSE` pour les détails.