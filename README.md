# File Sorting

### Za zadanie jest napisanie programu, który będzie umożliwaił segregowanie plików.
Program powinien:

#### stworzy strukturę katalogów: 
* HOME
* DEV
* TEST

W momencie pojawinia się w katalogu HOME pliku w zależności od rozszerzenia przeniesie go do folderu wg następujących reguł

* plik z roszerzeniem .jar, którego godzina utworzenia jest parzysta przenosimy do folderu DEV
* plik z roszerzeniem .jar, którego godzina utworzenia jest nieparzysta przenosimy do folderu TEST
* plik z roszerzeniem .xml, przenosimy do folderu DEV

Dodatkowo w nowo stworzonym pliku /home/count.txt należy przechowywać  liczbę przeniesionych plików (wszystkich i w podziale na katalogi), plik powinien w każdym momencie działania programu przechowywać aktualną liczbę przetworzonych plików.

