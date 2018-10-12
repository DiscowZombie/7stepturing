# 7stepturing

7stepturing est un petit projet qui permet de tester ses machines de Turing à 7 états. Ce projet est utile pour les étudents
et pour toutes les personnes créant des machines de Turing.

Ce programme est distribué sous licence [CC-BY-NC-SA 4.0 (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License)](https://creativecommons.org/licenses/by-nc-sa/4.0/)
L'usage de ce programme se fait dans les termes énoncés de la licence.

## Installation

Ce programme est écrit en Kotlin et compilé pour JVM. Toute machine, à partir du moment ou elle possède Java 8, est capable de
faire tourner ce programme. Cependant, si vous trouvez un problème, [merci de le signaler](https://github.com/DiscowZombie/7stepturing/issues).

Le téléchargement du programme se fait via la [partie "Version" (Release)](https://github.com/DiscowZombie/7stepturing/releases/latest). Il est
recommandé de toujours gardé à jour son application pour éviter tout problème.

Une fois le programme téléchargé, déplacez-le dans un dossier sur lequel vous avez des droits. Le lancement du programme se
fait depuis une console (tous les shells font l'affaire). La commande de lancement est la suivante :
`java -jar application.jar` (en remplacant `application.jar` par le nom du fichier).

## Écrire des fichiers .t7

Cette section traite de la rédaction de _fichiers .t7_ qui sont les fichiers lus par notre application. Ces fichiers contiennent
la liste des instructions (séquentielle) ainsi que les données du ruban d'entrée.

Un fichier .t7 se décompose donc en deux parties : les transitions et le ruban :
```
transition:

:transition

ruban:

:ruban
```
Tout ce qui se trouve en dehors de ces balises (les balises étant `transition:`, `:transition`, `ruban:`, `:ruban`) est ignoré par le programme.
C'est-à-dire, que vous pouvez écrire vos commentaires directement dans le fichier. Cependant, par convention, vous êtes invités à
précéder vos commentaires par un `#` puis un espace. 

Les instructions et données sur le ruban sont écrit par ligne et sans espace. Les cinq informations constituant une transition doivent
de plus être séparées par une espace (voir plus loin).

### Données acceptables

Dans une machine de Turing, une instruction est composée de cinq éléments :
* l'état initial
* la lettre à lire (les lettres étant représentés par un octet - voire plus loin)
* l'état dans lequel on doit passer si l'instruction correspond (appelé *état suivant* dans la suite)
* la lettre à écrire si l'instruction correspond (appelé *lettre à écrire* dans la suite)
* le déplacement à affectuer si l'instruction correspond

Le programme n'accepte pas n'importe quoi comme valeur pour ces différents éléments et il est important de les respecter
pour ne pas avoir d'erreur. Voici, pour chaque élément, les données attendus par le programme

| Element | Valeur acceptés |
| :-------- | :-------- | 
| État initial | Chaine de caractères. L'état initial est forcément **q0** et le finale forcément **qf**. La valeur **null n'est pas acceptée** |
| Lettre à lire | Interprété comme un octet (Byte), ceci doit donc être un nombre entre 0 et 255 inclus. La valeur **null** est acceptée et représente un blanc sur le ruban |
| État suivant | Même règles que pour l'état initial |
| Lettre à écrire | Même règles que pour la lettre à lire |
| Déplacement | **R** représentant droite (Right) et **L** représentant gauche ("Left"). La valeur **null** est acceptée **mais** ne doit être utilisée que pour l'instruction finale. Dans le cas contraire, elle cause une boucle infinie |

### Instructions

L'écriture des instructions est ensuite assez simple. Par exemple, voici comment vous auriez peut-être écrit :
*Si je suis à l'état q0 et que ma tête de lecture lit un 0, j'écris un 1, je passe en état qf et je me déplace à droite* :
```
q0, 0 -> qf, 1, Droite
```
_Cette écriture représente la façon dont vous l'auriez écrit "à la main"._

Pour le programme, cette écriture devient :
```
q0 0 qf 1 R
```
Nous enlevons toutes les fioritures d'écriture (les espaces permettant de faire les séparations) et on remplace les instructions
de déplacement par la première lettre du mot anglais en majuscules (voir "Données acceptables" pour de plus amples détails).

L'écriture n'est ensuite pas plus compliqué. Vous êtes invités à [lire les exemples pour plus d'informations](https://github.com/DiscowZombie/7stepturing/tree/master/src/main/resources/t7-files).

### Ruban d'entrée

Le ruban d'entrée constitue l'énumération des données qui doivent y être écrites au début du programme. Sa rédaction y est très facile
dans les fichiers *.t7*, chaque donnée doit être sur une ligne :
```
1
0
0
0
1
```
Le fichier ci-dessus est valide (je me suis volontairement limité à 1 mais rappelez-vous que le programme travaille sur des octets, soit
des valeurs jusqu'à 255 inclus **+** la valeur null). Ces valeurs seront copiées sur le ruban au début du programme et des "deux côtés" du ruban (droite et gauche), une
infinité* de caractères vides seront ajoutés.

*(NB: Évidement il n'est pas possible de travailler avec l'infini ni en informatique ni dans notre quotidien mais la limite ici est largement suffisante pour tous nos programmes)*

## Utilisation

Si vous avez essayé de lancer l'application, vous avez sans doute reçu une erreur. En effet, le programme essaye de lire le
fichier `simple.t7` qui n'existe surement pas. Heuresement, nous pouvons spécifier le paramètre `file` qui permet de régler
ce problème. La spécification du paramètre est relativement
simple :
```
java -jar application.jar file=nom_du_fichier.t7
```
_Note: pour des raisons de lisibilité il est recommandé d'utiliser des fichiers en .t7 mais c'est en réalité absoluement pas nécessaire pour le programme._

Lorsque vous lancez le programme en ayant spécifié un fichier valide, ce dernier va executer votre suite d'instructions.
Lorsqu'il a terminé, il vous affiche le nombre d'instructions lues ainsi que l'état de fin.
```
Instruction(s) exécutée(s) avec succès: 10
Le mot est-il accepté (fin sur un état acceptant): true
```
Dans mon cas, il a éxecuté dix instructions et a fini sur un état acceptant (`true` valant Vrai, c'est-à-dire fin sur un état acceptant). Cela signifie que mon mot est reconnu. i.e.
le programme est valide pour ce ruban d'entrée.

Si votre programme ne fini pas sur un état acceptant (`false`), cela signifie qu'il n'est pas complet ; en effet, le programme
n'a alors trouvé aucune instruction satisfaisant son état et s'est donc arrêté. Cela signifie que votre mot n'est pas conforme
ou que votre programme est incomplet.

## Contribuer

Vous pouvez contribuer en envoyant [vos idées ici](https://github.com/DiscowZombie/7stepturing/issues). Je suis également ouvert
[aux Pull Requests](https://github.com/DiscowZombie/7stepturing/pulls) via l'onglet du même nom. Merci pour votre soutien. <3 