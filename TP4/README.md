------------------------------------------------------------------------

![](resources/logo_poly.png)
<td><h1>INF2010 - Structures de données et algorithmes</h1></td>

Merci au cours INF3500 pour le format du Markdown

------------------------------------------------------------------------

Travail pratique \#4
====================

*Heap* - Implémentation d'une file de priorité
=============================================================

Objectifs
---------
* Apprendre le fonctionnement d’un *heap*

* Comprendre la complexité asymptotique d’un *heap*

* Utiliser un *heap* dans un problème complexe

Préparation au laboratoire
--------------------------
Pour ce laboratoire, il est recommandé d’utiliser l’[IDE IntelliJ](https://www.jetbrains.com/fr-fr/idea/download/)
offert par JetBrains. Vous avez accès à la version complète (Ultimate) en tant qu’étudiant à Polytechnique Montréal.
Il suffit de vous créer un compte étudiant en remplissant le [formulaire d'inscription étudiante](https://www.jetbrains.com/shop/eform/students).

------------------------------------------------------------------------

Partie 1 : Implémentation d'un *heap*
---------------
Un *heap* est une structure de données de la famille des arbres binaires et est communément utilisé comme implémentation d'une file de priorité.

Un des cas d'utilisation de cette structure est lorsque seulement les `a` plus petits éléments (ou plus grand) d'une liste de longueur `n` nous intéressent. En effet, dans ce cas de figure, la complexité temporelle sera de O(n + a log n), O(n) étant le constructeur du heap et O(a log n) l'accès à `a` éléments du heap. O(n + a log n) se simplifie à O(a log n), complexité bien meilleure que l'utilisation d'un bon tri comme *quicksort* ou *mergesort* qui demanderait O(n log n) même si seulement les `a` premiers éléments sont nécessaires.

Pour bien implémenter ledit *heap*, suivez les tests contenus dans `HeapTest.java`. Il est recommandé de laisser les tests avec baromètre pour la toute fin.

Les fonctions identifiées avec `HAS TO BE RECURSIVE` doivent obligatoirement être implémentées récursivement.

**ATTENTION : Une note de 0 sera attribuée à cette partie si l’étudiant utilise un `heap` déjà implémenté provenant d’une librairie quelconque.**

Vous pouvez utiliser la visualisation suivante pour mieux comprendre comment la structure d'un heap fonctionne : [lien vers la visualisation](https://visualgo.net/en/heap)

------------------------------------------------------------------------

Partie 2 : Problème typique d'entrevue
----------------
Pour résoudre ce problème vous devez utiliser un monceau ([PriorityQueue](https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html) en Java)

**Comment traiter les boîtes :**     
On prend les deux boîtes le plus lourdes et les deux entres en collision.
* Si les deux boîtes ont le même poids, le deux sont détruites.
* Si la première boîte à un poids supérieur à celui de la deuxieme, boîte 2 est détruite. Le nouveau poids de la première boîte :  
  *Poids Boites 1 = (Poids Boites 1) - (Poids Boites 2)*.

*Vous devez itérer sur tout les éléments de la collection afin de réduire sa taille à une boîte ou moins.*

### Entrées
* Une collection d'entiers. Chaque entier dans la collection représente le poids d'une boîte.

### Sortie
* Retourner le poids de la boîte finale. S'il n'a plus de boîte, retourner 0.

Voici un exemple:  
**Input** : [5,6,7,8,10,11]  
**Output**: 1

À la fin du jeu, il aura **au plus** une boîte restante.

Vous devez justifiez vos complexité spatiale et temporelle dans l'en-tête de la fonction.

**ATTENTION : Seul l'utilisation de la librairie java.util est permise pour cette partie. Si votre code montre un warning dans cette partie, il n'aura pas de pénalité au niveau de la qualité de code.**

------------------------------------------------------------------------

Barème de correction
--------------------

||||
|-----------------|-----------------------------|-----|
| Partie 1        | Réussite des tests          | /10 |
| Qualité du code | 		                     | /2  |
| Partie 2        | Réussite des tests          | /3  |
|                 | Justification de complexité | /3  |
| Qualité du code |                             | /2  |
| Total           |                             | /20 |

**Correction automatique** : Les tests sont un bon moyen d'évaluer votre note avant la remise. Néanmoins, l’entièreté
de votre code sera révisée par un chargé de laboratoire pour s'assurer qu'il réalise véritablement les tâches demandées.
Le jeu de test n'est pas complet, alors les tests devraient être utilisés comme direction pour le dévelopement
et non une évaluation du code.

**ATTENTION : Pour la Partie 1, une fonction n’ayant pas la bonne complexité entraîne la perte des points de tous les tests utilisant cette fonction.**

### Qu'est-ce que du code de qualité ?
* Absence de code dédoublé
* Absence de code mort
* Respecte les mêmes conventions de codage dans tout le code produit
  * Langue utilisée
  * Noms des variables, fonctions et classes
* Variables, fonctions et classes avec des noms qui expliquent leur intention et non leur comportement
* Création de fonctions privées pour diminuer la répétition de code
* Création de classes pour isoler des comportements
* Vous pouvez aussi vous référer au document suivant : [lien vers le document](https://docs.google.com/document/d/12YDr57UofDKu5mCJBSYhOQ1yibLu6K0s8xAbMj3_SGg/edit?usp=sharing)

Vous ne pouvez jamais changer l'API public des classes fournies.

Le dernier commit de votre répertoire sera utilisé comme remise finale. Chaque jour de retard créera une pénalité
additionnelle de 20 %. Aucun travail ne sera accepté après 4 jours de retard.

Remise : 8 juin 23h59 


