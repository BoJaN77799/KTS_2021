# ASDF Tim - Informacioni sistem restorana 
- Konstrukcija i testiranje softvera 2021

## Članovi tima
- [Marko Bjelica SW 04/2018]
- [Darko Tica SW 22/2018]
- [Bojan Baškalo SW 49/2018]
- [Veljko Tošić SW 55/2018]


## Smernice za pokretanje

- Importovati projekat u Intelij kao [Maven] projekat.
 - Kako biste instalirali sve potrebne dependency-je potrebno je da otvorite kontekstni meni nad root direktorijumom projekta, potom na stavci Maven odaberete **Reload project** (Intelij automatski pokreće povlačenje dependency-ja, te Maven reload nije nužan.).
- (**Run App**) Da biste pokrenuli Spring aplikaciju potrebno je da se pozicionirate na .src/main/java/com.app.RestaurantApp i desnim klikom na main klasu odaberete opciju **"Run RestaurantApp"**
- (**Run Tests**) Da biste pokrenuli testove (jedinične i integracione) potrebno je da se pozicionirate na .src/test/com.app.RestaurantApp i nad tim direktorijumom u kontekstnom meniju odabrati opciju **"Run Tests in RestaurantApp"** (Napomena: Testovi se pokreću nad zasebnom h2-in-memory bazom --> .src/test/resources/data-h2.sql)
- (**Run E2E Tests**) Da biste pokrenuli E2E testove potrebno je da se pozicionirati na .src/test/e2e direktorijumom i nad njim u kontekstnom meniju odaberete opciju **"Run Tests in e2e"** (Napomena: E2E testovi se pokreću nad zasebnom h2-in-memory bazom --> .src/main/resources/data-e2e.sql)

## Dodatne napomene:
 Konfiguracija produkcione [PostgreSQL] baze se nalazi u .src/main/resources/application.properties (spring.datasource.username i spring.datasource.password)

[PostgreSQL]: https://www.postgresql.org/
[Maven]: https://maven.apache.org/

[Marko Bjelica SW 04/2018]: https://github.com/bjelicamarko
[Darko Tica SW 22/2018]: https://github.com/darkotica
[Veljko Tošić SW 55/2018]: https://github.com/tosic-sw
[Bojan Baškalo SW 49/2018]: https://github.com/BoJaN77799
