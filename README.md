# Zadanie rekrutacyjne do firmy BOLDARE

## Opis programu

Program konsolowy, który przyjmuje na wejściu nieokreśloną liczbę reszt do wydania. Na początku programu kasa drobnych
ma określony stan monet. Celem programu jest minimalizacja liczby używanych monet przy wydawaniu reszt, z
uwzględnieniem, że poprzednie monety schodzą ze stanu i nie mogą być używane przy wydawaniu kolejnych reszt.

W programie została dodatkowo zaimplementowana funkcja pobierania argumentów z wejścia w celu umożliwienia podania przy
uruchomieniu reszt to wydania

### Przykład uruchomienia:

`./gradlew run --args="--change 10"`
W tym wypadku zostanie wydana reszta 10 zł

Wynik w konsoli:

```
Dla reszty 10.0 zł:
Wydaj 1 monet 5 zł
Wydaj 2 monet 2 zł
Wydaj 1 monet 1 zł
```

**Dodatkowo został dodany skrpyt aby uruchomic aplikacje z resztami zdefiniowanymi w pliku zadania rekrutacyjnego(
uznalem je jako domyslne)**:

Aby uruchomic w głównym katalogu projektu należy wykonac komendę:
`./start-default`
