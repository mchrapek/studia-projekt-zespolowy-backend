package com.journeyplanner.catalogue.domain.journey;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.journeyplanner.catalogue.domain.journey.JourneyStatus.*;

@Configuration
public class JourneyRepositoryConfiguration {

    @Bean
    public CommandLineRunner init(JourneyRepository journeyRepository) {
        Journey journey = Journey.builder()
                .id("57ee7379-eaa9-4029-9267-dd0eb6a51b2b")
                .status(ACTIVE)
                .name("Jarmark Bożonarodzeniowy Lipsk Express")
                .country("Niemcy")
                .city("Lipsk")
                .description("Dzień 1 Wyjazd wg rozkładu jazdy (wyjazd w nocy z piątku na sobotę). Przejazd do Lipska. Zwiedzanie - " +
                        "słynny Kościół św. Mikołaja, galeria handlowa Specks Hof, mały targ przekąsek, rynek ze Starym Ratuszem, Mädler Passage z " +
                        "Piwnicą Auerbacha, Barthels Hof i Kościół św. Tomasza. Ekskluzywna dzielnica Gohlis - zamek Gohlis, zbudowany w połowie " +
                        "XVIII wieku jako letnia rezydencja burżuazji. Dom Schillera - skromny domek wiejski, w którym Friedrich Schiller mieszkał " +
                        "przez kilka miesięcy w 1785 roku pisząc \"Odę do Radości\". Nieustająco zmieniająca się Plagwitz z kanałami i galeriami. " +
                        "Ta muzyczna dzielnica zachwyca zielenią i imponującymi willami. Od 2002 roku w budynku byłego Trybunału Ludowego działa " +
                        "Federalny Sąd Administracyjny. Trasa wycieczki prowadzi przez tętniące życiem południowe przedmieścia i obok popularnego " +
                        "Panometeru do słynnego miejsca w Lipsku, czyli Pomnika Bitwy Narodów. Następnie udamy się przez Stare Miasto, Plac niemiecki " +
                        "i obok bawarskiej stacji kolejowej do Augustusplatz. Tu znajdują się trzy ważne instytucje kulturowe, które w znaczący sposób " +
                        "kształtują wizerunek miasta - nowa sala koncertowa, uniwersytet i opera. Pobyt na kolorowych, pięknie przystrojonych jarmarkach " +
                        "adwentowych, które odbywają się na terenie całego historycznego centrum miasta. Wyjazd w drogę powrotną w godzinach wieczornych. " +
                        "Dzień 2 Powrót do Polski w godzinach porannych.")
                .transportType("Train")
                .price(new BigDecimal(1000L))
                .start(Instant.now().plus(100, ChronoUnit.DAYS))
                .end(Instant.now().plus(102, ChronoUnit.DAYS))
                .guideEmail("")
                .guideName("")
                .build();
        if (!journeyRepository.findById(journey.getId()).isPresent()) {
            journeyRepository.save(journey);
        }

        return args -> {
        };
    }
}
