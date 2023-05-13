# Mediatheque
Ce projet comporte un serveur proposant trois services : réservation, emprunt et retour de documents. Il comporte aussi un client pour chaque service et un client BTTP (version beaucoup moins futé que son cousin HTTP) qui est le médiateur entre le client et le serveur. 

## Contexte de l'exercice

Une médiathèque fictive désire un système de gestion des réservations, emprunt, et retour des documents. Chacun d'autre eux fera l'objet d'un service dédié lancé sur un serveur d'écoute propre à ce dernier. Chacun des services aura un client correspondant disponible chacun à un endroit différent : le client de retour est disponible uniquement sur l'ordinateur à l'accueil, le client de réservation sera, à l'avenir, disponible sur le site web de la médiathèque, et le client d'emprunt sur les bornes réservés à cet effet dans la médiathèque.

Pour le moment, nous supposons que tout tourne 24/7 et que les ajouts de documents ou abonnés se font directement sur la base de données.

## Prérequis 

### Dépendances

Les packages `IOSocket` et `Messages` sont présents en fichier jar dans le répertoire lib à la racine du projet. Ces fichiers sont inclus comme dépendances de tous les autres packages qui contiennent eux une classe Main à lancer.

Le package `Serveur` possède, de plus, un pom.xml qui répertorie toutes les dépendances que **Maven** peut satisfaire.

### Base de données

Le fichier `Library.sql` à la racine et un fichier contenant toutes les requêtes sql nécessaire pour recréer la base de données de test. La base de données utilisées est une BD **Postgre**.

### Arguments du main

La méthode `main` du client BTTP attend en première arguments le numéro de port sur lequel attendre les nouvelles connexions. Cette valeur doit être la même que celle dans les méthodes `main` des différents clients qui, par défaut, est **1**.

## Utilisation

La classe *AppServeur* du package `Serveur` doit être lancée en première, puis la classe *Main* du package `ClientBTTP`, et enfin le client.

Pour le moment, les clients ainsi que le client BTTP doivent être lancé sur la même machine. Pour pouvoir la lancer sur différentes machines, il suffit de remplacer la valeur de la variable :
```java 
final String HOST = "localhost";
```
présente dans la méthode `main` de tous les clients par l'adresse IPV4 de la machine **du même réseau** où le client BTTP est lancé.
