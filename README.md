[![codecov](https://codecov.io/gh/Peketeus/Miniprojekti/graph/badge.svg?token=Z9ZMHN4XR3)](https://codecov.io/gh/Peketeus/Miniprojekti)

# Miniprojekti
Ohjelmistotuotanto miniprojekti. Ryhmä 9

## BibTeX- viitteiden hallintajärjestelmä (CLI)

Helppokäyttöinen käyttöliittymä BibTeX-viitteiden lisäämiseen, poistamiseen, muokkaamiseen ja listaamiseen.

### Linkki backlogiin

Projektin backlog löytyy Trellosta:
https://trello.com/b/F1ZtqCsU/log

### Linkki CI-palveluun

Projektissa käytetään GitHub Actions -CI:tä:
https://github.com/Peketeus/Miniprojekti/actions/workflows/main.yml

### Vaatimukset:
- Java (suositus vähintään Java 17)

## Asennusohjeet

1. Siirry projektin Releases-välilehdelle
2. Lataa uusin version tiedostosta BibManager.jar
3. Tallenna JAR-tiedosto haluamaasi hakemistoon, esim:
    - Windows: C:\Users\<käyttäjä>\Downloads\
    - macOS / Linux: ~/Downloads/
4. Avaa komentorivi ja siirry samaan kansioon
5. Käynnistä ohjelma komennolla:
```
java -jar BibManager.jar
```

## Käyttöohjeet
Kun ohjelma käynnistyy, se näyttää päävalikon 
```
===== Welcome to Bib Manager! =====

What would you like to do (1 - 5)?
1) Add reference
2) Edit reference
3) Delete reference
4) List references
5) Exit
Enter your choice: 
```
Valitse haluamasi toiminto syöttämällä numero 1-5 ja paina Enter.\
Ohjelma opastaa sen jälkeen eteenpäin kysymällä tarvittavat tiedot
(esim. viitteen tyypin, avaimen, kentät ja mahdolliset tagit) ja
antaa palautteen sekä onnistuneista että virheellisistä syötteistä.

## Definition of Done

Käyttöliittymä (tai sen osa) katsotaan olevan valmis, kun kaikki seuraavat ehdot täyttyvät:

- Käyttöliittymä toimii ilman virheitä ja toteuttaa sille määritellyt toiminnot.
- On kirjoitettu tarvittavat yksikkötestit ja ne menevät läpi.
- Projekti kääntyy ja testit onnistuvat myös GitHub Actions -ympäristössä.
- Backlog on päivitetty vastaamaan toteutettua toiminnallisuutta.
- Koodissa ei ole keskeneräistä koodia.
- Käyttöliittymä on käyttäjälle selkeä, helppokäyttöinen ja antaa palautteen onnistuneista sekä virheellisistä syötteistä.
- Koodi noudattaa projektin sovittua rakennetta.

## License
This project is licensed under the MIT License.
See the [LICENSE](https://github.com/Peketeus/Miniprojekti/blob/main/LICENSE.txt) file for details.

